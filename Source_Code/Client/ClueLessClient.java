package Client;

import Common.*;
import Common.CharacterCard.CharacterName;
import Common.Messages.ActionRequests.*;
import Common.RoomCard.RoomName;
import Common.WeaponCard.WeaponType;
import Common.Messages.*;
import Common.Messages.StatusUpdates.*;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

public class ClueLessClient extends Thread
{
    /**
     * Very similar to server. Start the client receiving updates from the server,
     * then allow the user to run some test commands
     *
     * @param args - Unused
     * @throws IOException
     * @throws NumberFormatException
     * @throws TimeoutException
     * @throws InterruptedException
     *
     */
    public static void main(String[] args) throws NumberFormatException, IOException, TimeoutException, InterruptedException
    {
        ConsoleInput reader = new ConsoleInput();
        String server;
        String name;
        int port;
        if(args.length != 3)
        {
            System.out.println("Enter server IP:");
            server = reader.read();
            System.out.println("Enter server port:");
            port = Integer.parseInt(reader.read());
            System.out.println("Enter player name:");
            name = reader.read();
        }
        else
        {
            server = args[0];
            port = Integer.parseInt(args[1]);
            name = args[2];
        }

        ClueLessClient clientApplication = new ClueLessClient(server, port);  // Create the server
        clientApplication.start();  // Start processing
        clientApplication.setPriority( 10 );

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
        int input = 0;

        System.out.println();
        printPreGameInstructions();

        while(input != -1 && !clientApplication.activeGame)
        {

            input = Integer.parseInt( reader.read() );

            // Clients not going first need a way to break from this while loop
            // without
            if( clientApplication.activeGame || interrupted )
            {
//               System.out.println("\n****Enter a command****");
//               System.out.println("Move Left: 1");
//               System.out.println("Move Right: 2");
//               System.out.println("Move Up: 3");  // To test sending multiple
//               System.out.println("Move Down: 4");
//               System.out.println("Exit: -1");
               break;
            }

            switch (input)
            {
                case 0 ->
                        {
                            //break;
                        }
                case 1 ->
                        {
                            System.out.println("Sending action request...\n");
                            clientApplication.csc.send(new ActionRequest());
//                            printPreGameInstructions();
                        }
                case 2 ->
                        {
                            System.out.println("Num status updates received: " + clientApplication.numUpdatesReceived + "\n");
//                            printPreGameInstructions();
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
//                            printPreGameInstructions();

                        }
                case 4 ->
                        {
                            System.out.println("Sending game start request...\n");
                            clientApplication.csc.send(new GameStartRequest());

                            // Using a sleep here to wait for the active game status to be updated.
                            // There's probably a better way of doing this.
                            try
                            {
                               Thread.sleep( 500 );
                            }
                            catch( InterruptedException e )
                            {
                               e.printStackTrace();
                            }

                            input = Integer.parseInt( reader.read() );
//                            printPreGameInstructions();
                        }
                case -1 ->
                        {
                            System.out.println("Exiting...\n");
                        }
                default ->
                        {
                            System.out.println("Sorry, that option isn't currently supported. Please try again\n");
//                            printPreGameInstructions();
                        }
            }
        }


        // Present the user with actions and wait for them to take their turn
        do
        {

           // Quick turn validation to make sure it's the player's turn
           if( clientApplication.UserPlayer.PlayerTurn )
           {

              if( !clientApplication.UpdateQueue.isEmpty() || interrupted )
              {
                 break;
              }

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
                            System.out.println("Sending shortcut request...\n");
                            clientApplication.csc.send(new MoveRequest(clientApplication.UserPlayer.PlayerName, MoveRequest.Move.SHORTCUT));
                        }
                case 6 ->
                        {
                            if(clientApplication.board.inRoom(clientApplication.UserPlayer))
                            {
                                System.out.println( clientApplication.UserPlayer.getAllCardsString() + "\n");
                                System.out.println("Which character would you like to suggest?\n");
                                int index = 1;
                                for ( CharacterName character : CharacterName.values() )
                                {
                                    System.out.println( "\t[" + index + "] " + character);
                                    index += 1;
                                }
                                // int suggestCharacterIndex = scan.nextInt() - 1;
                                CharacterName suggestCharacter = CharacterName.values()[ Integer.parseInt( reader.read() ) - 1] ;

                                RoomName suggestRoom = clientApplication.board.boardTextToRoomEnum(clientApplication.board.board[clientApplication.UserPlayer.yPos][clientApplication.UserPlayer.xPos]);

                                System.out.println("Which weapon would you like to suggest?\n");
                                index = 1;
                                for ( WeaponType weapon : WeaponType.values() )
                                {
                                    System.out.println("\t[" + index + "] " + weapon);
                                    index += 1;
                                }
                                // int suggestWeaponIndex = scan.nextInt() - 1;
                                WeaponType suggestWeapon = WeaponType.values()[ Integer.parseInt( reader.read() ) - 1 ];

                                SuggestHand suggestHand = new SuggestHand( suggestCharacter, suggestRoom, suggestWeapon );
                                clientApplication.csc.send(new SuggestRequest(clientApplication.UserPlayer.PlayerName, suggestHand, clientApplication.UserPlayer.xPos, clientApplication.UserPlayer.yPos));
                            }
                            else
                            {
                                System.out.println("You need to be in a room to suggest!");
                            }
                        }
                case 7 ->
                        {
                            System.out.println("Ending turn...\n");
                            clientApplication.csc.send(new EndTurn(clientApplication.UserPlayer.PlayerName));
                        }
                case 9 ->
                        {

                            System.out.println( clientApplication.UserPlayer.getAllCardsString() + "\n");
                            System.out.println("Which character would you like to accuse?\n");
                            int index = 1;
                            for ( CharacterName character : CharacterName.values() )
                            {
                                System.out.println("\t[" + index + "] " + character);
                                index += 1;
                            }
                            CharacterName accuseCharacter = CharacterName.values()[ Integer.parseInt( reader.read() ) - 1];

                            System.out.println("Which room would you like to accuse?");
                            index = 1;
                            for ( RoomName room : RoomName.values() )
                            {
                                System.out.println("\t[" + index + "] " + room );
                                index += 1;
                            }
                            RoomName accuseRoom = RoomName.values()[ Integer.parseInt( reader.read() ) - 1 ];

                            System.out.println("Which weapon would you like to accuse?");
                            index = 1;
                            for ( WeaponType weapon : WeaponType.values() )
                            {
                                System.out.println("\t[" + index + "] " + weapon);
                                index += 1;
                            }
                            WeaponType accuseWeapon = WeaponType.values()[ Integer.parseInt( reader.read() ) - 1 ];

                            SuggestHand accuseHand = new SuggestHand( accuseCharacter, accuseRoom, accuseWeapon );
                            clientApplication.csc.send(new AccuseRequest(clientApplication.UserPlayer.PlayerName, accuseHand));
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

           input = Integer.parseInt( reader.read() );

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

    public class LogPair
    {
        public Color color;
        public String msg;

        LogPair()
        {
            this(Color.BLACK, "");
        }
        LogPair(Color c, String m)
        {
            color = c;
            msg = m;
        }
    }

    Color MEDIUM_GREEN = new Color(66, 105, 47);
    Color PURPLE = new Color(78, 0, 142);

    public ClientSocketComms csc;  // Should be private.... too bad

    ArrayBlockingQueue<StatusUpdate> UpdateQueue;

    static final int UpdateQueueDepth = 10;

    ArrayBlockingQueue<LogPair> LogQueue;
    static final int LogQueueDepth = 10;

    private String ServerIP;
    private int ServerPort;

    private Player UserPlayer;

    private ConsoleInput reader = new ConsoleInput();

    private static Boolean interrupted = false; //Am I busy responding to a request?

    private Board board;

    private boolean ConnectionRequested;  // Wait for a response before asking to connect again

    public int numUpdatesReceived;  // Temp just to show we're getting things

    public Semaphore initialized;

    public boolean activeGame; // True if the game has started

    private boolean initDone;

    private boolean startPlayer;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private Thread logThread;

    public ClueLessClient()  // So we can instantiate a pointer to the object before we start the GUI
    {
        initDone = false;  // Not doing a real init
    }

    public ClueLessClient(String serverIP, int serverPort)
    {
        initDone= true;
        initialized = new Semaphore(0);
        ServerIP = serverIP;
        ServerPort = serverPort;
        UpdateQueue = new ArrayBlockingQueue<StatusUpdate>(UpdateQueueDepth);
        numUpdatesReceived = 0;
        ConnectionRequested = false;
        activeGame = false;
        UserPlayer = new Player();
        startPlayer = true;
        setStartPlayer(false);
        LogQueue = new ArrayBlockingQueue<LogPair>(LogQueueDepth);
    }

    public void init(String serverIP, int serverPort, String playerName)
    {
        if (!initDone)
        {
            initDone = true;
            initialized = new Semaphore(0);
            ServerIP = serverIP;
            ServerPort = serverPort;
            UpdateQueue = new ArrayBlockingQueue<StatusUpdate>(UpdateQueueDepth);
            numUpdatesReceived = 0;
            ConnectionRequested = false;
            activeGame = false;
            UserPlayer = new Player();
            startPlayer = true;  // Flip flop this to initialize the Start Game button properly
            setStartPlayer(false);
            LogQueue = new ArrayBlockingQueue<LogPair>(LogQueueDepth);
            setPlayerName(playerName);
            start();  // Start processing
            setPriority( 10 );

            // Auto-connect to the server, but make sure we're initialized first
            try
            {
                initialized.acquire();  // Sem wait
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            connect();
        }

    }

    public void run()
    {
        logThread = new Thread(this::LogThread);
        logThread.start();
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

    private void LogThread()
    {
        for(;;)
        {
            try
            {
                LogPair log = LogQueue.take();
                pcs.firePropertyChange("LogReceived", null, log);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void Log(Color color, String msg)
    {
        try
        {
            LogQueue.put(new LogPair(color, msg));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addPropertyChangeListener(String val, PropertyChangeListener pcl)
    {
        pcs.addPropertyChangeListener(val, pcl);
    }

    public void removePropertyChangeListener(String val, PropertyChangeListener pcl)
    {
        pcs.removePropertyChangeListener(val, pcl);
    }

    public boolean getActiveGame()
    {
        return activeGame;
    }

    public void setActiveGame(boolean newValue)
    {
        boolean oldValue = activeGame;
        activeGame = newValue;
        this.pcs.firePropertyChange("activeGame", oldValue, newValue);
    }

    public boolean getStartPlayer()
    {
        return startPlayer;
    }

    public void setStartPlayer(boolean newValue)
    {
        boolean oldValue = startPlayer;
        startPlayer = newValue;
        this.pcs.firePropertyChange("startPlayer", oldValue, newValue);
    }

    public String getPlayerName()
    {
        return UserPlayer.PlayerName;
    }

    public void startGame()
    {
        csc.send(new GameStartRequest());
    }

    public void connect()
    {
        if(!ConnectionRequested)
        {
            System.out.println("Sending connect request...\n");
            csc.send(new ConnectRequest(UserPlayer.PlayerName));
            ConnectionRequested = true;
        }
        else
        {
            System.out.println("Connect request pending...\n");
        }
        printPreGameInstructions();
    }

    public void endTurn()
    {
        csc.send(new EndTurn(UserPlayer.PlayerName));
    }

    public RoomCard getRoom()
    {
        return board.getPlayerRoom(UserPlayer);
    }

    public void suggest(SuggestHand suggestHand)
    {
        csc.send(new SuggestRequest(UserPlayer.PlayerName, suggestHand, UserPlayer.xPos, UserPlayer.yPos));
    }

    public void accuse(SuggestHand accuseHand)
    {
        csc.send(new AccuseRequest(UserPlayer.PlayerName, accuseHand));
    }

    public void refute(String to, Card card)
    {
        csc.send( new RefuteSuggestionResponse( UserPlayer.PlayerName, to, card) );
    }

    public void move(MoveRequest.Move m)
    {
        csc.send(new MoveRequest(UserPlayer.PlayerName, m));
    }

    public boolean isTurn()
    {
        return UserPlayer.PlayerTurn;
    }

    public void sendChatMessage(String to, String msg, boolean toAll)
    {
        if(toAll)
        {
            to = "All";
        }
        Log(Color.DARK_GRAY, "[To " + to + "] " + msg);
        csc.send(new ChatFromClient(UserPlayer.PlayerName, to, msg, toAll));
    }

    /**
     * Process status updates sent to the client
     *
     * @param statUp - Received status update
    * @throws TimeoutException
     */
    public void updateStatus(StatusUpdate statUp) throws TimeoutException
    {
        if(statUp instanceof PlayerConnection)  // Another player joined/left the game
        {
            processPlayerConnection((PlayerConnection) statUp);
        }
        else if (statUp instanceof PlayerUpdate)
        {
            for(Player pl : ((PlayerUpdate) statUp).p ){
//                System.out.println(pl.PlayerName);
//                System.out.println(pl.charName);
//                System.out.println(pl.xPos);
//                System.out.println(pl.yPos);
                if(pl.PlayerName.equals(UserPlayer.PlayerName))
                {
                    UserPlayer.xPos = pl.xPos;
                    UserPlayer.yPos = pl.yPos;
                }
            }
            this.board = new Board();
            this.board.putPlayers(((PlayerUpdate) statUp).p);
            this.board.printBoard();
            pcs.firePropertyChange("PlayerUpdate", null, ((PlayerUpdate) statUp).p);
            return;
        }
        else if(statUp instanceof ConnectRequestStatus)  // Response to our join request
        {
            processConnectionRequestStatus((ConnectRequestStatus) statUp);
        }
        else if(statUp instanceof GameStart)  // Starting/ending the game
        {
            processGameStart((GameStart) statUp);
        }
        else if( statUp instanceof TurnUpdate)
        {
            processTurnUpdate((TurnUpdate) statUp);
        }
        else if( statUp instanceof PlayerHandUpdate)
        {
            UserPlayer.setHand( ((PlayerHandUpdate) statUp).getHandUpdate() );
            System.out.println( UserPlayer.getAllCardsString() + "\n");
            pcs.firePropertyChange("PlayerHand", null, UserPlayer.getHand());
            //pcs.firePropertyChange("PlayerTurn", false, UserPlayer.PlayerTurn);  // So first player can select cards
           //System.out.println( ((PlayerHandUpdate) statUp).getHandUpdate() + "\n");
        }
        // These just print stuff, so I guess just leave them
        else if(statUp instanceof Notification)  // Generic server print essentially
        {
            System.out.println("\t[Server] " + ((Notification) statUp).NotificationText);
            Log(Color.BLACK, ((Notification) statUp).NotificationText);
        }
        //Notify all players of a suggestion (who, and what they are suggestion)
        else if( statUp instanceof SuggestNotification)
        {
            System.out.println((SuggestNotification) statUp);
            Log(Color.BLUE, ((SuggestNotification) statUp).toString());
        }
        //Notify this player who refuted and what card they showed
        else if( statUp instanceof RefuteSuggestion)
        {
            System.out.println((RefuteSuggestion) statUp);
            Log(Color.BLUE, ((RefuteSuggestion) statUp).toString());
        }
        // Let the player choose which card to use when refuting a suggestion
        else if( statUp instanceof RefuteSuggestionPicker )
        {
            processRefuteSuggestionPicker( (RefuteSuggestionPicker) statUp );
        }
        //Notify all players that a given player was unable to refute
        else if( statUp instanceof SuggestionPassed)
        {
            System.out.println(((SuggestionPassed) statUp).PlayerName + " was not able to refute the suggestion");
            Log(Color.RED, ((SuggestionPassed) statUp).PlayerName + " was not able to refute the suggestion");
        }
        //Notify all players that a given player was able to refute (WITHOUT THE SPECIFIC CARD)
        else if( statUp instanceof SuggestionWrong)
        {
            System.out.println(((SuggestionWrong) statUp).RefuterName + " was able to refute the suggestion");
            Log(MEDIUM_GREEN, ((SuggestionWrong) statUp).RefuterName + " was able to refute the suggestion");
        }

        // Update the Board object based on updates received from the server
        else if( statUp instanceof BoardUpdate)
        {
            // align client board with server version
            //setBoard(((BoardUpdate) statUp).getBoard());
            //((BoardUpdate) statUp).getBoard().printBoard();
            // display board
            //this.board.printBoard();
        }
        // Notify players about an accusation
        else if (statUp instanceof AccuseNotification)
        {
            processAccuseNotification((AccuseNotification) statUp);
        }
        //This is sent to the player when they accuse
        else if (statUp instanceof EnvelopePeakNotification)
        {
            System.out.println((EnvelopePeakNotification) statUp);
            Log(Color.RED, ((EnvelopePeakNotification) statUp).toString());
        }
        // This is sent to a player to let them know they are out of the game.
        // Do we have to do anything else here?
        else if (statUp instanceof OutNotification)
        {
            System.out.println("You can no longer participate in the game.");
            Log(Color.RED, "You can no longer participate in the game.");
            UserPlayer.PlayerActive = false;
            csc.send(new EndTurn( UserPlayer.PlayerName ));
        }
        else if (statUp instanceof ChatToClient)
        {
            String toAll = ((ChatToClient)statUp).ToAll ? " to all" : "";
            Log(Color.DARK_GRAY, "[" + ((ChatToClient)statUp).SendingPlayer + toAll + "] " + ((ChatToClient)statUp).ChatMessage);
        }


        else  // Something else. Eventually this'll be an error case, but it's fine for now
        {
            System.out.println("\t[Server] Update Status: Received StatusUpdate");
        }
        numUpdatesReceived++;

        return;
    }

    public void processPlayerConnection(PlayerConnection pc)
    {
        if(pc.Connected)
        {
            System.out.println("\t[Server] " + pc.PlayerName + " joined the game!");
            Log(PURPLE, pc.PlayerName + " joined the game!");
        }
        else
        {
            System.out.println("\t[Server] " + pc.PlayerName + " left the game!");
            Log(PURPLE, pc.PlayerName + " left the game!");
        }

        setStartPlayer(pc.StartPlayer.equals(UserPlayer.PlayerName));

    }

    public void processConnectionRequestStatus(ConnectRequestStatus crs)
    {
        if(crs.Joined)
        {
            UserPlayer.PlayerName = crs.PlayerName;  // Think these should always already match
            System.out.println("\t[Server] You are now connected as " + UserPlayer.PlayerName + "!");
        }
        else
        {
            System.out.println("\t[Server] Join request refused");
            csc.send(new ConnectRequestStatus(false, UserPlayer.PlayerName));  // Send refusal acknowledgement
        }
        ConnectionRequested = false;  // We got a response, so we can ask again if we want
    }

    public void processGameStart(GameStart gs)
    {
        if(gs.GameStarting && !activeGame )
        {
            System.out.println("\t[Server] Game starting!\n");
            Log(Color.ORANGE, "Game started!");
            // Only instantiate the board after the game starts
            board = new Board();
            setActiveGame(true);
        }
        else if( gs.GameStarting && activeGame )
        {
           System.out.println("\t[Server] Game has already been started!\n");
        }
        else
        {
            System.out.println("\t[Server] Game ending!\n");
            Log(Color.ORANGE, "Game ending!");
            setActiveGame(false);
        }
    }

    public void processTurnUpdate(TurnUpdate tu)
    {
        setActiveGame(true);  // In case of disconnect and reconnect
        if(tu.TurnPlayer.equals(UserPlayer.PlayerName))
        {
            boolean oldValue = UserPlayer.PlayerTurn;
            UserPlayer.PlayerTurn = true; // Set the turn status to true
//            System.out.println( "\n[Server] It is now your turn.");
//            System.out.println("\n\n****Enter a command****");
//            System.out.println("Move Left: 1");
//            System.out.println("Move Right: 2");
//            System.out.println("Move Up: 3");  // To test sending multiple
//            System.out.println("Move Down: 4");
//            System.out.println("Suggest: 5");
//            System.out.println("End turn: 6");
//            System.out.println("Accuse: 9");
//            System.out.println("Exit: -1");
//            printGameInstructions();
            Log(Color.ORANGE, "It's your turn!");
            pcs.firePropertyChange("PlayerTurn", oldValue, UserPlayer.PlayerTurn);
        }
        else
        {
            boolean oldValue = UserPlayer.PlayerTurn;
            UserPlayer.PlayerTurn = false; // Set the turn status to false
            System.out.println( "\t[Server] It's " + tu.TurnPlayer + "'s turn.\n");
            Log(Color.BLACK, "It's " + tu.TurnPlayer + "'s turn.");
            pcs.firePropertyChange("PlayerTurn", oldValue, UserPlayer.PlayerTurn);
        }
    }


    /**
     * Steals the active thread to get the player to pick a card to refute the
     * suggestion
     * @param rs The specific RefuteSuggestionPicker message with the possible
     *   Cards to use in the refute process
    * @throws TimeoutException
    * @throws InterruptedException
     */
    // LOGGING NOT DONE
    public void processRefuteSuggestionPicker( RefuteSuggestionPicker rs ) throws TimeoutException
    {
        // Leaving this in in case anyone needs to go back to the text-based for something
        /* Tells the client to assume an interrupted state so that it
         * disregards user input for all other things.
         */
        /*interrupted = true;

        System.out.println( "\n\t[Server] You have multiple cards that can refute the"
           + " suggestion; please pick one:");

        /* I think I want to move this level of processing elsewhere (maybe another
         * object type. Not sure yet. We want to blend all the cards from the
         * hand into one arraylist for easier iterations.
         *//*
        ArrayList<Card> cardChoices = new ArrayList<Card>();

        cardChoices.addAll( rs.getHand().getCharacters() );
        cardChoices.addAll( rs.getHand().getRooms() ) ;
        cardChoices.addAll( rs.getHand().getWeapons() );

        for( int i = 1; i <= cardChoices.size(); i++ )
        {
           System.out.print( "[" + i + "] " );
           if( cardChoices.get( i - 1 ) instanceof CharacterCard )
           {
              System.out.println( ((CharacterCard) cardChoices.get( i - 1 )).getCharacterName() );
           }
           else if( cardChoices.get( i - 1 ) instanceof RoomCard )
           {
              System.out.println( ((RoomCard) cardChoices.get( i - 1 )).getRoomName() );
           }
           else
           {
              System.out.println( ((WeaponCard) cardChoices.get( i - 1 )).getWeaponType() );
           }

        }

        // In case of errors, defaults to sending the first card found
        int userInput = 1;

        try
        {
           userInput = Integer.parseInt( reader.read() );
        }
        catch( NumberFormatException e )
        {
           e.printStackTrace();
        }
        catch( IOException e )
        {
           e.printStackTrace();
        }


//        System.out.println("About to send...");
        csc.send( new RefuteSuggestionResponse( UserPlayer.PlayerName, rs.getPlayer(), cardChoices.get( userInput - 1 ) ) );


        interrupted = false; // All done!

        return;*/
        pcs.firePropertyChange("RefuteSuggestionPicker", null, rs);

    }

    public void processAccuseNotification(AccuseNotification accuseNotification)
    {
        // Tell them about the accusation
        System.out.println( accuseNotification );
        // If they (or you) are correct
        if ( accuseNotification.Correct )
        {
            System.out.println("\n\t[Server] That was correct!");
            Log(MEDIUM_GREEN, accuseNotification.PlayerName + " was correct!");
            activeGame = false;
            pcs.firePropertyChange("EndGame", null, accuseNotification );
        }
        // If incorrect
        else
        {
            System.out.println("\n\t[Server] That was incorrect!\n\t");
            System.out.println(accuseNotification.PlayerName + " was wrong! They are out of the game!\n");
            Log(Color.RED, accuseNotification.PlayerName + " was wrong! They are out of the game!");
        }
    }

    /**
     * CSC just received something from the server. Parse it and pass it to where it needs to go
     *
     * @param msg - The message received from the server
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
     * Prints the lobby instructions for the player.
     */
    public static void printPreGameInstructions()
    {
       System.out.println("****Enter a command****");
       System.out.println("Send action request to server: 1");
       System.out.println("See total num received status updates: 2");
       System.out.println("Send Connect Request: 3");  // To test sending multiple
       System.out.println("Request Start Game: 4");
       System.out.println("Exit: -1\n");
    }


    /**
     * Prints the game play instructions for the player.
     */
    public static void printGameInstructions()
    {
       System.out.println( "\n[Server] It is now your turn.");
       System.out.println("\n\n****Enter a command****");
       System.out.println("Move Left: 1");
       System.out.println("Move Right: 2");
       System.out.println("Move Up: 3");  // To test sending multiple
       System.out.println("Move Down: 4");
       System.out.println("Take Shortcut: 5");
       System.out.println("Suggest: 6");
       System.out.println("End turn: 7");
       System.out.println("Accuse: 9");
       System.out.println("Exit: -1\n");
   }


    /**
     * Kill the client
     */
    public void close()
    {
        csc.close();  // Kill server comms
        interrupt();  // Then kill our action processing
    }

    // treating board as
    public void setBoard(Board board)
    {
        this.board = board;
    }

}
