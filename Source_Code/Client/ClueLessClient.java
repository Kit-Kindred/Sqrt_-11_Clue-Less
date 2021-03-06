package Client;

import Common.ActionRequest;
import Common.Message;
import Common.StatusUpdate;
import Server.ClueLessServer;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.Thread.sleep;

public class ClueLessClient extends Thread
{
    /**
     * Very similar to server. Start the client receiving updates from the server,
     * then allow the user to run some test commands
     *
     * @param args - Unused
     *
     */
    public static void main(String[] args)
    {
        ClueLessClient clientApplication = new ClueLessClient();  // Create the server
        clientApplication.start();  // Start processing

        // BEGIN TEMPORARY - Just to let us test out the interface
        // Read user input from CLI and perform appropriate action
        Scanner scan = new Scanner(System.in);
        int input;
        do
        {
            System.out.println("****Enter a command****");
            System.out.println("Send action request to server: 1");
            System.out.println("See total num received status updates: 2");
            System.out.println("Exit: -1");
            input = scan.nextInt();
            switch (input)
            {
                case 1 ->
                        {
                            System.out.println("Sending action request...\n");
                            clientApplication.csc.send(new ActionRequest());
                        }
                case 2 ->
                        {
                            System.out.println("Num status updates received: " + clientApplication.numUpdatesReceived + "\n");
                        }
                case -1 ->
                        {
                            System.out.println("Exiting...\n");
                        }
                default ->
                        {
                            System.out.println("Sorry, that option isn't currently supported. Please try again\n");
                        }
            }
        }
        while(input != -1);
        // END TEMPORARY

        // Not sure what our server exit condition will be in the final form...
        clientApplication.close();
        /*try
        {
            serverApplication.join();  // Just sit here until someone kills the server
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
    }

    public ClientSocketComms csc;  // TODO: Should be private

    ArrayBlockingQueue<StatusUpdate> UpdateQueue;

    static final int UpdateQueueDepth = 10;

    public int numUpdatesReceived;  // Temp just to show we're getting things

    public ClueLessClient()
    {
        UpdateQueue = new ArrayBlockingQueue<StatusUpdate>(UpdateQueueDepth);
        numUpdatesReceived = 0;
    }

    public void run()
    {
        csc = new ClientSocketComms("127.0.0.1", 6789, this::recvCallback);
        while(true)
        {
            try
            {
                StatusUpdate statUp = UpdateQueue.take();  // Blocks until something gets added to the queue
                updateStatus(statUp);
            }
            catch(InterruptedException e)
            {
                return;  // Someone called close; time to die
            }
            catch (Exception e)
            {
                e.printStackTrace();  // We don't expect this. Log it and try to keep going
            }
        }
    }

    public void updateStatus(StatusUpdate statUp)
    {
        System.out.println("Update Status: Received StatusUpdate");
        numUpdatesReceived++;
    }

    /**
     * CSC just received something from the server. Parse it and pass it to where it needs to go
     *
     * @param msg - The message received from the server
     *
     * TODO: Stub
     *
     */
    public void recvCallback(Message msg)
    {
        System.out.println("CSC: Received " + msg.type);
        if(msg instanceof StatusUpdate )
        {
            try
            {
                UpdateQueue.put((StatusUpdate) msg);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * AKill the client
     */
    public void close()
    {
        csc.close();  // Kill server comms
        interrupt();  // Then kill our action processing
    }
}
