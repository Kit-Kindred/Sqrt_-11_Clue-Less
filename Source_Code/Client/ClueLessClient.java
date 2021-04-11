package Client;

import Common.*;
import Common.CharacterCard.CharacterName;
import Common.RoomCard.RoomName;
import Common.WeaponCard.WeaponType;
import Common.Messages.*;
import Common.Messages.ActionRequests.ActionRequest;
import Common.Messages.ActionRequests.ConnectRequest;
import Common.Messages.ActionRequests.GameStartRequest;
import Common.Messages.ActionRequests.MoveRequest;
import Common.Messages.ActionRequests.SuggestRequest;
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
        String name;
        int port;
        if(args.length != 3)
        {
            System.out.println("Enter server IP:");
            server = scan.nextLine();
            System.out.println("Enter server port:");
            port = Integer.parseInt(scan.nextLine());
            System.out.println("Enter player name:");
            name = scan.nextLine();
        }
        else
        {
            server = args[0];
            port = Integer.parseInt(args[1]);
            name = args[2];
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
        //clientApplication.csc.send(new ConnectRequest(clientApplication.UserPlayer.PlayerNumber));
        clientApplication.setPlayerName(name);
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
               System.out.println("\n****Enter a command****");
               System.out.println("Move Left: 1");
               System.out.println("Move Right: 2");
               System.out.println("Move Up: 3");  // To test sending multiple
               System.out.println("Move Down: 4");
               System.out.println("Exit: -1");
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
                                clientApplication.csc.send(new ConnectRequest(clientApplication.UserPlayer.PlayerName));
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
                               Thread.sleep( 500 );
                            }
                            catch( InterruptedException e )
                            {
                               e.printStackTrace();
                            }

                            input = scan.nextInt();

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
        while(input != -1 && !clientApplication.activeGame);



        // Present the user with actions and wait for them to take their turn
        do
        {
           // Quick turn validation to make sure it's the player's turn
           if( clientApplication.UserPlayer.PlayerTurn )
           {
              switch (input)
              {
                case 1 ->
                        {
                            System.out.println("Sending move left request...\n");
                            clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerName, MoveRequest.Move.LEFT));
                        }
                case 2 ->
                        {
                           System.out.println("Sending move right request...\n");
                           clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerName, MoveRequest.Move.RIGHT));
                        }
                case 3 ->
                        {
                           System.out.println("Sending move up request...\n");
                           clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerName, MoveRequest.Move.UP));

                        }
                case 4 ->
                        {
                           System.out.println("Sending move down request...\n");
                           clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerName, MoveRequest.Move.DOWN));
                        }
                case 5 ->
                        {
                            System.out.println("Which character would you like to suggest?");
                            int index = 1;
                            for ( CharacterName character : CharacterName.values() )
                            {
                                System.out.println(character + " : " + index);
                                index += 1;
                            }
                            // int suggestCharacterIndex = scan.nextInt() - 1;
                            CharacterName suggestCharacter = CharacterName.values()[ scan.nextInt() - 1] ;

                            // index = 1;
                            // for ( RoomName room : RoomName.values() )
                            // {
                            //     System.out.println(room + " : " + index);
                            //     index += 1;
                            // }
                            // int suggestRoomIndex = scan.nextInt();

                            // TODO TAKE THIS OUT IT IS JUST HARDCODED TO ALWAYS GUESS
                            // 1. THIS NEEDS TO BE CHANGED TO USE THE CURRENT ROOM
                            // THE PLAYER IS IN, AND ADD CHECKS ON SERVER SIDE
                            RoomName suggestRoom = RoomName.values()[1];

                            System.out.println("Which weapon would you like to suggest?");
                            index = 1;
                            for ( WeaponType weapon : WeaponType.values() )
                            {
                                System.out.println(weapon + " : " + index);
                                index += 1;
                            }
                            // int suggestWeaponIndex = scan.nextInt() - 1;
                            WeaponType suggestWeapon = WeaponType.values()[ scan.nextInt() - 1 ];

                            SolutionHand suggestHand = new SolutionHand( suggestCharacter, suggestRoom, suggestWeapon );
                            clientApplication.csc.send(new SuggestRequest(clientApplication.UserPlayer.PlayerName, suggestHand));
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

           else
           {
              System.out.println("It is not your turn, please wait.");
           }

            input = scan.nextInt();
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

    private final Player UserPlayer;

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
     * Process status updates sent to the client
     *
     * @param statUp - Received status update
     */
    public void updateStatus(StatusUpdate statUp)
    {
        if(statUp instanceof PlayerConnection)  // Another player joined/left the game
        {
            if(((PlayerConnection) statUp).Connected)
            {
                System.out.println("[Server] " + ((PlayerConnection) statUp).PlayerName + " joined the game!");
            }
            else
            {
                System.out.println("[Server] " + ((PlayerConnection) statUp).PlayerName + " left the game!");
            }
        }
        else if(statUp instanceof ConnectRequestStatus)  // Response to our join request
        {
            if(((ConnectRequestStatus) statUp).Joined)
            {
                UserPlayer.PlayerName = ((ConnectRequestStatus) statUp).PlayerName;  // Think these should always already match
                System.out.println("[Server] You are now connected as " + UserPlayer.PlayerName + "!");
            }
            else
            {
                System.out.println("[Server] Join request refused");
                csc.send(new ConnectRequestStatus(false, UserPlayer.PlayerName));  // Send refusal acknowledgement
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
                System.out.println("[Server] Game starting!\n");
                activeGame = true;
            }
            else
            {
                System.out.println("[Server] Game ending!\n");
                activeGame = false;
            }
        }
        else if( statUp instanceof TurnUpdate)
        {
            activeGame = true;  // In case of disconnect and reconnect
            if(((TurnUpdate) statUp).activeTurn )
            {
                UserPlayer.PlayerTurn = true; // Set the turn status to true
                System.out.println( "\n[Server] It is now your turn.");
                System.out.println("\n\n****Enter a command****");
                System.out.println("Move Left: 1");
                System.out.println("Move Right: 2");
                System.out.println("Move Up: 3");  // To test sending multiple
                System.out.println("Move Down: 4");
                System.out.println("Suggest: 5");
                System.out.println("Exit: -1");
            }



            else
            {
               UserPlayer.PlayerTurn = false; // Set the turn status to false
               System.out.println( "[Server] Ended turn.");
            }

        }


        else if( statUp instanceof PlayerHandUpdate)
        {
            UserPlayer.setHand( ((PlayerHandUpdate) statUp).getHandUpdate() );
            System.out.println( UserPlayer.getAllCardsString() + "\n");
           //System.out.println( ((PlayerHandUpdate) statUp).getHandUpdate() + "\n");

        }

        //Notify all players of a suggestion (who, and what they are suggestion)
        else if( statUp instanceof SuggestNotification)
        {
            System.out.println((SuggestNotification) statUp);
        }

        //Notify this player who refuted and what card they showed
        else if( statUp instanceof RefuteSuggestion)
        {
            System.out.println((RefuteSuggestion) statUp);
        }

        //Notify all players that a given player was unable to refute
        else if( statUp instanceof SuggestionPassed)
        {
            System.out.println(((SuggestionPassed) statUp).PlayerName + " was not able to refute the suggestion");
        }

        //Notify all players that a given player was able to refute (WITHOUT THE SPECIFIC CARD)
        else if( statUp instanceof SuggestionWrong)
        {
            System.out.println(((SuggestionWrong) statUp).RefuterName + " was able to refute the suggestion");
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

    public void setPlayerName(String name)
    {
        UserPlayer.PlayerName = name;
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
