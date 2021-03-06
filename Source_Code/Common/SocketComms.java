package Common;

import Common.Message;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SocketComms
{

    /**
     * The queue we send messages from, passed in from outside
     */
    public ArrayBlockingQueue<Message> SendQueue;

    /**
     * The queue we add received messages to, passed in from outside
     */
    public ArrayBlockingQueue<Message> RecvQueue;


    /**
     * The stream we use to send data over the socket in the send thread
     */
    ObjectOutputStream Send;

    /**
     * The stream we use to receive data from the socket in the receive thread
     */
    ObjectInputStream Recv;


    /**
     * Receive thread. Might not actually need to keep track of this
     */
    private Thread rt;

    /**
     * Send thread. Might not actually need to keep track of this
     */
    private Thread st;

    /**
     * Are we among the living?
     */
    private boolean isAlive;


    /**
     * Initialize the actual sockets that we send and receive on. Server and client are identical except
     * that the input and output stream creation order needs to be different between client and server
     *
     * @param socket - Socket that we'll use for Tx/Rx. This will always be connected before entering this constructor
     * @param sendQueue - Where we send from
     * @param recvQueue - Where we receive to
     * @param server - Is this a server or client socket?
     */
    public SocketComms(Socket socket,
                       ArrayBlockingQueue<Message> sendQueue,
                       ArrayBlockingQueue<Message> recvQueue,
                       boolean server)
    {
        try
        {
            System.out.println("SocketComms: Connected!");

            SendQueue = sendQueue;
            RecvQueue = recvQueue;

            // Socket is connected; initialize i/o streams
            // ORDER MATTERS! Trying to initialize send-send or recv-recv creates a deadlock!
            if(server)
            {
                Recv = new ObjectInputStream(socket.getInputStream());
                Send = new ObjectOutputStream(socket.getOutputStream());
            }
            else
            {
                Send = new ObjectOutputStream(socket.getOutputStream());
                Recv = new ObjectInputStream(socket.getInputStream());
            }

            isAlive = true;  // It's alive...

            (st = new Thread(this::sendThreadFunc)).start();  // Let's start looking for sends
            (rt = new Thread(this::recvThreadFunc)).start();  // And start looking for receives
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed SocketComms Init!");  // Awkward...
        }
    }


    /**
     * This thread just blocks waiting for receives
     */
    private void recvThreadFunc()
    {
        while(true)  // Listen unless we disconnect from the server
        {
            try
            {
                Message m = (Message)Recv.readObject();  // Block until we get a message
                RecvQueue.put(m);  // We got something. Application will error check\
            }
            // EOF - Server died
            // Socket - Someone called close
            catch(EOFException | SocketException e)  // Socket was disconnected. Shut down the whole socket
            {
                System.out.println("SC Recv Thread: Connection closed!");
                isAlive = false;
                return;
            }
            catch (Exception e)  // Something unexpected. Log it and hope we recover
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * This thread just blocks waiting for sends
     */
    private void sendThreadFunc()
    {
        while(isAlive)  // Listen unless we disconnect from the server
        {
            try
            {
                // Check if we have a message to send every ms. Should stay responsive without
                // using too much CPU time. Can be adjusted if needed
                Message m = SendQueue.poll(1, TimeUnit.MILLISECONDS);
                if(m != null)
                {
                    Send.writeObject(m);  // If we have something, send it
                }
            }
            catch(SocketException e)  // Someone called close
            {
                System.out.println("SC Send Thread: Socket closed! Exiting...");
                // Set isAlive? Currently interrupting both threads to kill
                return;
            }
            catch (Exception e)  // Something unexpected. Log it and hope we recover
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Is this thread alive?
     *
     * @return - true if alive, false if not
     *
     */
    public boolean isAlive()
    {
        return isAlive;
    }


    /**
     * We might be the server where a client just dropped
     * We might be a client when the server has stopped
     *
     * Whatever the reason, wherever it lies
     * This socket is over, so now it must die
     */
    public void kill()
    {
        try
        {
            // Send and receive will close themselves if the i/o streams are closed
            Send.close();
            Recv.close();
            isAlive = false;  // Make sure this is up-to-date
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
