package Server;

import Common.ActionRequest;
import Common.Message;
import Common.StatusUpdate;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This is the main server application. It initializes ServerSocketComms, then waits to receive ActionRequests.
 * It will then serially handle action requests in the order they are received.
 *
 * @author Kit Kindred
 *
 * TODO: Most of the contents of this file are stubs for the game logic to tie into later
 *
 */
public class ClueLessServer extends Thread
{
    /**
     * Start the server application, then allow the user to run a couple of commands
     * as a basic functionality test.
     *
     * @param args - Unused
     */
    public static void main(String[] args)
    {
        ClueLessServer serverApplication = new ClueLessServer();  // Create the server
        serverApplication.start();  // Start processing

        // BEGIN TEMPORARY - Just to let us test out the interface
        // Read user input from CLI and perform appropriate action
        Scanner scan = new Scanner(System.in);
        int input;
        do
        {
            System.out.println("****Enter a command****");
            System.out.println("Send status to all clients: 1");
            System.out.println("Get number of connected clients: 2");
            System.out.println("Exit: -1");
            input = scan.nextInt();
            switch (input)
            {
                case 1 ->
                        {
                            System.out.println("Sending status...\n");
                            serverApplication.ssc.sendToAll(new StatusUpdate());
                        }
                case 2 ->
                        {
                            System.out.println("Currently connected clients: " + serverApplication.ssc.getNumConnectedClients() + "\n");
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
        serverApplication.close();
        /*try
        {
            serverApplication.join();  // Just sit here until someone kills the server
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
    }

    /**
     * Main request queue that the main processing loop waits on
     */
    ArrayBlockingQueue<ActionRequest> ActionQueue;

    /**
     * Max number of activities that can be queued simultaneously. May have to be increased later
     */
    static final int ActionQueueDepth = 10;

    /**
     * The server applications underlying network interface
     */
    public ServerSocketComms ssc;  // TODO: This should be private, and ClueLessServer should have its own send
    //       abstraction. Since that will be implemented with the game logic, we'll
    //       call the ssc send directly for testing.


    /**
     * Initialize the ClueLessServer. Set up game state
     */
    public ClueLessServer()
    {
        ActionQueue = new ArrayBlockingQueue<ActionRequest>(ActionQueueDepth);
    }


    /**
     * Main processing thread. Start the ServerSocketComms, then wait
     * until something starts asking us to do something
     */
    public void run()
    {
        ssc = new ServerSocketComms(6789, this::recvCallback);  // Initialize SSC  TODO: Hardcoded port...
        while(true)  // Keep trying to execute commands forever
        {
            try
            {
                ActionRequest actReq = ActionQueue.take();  // Blocks until something gets added to the queue
                processAction(actReq);
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


    /**
     * SSC just received something from a client. Parse it and pass it to where it needs to go
     *
     * @param msg - The message received from a client
     *
     * TODO: Stub
     *
     */
    public void recvCallback(Message msg)
    {
        System.out.println("SSC: Received " + msg.type);
        if(msg instanceof ActionRequest)
        {
            try
            {
                ActionQueue.put((ActionRequest)msg);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * This gets called serially from the main ClueLessServer thread everytime we add something to the action queue
     *
     * @param actionRequest - The action that we have been requested to perform
     *
     * TODO: Stub
     *
     */
    public void processAction(ActionRequest actionRequest)
    {
        System.out.println("Process Action: Received ActionRequest");
    }


    /**
     * Alright, game's over. Kill the server
     */
    public void close()
    {
        ssc.close();  // Kill server comms
        interrupt();  // Then kill our action processing
    }
}
