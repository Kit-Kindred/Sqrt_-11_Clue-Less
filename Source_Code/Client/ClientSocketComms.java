package Client;

import Common.Message;
import Common.ReceiveCallback;
import Common.SocketComms;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ClientSocketComms
{
    private SocketComms SockComms;

    private static final int MessageQueueSize = 10;  // Arbitrary

    private ArrayBlockingQueue<Message> SendQueue;
    private ArrayBlockingQueue<Message> RecvQueue;

    private Thread RecvCallbackThread;

    public ClientSocketComms(String host, int port, ReceiveCallback recvCallback)
    {
        try
        {
            SendQueue = new ArrayBlockingQueue<Message>(MessageQueueSize);
            RecvQueue = new ArrayBlockingQueue<Message>(MessageQueueSize);

            System.out.println("CSC: Attempting to connect to " + host + " on port " + port + "...\n");
            SockComms = new SocketComms(new Socket(host, port),
                    SendQueue,
                    RecvQueue,
                    false);
            RecvCallbackThread = new Thread(() -> {
                recvCallbackThreadFunc(recvCallback);
            });
            RecvCallbackThread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("CSC: Could not connect to " + host + " on port " + port + "!\n");
        }
    }

    public void send(Message msg)
    {
        try
        {
            SendQueue.put(msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        RecvCallbackThread.interrupt();
        SockComms.kill();
    }

    private void recvCallbackThreadFunc(ReceiveCallback recvCallback)
    {
        // Listen for received messages until the socket is closed
        while(SockComms.isAlive())
        {
            try
            {
                Message m = RecvQueue.poll(1, TimeUnit.MILLISECONDS);
                if (m != null)
                {
                    recvCallback.onRecv(m);
                }
            }
            catch(InterruptedException e)
            {
                System.out.println("RecvCBThread: Closing connection...");
                return;  // Die if interrupted
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
