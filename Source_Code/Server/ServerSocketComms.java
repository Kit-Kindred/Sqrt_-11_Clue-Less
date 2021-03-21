package Server;

import Common.DisconnectCallback;
import Common.Messages.Message;
import Common.ReceiveCallback;
import Common.SocketComms;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This is the heart of the server. Has two main threads:
 * The first listens for new clients to connect, then adds them to the list of connected clients
 * The second cleans out any dead clients, then polls all of the active clients in turn for messages
 *
 * @author Kit Kindred
 *
 */
public class ServerSocketComms extends Thread
{
    /**
     * Java ServerSocket to handle accepting incoming client connections
     */
    private ServerSocket ServSock;

    /**
     * The list of clients connected to the server. 'final' so that we can safely use it for synchronization
     */
    private final ArrayList<SocketComms> ClientList = new ArrayList<SocketComms>();

    /**
     * Max send/recv queue depth. Chosen arbitrarily; may need to change
     */
    private static final int MessageQueueSize = 10;

    private int ClientID;

    /**
     * Thread two from the class description
     */
    private Thread listenAndCleanThread;

    /**
     * Need this so we can pass the passed-in receive callback to the listener thread
     */
    private ReceiveCallback recvCallback;

    private DisconnectCallback DisconnectCallback;

    /**
     * Try to bind the server to a port then kick off our threads
     *
     * @param port - The local port we're trying to bind the server to
     * @param recvCallback - What to do when we get a message
     *
     */
    public ServerSocketComms(int port, ReceiveCallback recvCallback, DisconnectCallback disconnectCallback)
    {
        try
        {
            ServSock = new ServerSocket(port);  // Bind to the desired port
            this.recvCallback = recvCallback;
            this.DisconnectCallback = disconnectCallback;
            ClientID = 0;
            start();

        }
        catch (Exception e)
        {
            e.printStackTrace();  // We should be pretty good unless our port is blocked
        }
    }

    /**
     * Kicks off the cleaner/listener thread, then waits for clients to try to connect
     */
    public void run()
    {
        // Start message listener
        listenAndCleanThread = new Thread(() -> {
            listenAndClean(recvCallback);
        });
        listenAndCleanThread.start();

        // Start connection listener
        while(true)
        {
            try
            {
                //System.out.println("SSC: Waiting for connection on port " + ServSock.getLocalPort());

                // Wait for a client to connect, create a new SocketThread, and add it to the list
                Socket socket = ServSock.accept();
                SocketComms newClient = new SocketComms(socket,
                                        new ArrayBlockingQueue<Message>(MessageQueueSize),
                                        new ArrayBlockingQueue<Message>(MessageQueueSize),
                                        DisconnectCallback,
                                 true,  // Means that we are connecting this socket from the server-side,
                                               // not that the socket is a server socket
                                        ClientID);

                synchronized (ClientList)  // We add and remove from different threads. Lock this so we do one at a time
                {
                    ClientList.add(newClient);
                }

                //System.out.println("SSC: Accepted connection on port " + ServSock.getLocalPort());
                ClientID++;
            }
            catch(SocketException e)
            {
                return;  // Someone called close. Or something horrible happened to our ServerSocket. Either way, die
            }
            catch (Exception e)
            {
                e.printStackTrace();  // Shouldn't get anything else. Log it and try to recover
            }
        }
    }

    /**
     * Clean up dead threads, then poll the living
     *
     * @param recvCallback - What to do if we get something
     *
     */
    private void listenAndClean(ReceiveCallback recvCallback)
    {
        while (true)  // Listen unless interrupted
        {
            try
            {
                if(ClientList.size() == 0)  // No clients, nothing to do. Just wait a sec to see if anyone shows up
                {
                    sleep(1000); // Check for clients every second
                }
                else
                {
                    // Don't want to remove from the main list while we're iterating over it
                    ArrayList<SocketComms> tempList = new ArrayList<SocketComms>();

                    // Clean dead threads. No touching while this is happening
                    synchronized(ClientList)
                    {
                        for(int i = 0; i < ClientList.size(); i++)  // Every client
                        {
                            if(!ClientList.get(i).isAlive())  // Is it running?
                            {
                                tempList.add(ClientList.get(i));  // No? Queue it for removal
                            }
                        }
                        ClientList.removeAll(tempList);  // Bye
                    }
                    // Now poll all clients. It's OK if something gets added to the end while we're polling
                    for(int i = 0; i < ClientList.size(); i++)
                    {
                        // There will necessarily be a couple ms lag between receiving and processing a request
                        // I don't see it being an issue, but we can adjust accordingly
                        Message m = ClientList.get(i).RecvQueue.poll(1, TimeUnit.MILLISECONDS);
                        if (m != null)
                        {
                            recvCallback.onRecv(m);  // Hey, we got something! Send it off for processing
                        }
                    }
                }
            }
            catch (InterruptedException e)
            {
                return;  // Someone called close. Time to die
            }
            catch (Exception e)
            {
                e.printStackTrace();  // Probably shouldn't happen
            }
        }
    }

    /**
     * Add something to a specified client's send queue (puts it at the end of the send line)
     *
     * @param clientID - Which client in the list do we want to send to?
     * @param msg - The message we're sending
     * @return - Did we add it to the send queue?
     *
     */
    public boolean sendToClient(int clientID, Message msg)
    {
        for(SocketComms client : ClientList)  // Find the client
        {
            if(client.getUniqueID() == clientID)
            {
                if (client.isAlive())  // Is this client alive?
                {
                    try
                    {
                        client.SendQueue.put(msg);  // Give it a go
                        return true;  // TODO: Doesn't actually verify send; just queue addition
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();  // Log it
                    }
                }
            }
        }
        return false;  // If the put failed or the client was invalid, we didn't send
    }

    /**
     * Add a message to every client's send queue. Wraps sendToClient
     * Really just debug since the server will only send to active player clients
     *
     * @param msg - What we're sending
     * @return - True if added to every queue, false otherwise
     *
     */
    public boolean sendToAll(Message msg)  // TODO: If one fails, they all fail. Track individual pass/fail in future
    {
        boolean status = true;  // Assume all good unless told otherwise
        synchronized (ClientList)  // No additions or removals during global send
        {
            for(int i = 0; i < ClientList.size(); i++)  // Loop through every client
            {
                try
                {
                    boolean temp = sendToClient(i, msg);  // Try to send
                    if(!temp)
                    {
                        status = false;  // No good...
                    }
                }
                catch (Exception e)
                {
                    status = false;
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    public boolean disconnectClient(int uid)
    {
        synchronized(ClientList)
        {
            for(SocketComms servSock : ClientList)
            {
                if(servSock.getUniqueID() == uid)
                {
                    servSock.kill();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the number of connected clients
     *
     * @return - How many clients are connected
     *
     */
    public int getNumConnectedClients()
    {
        return ClientList.size();
    }

    /**
     * Fun's over; stop the server
     */
    public void close()
    {
        listenAndCleanThread.interrupt();  // Stop listening
        synchronized(ClientList)  // And kill everyone. But make sure we're not trying to add someone simultaneously
        {
            for(SocketComms servSock : ClientList)
            {
                servSock.kill();
            }
        }
        try
        {
            ServSock.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}