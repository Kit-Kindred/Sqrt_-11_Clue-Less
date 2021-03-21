package Client;

import Common.*;
import Common.Messages.*;
import Common.Messages.ActionRequests.ActionRequest;
import Common.Messages.ActionRequests.ConnectRequest;
import Common.Messages.ActionRequests.GameStartRequest;
import Common.Messages.ActionRequests.MoveRequest;
import Common.Messages.StatusUpdates.*;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.Semaphore;

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
        // Can pass these in from the command line if you want
        // TODO: Error-check these
        Scanner scan = new Scanner(System.in);
        String server;
        int port;
        if(args.length != 2)
        {
            System.out.println("Enter server IP:");
            server = scan.nextLine();
            System.out.println("Enter server port");
            port = scan.nextInt();
        }
        else
        {
            server = args[0];
            port = Integer.parseInt(args[1]);
        }

        ClueLessClient clientApplication = new ClueLessClient(server, port);  // Create the server
        clientApplication.start();  // Start processing

        // Auto-connect to the server for now, but make sure we're initialized first
        try
        {
            clientApplication.initialized.acquire();  // Sem wait
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        clientApplication.csc.send(new ConnectRequest(clientApplication.UserPlayer.PlayerNumber));

        // BEGIN TEMPORARY - Just to let us test out the interface
        // Read user input from CLI and perform appropriate action
        int input;
        do
        {
            System.out.println("****Enter a command****");
            System.out.println("Send action request to server: 1");
            System.out.println("See total num received status updates: 2");
            System.out.println("Send Connect Request: 3");  // To test sending multiple
            System.out.println("Request Start Game: 4");
            System.out.println("Exit: -1");
            input = scan.nextInt();
            
            // Clients not going first need a way to break from this while loop 
            // without 
            if( clientApplication.activeGame )
            {
               break;
            }
            
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
                case 3 ->
                        {
                            if(!clientApplication.ConnectionRequested)
                            {
                                System.out.println("Sending connect request...\n");
                                clientApplication.csc.send(new ConnectRequest(clientApplication.UserPlayer.PlayerNumber));
                                clientApplication.ConnectionRequested = true;
                            }
                            else
                            {
                                System.out.println("Connect request pending...\n");
                            }

                        }
                case 4 ->
                        {
                            System.out.println("Sending game start request...\n");
                            clientApplication.csc.send(new GameStartRequest());
                            
                            // Using a sleep here to wait for the active game status to be updated.
                            // There's probably a better way of doing this.
                            // TODO: reduce the sleep time if possible
                            try
                            {
                               Thread.sleep( 1000 );
                            } 
                            catch( InterruptedException e )
                            {
                               e.printStackTrace();
                            }
                            
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
        while(input != -1 && clientApplication.activeGame != true );
        
        
        /**
         * Present the user with actions and wait for them to take their turn
         */
        do
        {
            input = scan.nextInt();
            switch (input)
            {
                case 1 ->
                        {
                            System.out.println("Sending move left request...\n");
                            clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerNumber, "left"));
                        }
                case 2 ->
                        {
                           System.out.println("Sending move right request...\n");
                           clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerNumber, "right"));
                        }
                case 3 ->
                        {
                           System.out.println("Sending move up request...\n");
                           clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerNumber, "up"));

                        }
                case 4 ->
                        {
                           System.out.println("Sending move down request...\n");
                           clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerNumber, "down"));
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
        while(input != -1 && clientApplication.activeGame );
        
        
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

    private final String ServerIP;
    private final int ServerPort;

    private Player UserPlayer;

    private boolean ConnectionRequested;  // Wait for a response before asking to connect again

    public int numUpdatesReceived;  // Temp just to show we're getting things

    public Semaphore initialized;
    
    public boolean activeGame; // True if the game has started

    public ClueLessClient(String serverIP, int serverPort)
    {
        initialized = new Semaphore(0);
        ServerIP = serverIP;
        ServerPort = serverPort;
        UpdateQueue = new ArrayBlockingQueue<StatusUpdate>(UpdateQueueDepth);
        numUpdatesReceived = 0;
        ConnectionRequested = false;
        activeGame = false;
        UserPlayer = new Player();
    }

    public void run()
    {
        csc = new ClientSocketComms(ServerIP, ServerPort, this::recvCallback);
        initialized.release();  // Ready to go; post the semaphore
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

    /**
     * Process status updates sent to the clien
     *
     * @param statUp - Received status update
     */
    public void updateStatus(StatusUpdate statUp)
    {
        if(statUp instanceof PlayerConnection)  // Another player joined/left the game
        {
            if(((PlayerConnection) statUp).Connected)
            {
                System.out.println("[Server] Player " + ((PlayerConnection) statUp).PlayerID + " joined the game!");
            }
            else
            {
                System.out.println("[Server] Player " + ((PlayerConnection) statUp).PlayerID + " left the game!");
            }
        }
        else if(statUp instanceof ConnectRequestStatus)  // Response to our join request
        {
            if(((ConnectRequestStatus) statUp).Joined)
            {
                UserPlayer.PlayerNumber = ((ConnectRequestStatus) statUp).PlayerID;
                System.out.println("[Server] You are now Player " + UserPlayer.PlayerNumber + "!");
            }
            else
            {
                System.out.println("[Server] Join request refused");
                csc.send(new ConnectRequestStatus(false, UserPlayer.PlayerNumber));  // Send refusal acknowledgement
            }
            ConnectionRequested = false;  // We got a response, so we can ask again if we want
        }
        else if(statUp instanceof Notification)  // Generic server print essentially
        {
            System.out.println("[Server] " + ((Notification) statUp).NotificationText);
        }
        else if(statUp instanceof GameStart)  // Starting/ending the game
        {
            if(((GameStart) statUp).GameStarting)
            {
                System.out.println("[Server] Game starting!");
                activeGame = true;
            }
            else
            {
                System.out.println("[Server] Game ending!");
                activeGame = false;
            }
        }
        else if( statUp instanceof TurnUpdate)
        {
            if(((TurnUpdate) statUp).activeTurn )
            {
               UserPlayer.PlayerTurn = true; // Set the turn status to true 
               System.out.println( "[Server] It is now your turn.");
               System.out.println("\n\n****Enter a command****");
               System.out.println("Move Left: 1");
               System.out.println("Move Right: 2");
               System.out.println("Move Up: 3");  // To test sending multiple
               System.out.println("Move Down: 4");
               System.out.println("Exit: -1");
            }
            
            else
            {
               UserPlayer.PlayerTurn = false; // Set the turn status to false
               System.out.println( "[Server] Ended turn.");
            }
            
        }
        
        else  // Something else. Eventually this'll be an error case, but it's fine for now
        {
            System.out.println("[Server] Update Status: Received StatusUpdate");
        }
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
     * Kill the client
     */
    public void close()
    {
        csc.close();  // Kill server comms
        interrupt();  // Then kill our action processing
    }
}
