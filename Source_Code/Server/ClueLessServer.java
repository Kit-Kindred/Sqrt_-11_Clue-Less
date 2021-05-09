package Server;

import Common.*;
import Common.Messages.ActionRequests.*;
import Common.WeaponCard.WeaponType;
import Common.CharacterCard.CharacterName;
import Common.RoomCard.RoomName;
import Common.Messages.*;
import Common.Messages.StatusUpdates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * This is the main server application. It initializes ServerSocketComms, then waits to receive ActionRequests.
 * It will then serially handle action requests in the order they are received.
 *
 * @author Kit Kindred
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
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter server port");
        int port = scan.nextInt();
        ClueLessServer serverApplication = new ClueLessServer(port);  // Create the server
        serverApplication.start();  // Start processing

        // BEGIN TEMPORARY - Just to let us test out the interface
        // Read user input from CLI and perform appropriate action;
        int input;
        do
        {
            System.out.println("****Enter a command****");
            System.out.println("Send status to all clients: 1");
            System.out.println("Get number of connected clients: 2");
            System.out.println("Get number of total players: 3");
            System.out.println("Get number of connected players: 4");
            System.out.println("Exit: -1");
            input = scan.nextInt();
            switch (input)
            {
                case 1 ->
                        {
                            System.out.println("Sending status...\n");
                            serverApplication.sendToAllPlayers(new StatusUpdate());
                        }
                case 2 ->
                        {
                            System.out.println("Currently connected clients: " + serverApplication.ssc.getNumConnectedClients() + "\n");
                        }
                case 3 ->
                        {
                            System.out.println("Total players: " + serverApplication.PlayerList.size() + "\n");
                        }
                case 4 ->
                        {
                            int ConnectedPlayers = 0;
                            for(Player p : serverApplication.PlayerList)
                            {
                                if (p.PlayerConnected)
                                {
                                    ConnectedPlayers++;
                                }
                            }
                            System.out.println("Connected Players: " + ConnectedPlayers + "\n");
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

    private enum ServerState
    {
        Lobby,
        ActiveGame
    }

    /**
     * Max number of activities that can be queued simultaneously. May have to be increased later
     */
    private static final int ActionQueueDepth = 10;

    private static final int MaxPlayers = 6;
    private static final int MinPlayers = 3;

    /**
     * Main request queue that the main processing loop waits on
     */
    private final ArrayBlockingQueue<ActionRequest> ActionQueue = new ArrayBlockingQueue<ActionRequest>(ActionQueueDepth);

    /**
     * List of players in the game. In lobby mode, we can add to and remove from this.
     * Once the game has started, players stay static.
     */
    private final ArrayList<Player> PlayerList = new ArrayList<Player>();

    /**
     * The server applications underlying network interface
     */
    private ServerSocketComms ssc;

    /**
     * Port the server binds to
     */
    private final int ServerPort;

    /**
     * Current state of the server (Lobby/Active)
     */
    private ServerState ServState;

    private final Semaphore BoardUpdateSem = new Semaphore(0);
    private Thread boardUpdateThread;

    private final TurnTracker turnTracker;

    private final Board board = new Board();

    /**
     * The index of the player who's turn it is currently. This index corresponds to the
     * PlayerList ArrayList.
     */
    private int CurrentPlayerIndex;

    /**
    * The solution sealed in the envelope before the start of the Game
    */
    private SuggestHand EnvelopeHand;

    /**
     * Initialize the ClueLessServer. Set up game state
     */
    public ClueLessServer(int port)
    {
        ServerPort = port;
        ServState = ServerState.Lobby;  // When server starts, it's in the lobby state
        turnTracker = new TurnTracker();
        CurrentPlayerIndex = 0;
    }


    /**
     * Main processing thread. Start the ServerSocketComms, then wait
     * until something starts asking us to do something
     */
    public void run()
    {
        ssc = new ServerSocketComms(ServerPort, this::recvCallback, this::onClientDisconnect);  // Initialize SSC

        // More threads
        boardUpdateThread = new Thread(this::boardUpdateThreadFunc);
        boardUpdateThread.start();

        // Start message listener
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

    public void boardUpdateThreadFunc()
    {
        for(;;)  // Go, go, go
        {
            try
            {

                BoardUpdateSem.acquire();  // Wait for someone to ask us to update the board. Which is actually sending out player updates. Go figure
                synchronized (PlayerList)  // Synchronization on this is really half-assed throughout. Oh well
                {
                    sendToAllPlayers(new PlayerUpdate(PlayerList));
                    System.out.println("Sent board");
                }
            }
            catch(InterruptedException e)
            {
                return;  // Someone called close; time to die
            }
        }
    }

    /**
     * SSC just received something from a client. Parse it and pass it to where it needs to go
     *
     * @param msg - The message received from a client
     */
    public void recvCallback(Message msg)
    {
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
        // We rejected a client. That client has acknowledged that rejection
        // Maybe this eventually gets replaced by a disconnect callback in the client?
        if(msg instanceof ConnectRequestStatus && !((ConnectRequestStatus) msg).Joined)
        {
            ssc.disconnectClient(msg.UniqueID);  // Axe the client
        }
    }

    /**
     * A client just disconnected. Could have been initiated from either side
     *
     * @param uid - The UniqueID of the client that disconnected
     */
    public void onClientDisconnect(int uid)
    {
        if(ServState == ServerState.Lobby)  // Players can leave in the lobby
        {
            synchronized (PlayerList)
            {
                Player dc = null;
                for(Player p : PlayerList)  // This client a player?
                {
                    if(p.ClientID == uid)  // Yup
                    {
                        dc = p;
                    }
                }
                if(dc != null)
                {
                    PlayerList.remove(dc);
                    sendToAllPlayers(new PlayerConnection(dc.PlayerName, false, PlayerList.get(0).PlayerName, PlayerList.size()));  // Let everyone know
                }
            }
        }
        else  // Leave the player after the game starts. We still need to know what cards they have
        {
            synchronized (PlayerList)
            {
                for(Player p : PlayerList)  // This client a player?
                {
                    if(p.ClientID == uid)  // Yup
                    {
                        p.PlayerConnected = false;
                        sendToAllPlayers(new PlayerConnection(p.PlayerName, false, PlayerList.get(0).PlayerName, PlayerList.size()));  // Let everyone know
                    }
                }
            }
        }
    }


    /**
     * This gets called serially from the main ClueLessServer thread everytime we add something to the action queue
     *
     * @param actionRequest - The action that we have been requested to perform
     */
    public void processAction(ActionRequest actionRequest)
    {

        System.out.println("Process Action: Received ActionRequest (" + actionRequest.getClass() + ")");
        if(actionRequest instanceof ConnectRequest)  // Process this one regardless of turn order
        {
            processConnectRequest((ConnectRequest) actionRequest);
        }
        if(actionRequest instanceof ChatFromClient)
        {
            processChatFromClient((ChatFromClient) actionRequest);
        }
        boolean newGame = false;
        for(Player p : PlayerList)  // Only process non-connection requests from players
        {
            if(actionRequest.UniqueID == p.ClientID)
            {
                if(actionRequest instanceof GameStartRequest)
                {
                    newGame = processGameStartRequest((GameStartRequest) actionRequest);
                }

                else if(actionRequest instanceof RefuteSuggestionResponse)
                {
                    processRefuteSuggestionResponse((RefuteSuggestionResponse) actionRequest);
                }

                else if(PlayerList.get( CurrentPlayerIndex ).PlayerName.equals(p.PlayerName))  // Only the player who's turn it is can do these
                {
                    if(actionRequest instanceof MoveRequest)
                    {
                       processMoveRequest((MoveRequest) actionRequest);
                    }
                    else if(actionRequest instanceof SuggestRequest)
                    {
                        processSuggestRequest((SuggestRequest) actionRequest);
                    }
                    else if(actionRequest instanceof EndTurn || turnTracker.isTurnOver())  // isTurnOver is basically always false since nobody's accusing most of the time
                    {
                        nextPlayer();
                    }
                    else if (actionRequest instanceof AccuseRequest)
                    {
                        processAccuseRequest((AccuseRequest) actionRequest);
                    }
                    break;
                }
            }
        }

        if (newGame)
        {
            fillPlayers();
        }
    }

    public void processChatFromClient(ChatFromClient cfc)
    {
        for (Player p : PlayerList)
        {
            if(cfc.ToAll)
            {
                if(!p.PlayerName.equals("") && !p.PlayerName.equals(cfc.PlayerName))
                {
                    sendToPlayer(p.PlayerName, new ChatToClient(cfc.PlayerName, cfc.ChatMessage, true));
                }
            }
            else if (p.PlayerName.equals(cfc.DestinationPlayer))
            {
                sendToPlayer(p.PlayerName, new ChatToClient(cfc.PlayerName, cfc.ChatMessage, false));
                break;
            }
        }
    }

    /**
     * A connected client wants to join the game
     *
     * @param cr - Message that contains info about the joining client
     */
    public void processConnectRequest(ConnectRequest cr)
    {
        if(!cr.PlayerName.equals(""))
        {
            synchronized (PlayerList)
            {
                for (Player p : PlayerList)  // Is this a reconnecting player?
                {
                    if(cr.PlayerName.equals(p.PlayerName))
                    {
                        if(!p.PlayerConnected)
                        {
                            p.ClientID = cr.UniqueID;  // Assign the new client to the existing player
                            p.PlayerConnected = true;  // Player is connected again
                            sendToPlayer(p.PlayerName, new ConnectRequestStatus(true, p.PlayerName));  // Let player know they're in
                        }
                        else
                        {
                            sendToClient(p.ClientID, new Notification("You're already connected!"));
                        }
                        return;
                    }
                }
            }
            if (ServState == ServerState.Lobby && PlayerList.size() < MaxPlayers)  // Not an existing player; can join
            {
                synchronized (PlayerList)
                {
                    // Yay, new player!
                    sendToAllPlayers(new PlayerConnection(cr.PlayerName, true, PlayerList.size() > 0 ? PlayerList.get(0).PlayerName : cr.PlayerName, PlayerList.size()));  // Notify existing players
                    PlayerList.add(new Player(cr.PlayerName, cr.UniqueID));
                    sendToClient(cr.UniqueID, new ConnectRequestStatus(true, cr.PlayerName));  // Let player know they're in
                    return;
                }
            }
        }
        sendToClient(cr.UniqueID, new ConnectRequestStatus(false, ""));  // Couldn't join
    }

    /**
     * Process a request to start the game
     *
     * @param gsr - Request
     */
    public boolean processGameStartRequest(GameStartRequest gsr)
    {
        if(ServState == ServerState.Lobby)
        {
            if(PlayerList.size() > 0 && gsr.UniqueID == PlayerList.get(0).ClientID)
            {
                if(PlayerList.size() >= MinPlayers)
                {
                    ServState = ServerState.ActiveGame;
                    sendToAllPlayers(new GameStart());

                    // Update player objects with all the cards in the deck
                    EnvelopeHand = CardDeck.shuffleAndAssignCards( PlayerList );
                    
                    
                    /*  Enable God Mode
                     *  Eliminate all the guess work!!
                     */
                    System.out.println( EnvelopeHand.getCharacterName() + "\n" +
                       EnvelopeHand.getRoomName() + "\n" + 
                       EnvelopeHand.getWeaponType());
                    
                    // Notify the players of the first turn
                    sendToAllPlayers( new TurnUpdate(PlayerList.get( CurrentPlayerIndex ).PlayerName) );
                    
                    // Send the player hands to their respective clients
                    for( Player p : PlayerList )
                    {
                       //System.out.println( p.PlayerName + " " + p.getHand().toString() );
                       sendToClient( p.ClientID, new PlayerHandUpdate( p.getHand() ) );
                    }

                    return true;
                }
                else
                {
                    sendToClient(gsr.UniqueID, new Notification("Need at least " + MinPlayers + " players!"));
                    return false;
                }
            }
            else
            {
                sendToClient(gsr.UniqueID, new Notification("Only " + PlayerList.get(0).PlayerName + " can start the game"));
                return false;
            }
        }
        else
        {
            sendToClient(gsr.UniqueID, new Notification("Game in-progress!"));
            return false;
        }
    }

    public void fillPlayers()
    {
        // Fill in non-players, assign characters to players. After cards are dealt, so hopefully OK
        int toAdd = MaxPlayers - PlayerList.size();
        for (int i = 0; i < toAdd; i++)
        {
            PlayerList.add(new Player("", -13, false, false));
        }
        for(int i = 0; i < PlayerList.size(); i++)
        {
            PlayerList.get(i).assignCharacter(CharacterName.values()[i]);
        }
        System.out.println("Filled players " + PlayerList.size());
        board.putPlayers(PlayerList);
        for (Player p : PlayerList)
        {
            if(!p.PlayerName.equals(""))
            {
                sendToAllPlayers(new Notification(p.PlayerName + " is " + p.getCharacterName()));
            }
        }
        BoardUpdateSem.release();  // Send out a board update
    }

    public void processMoveRequest( MoveRequest mr )
    {
        // processAction now checks if the sending player is allowed to move
        if(turnTracker.CanMove)
        {   
            // try to move player
            try 
            {
                // get current player instance
                Player player = PlayerList.get(CurrentPlayerIndex);
                
                // update player location on the board
                board.movePlayer(player, mr);

                // toggle to next player
                turnTracker.move();

                // Announce the move to all players
                sendToAllPlayers(new Notification( mr.PlayerName
                + " moved " + mr.moveDirection));

                // Send the new board layout to all of the players
//                for( Player p : PlayerList )
//                {
//                    //sendToClient( p.ClientID, new BoardUpdate( board ) );
//                    //sendToAllPlayers(new PlayerUpdate(PlayerList));
//                    System.out.println(p.PlayerName);
//                    System.out.println(p.charName);
//                    System.out.println(p.xPos);
//                    System.out.println(p.yPos);
//                }

                //sendToAllPlayers( new PlayerUpdate(PlayerList) );
                //board.printBoard();
                BoardUpdateSem.release();
            }
            // illegal move attempted
            catch (IllegalArgumentException e)
            {
                sendToClient(mr.UniqueID, new Notification("Sorry, you cannot move there."));
            }       
        }
        else
        {
            sendToClient(mr.UniqueID, new Notification("You've already moved this turn!"));
        }
    }

    public void processSuggestRequest( SuggestRequest sr )
    {
        if(turnTracker.CanSuggest)
        {
            turnTracker.suggest();
            // processAction now checks if the sending player is allowed to move
            // right now we are just sending back the first refutation

            sendToAllPlayers( new SuggestNotification( sr ) );

            movePlayerByName(sr.Hand.character.getCharacterEnum(), sr.xPos, sr.yPos);

            for (int i = 1; i < PlayerList.size(); i++)  // Start at 1 to start with the next player. Don't check the current player
            {
                Player p = PlayerList.get((CurrentPlayerIndex + i) % PlayerList.size());
                if(!p.PlayerName.equals(""))  // Skip dummy players
                {
                    PlayerHand possibleRefutations = sr.checkRefutations(p.getHand());


                    if (!possibleRefutations.isEmpty()) {

                        sendToAllPlayers(new PlayerUpdate(PlayerList));

                        System.out.println(possibleRefutations);
                        sendToPlayer(p.PlayerName, new RefuteSuggestionPicker(sr.PlayerName, possibleRefutations));

                        System.out.println(possibleRefutations);

                        // Tell all other players
                        sendToAllPlayers(new SuggestionWrong(sr.PlayerName, p.PlayerName));

                        // We found a player who can refute the suggestion, so we're done here.
                        break;
                    } else {
                        sendToAllPlayers(new PlayerUpdate(PlayerList));
                        sendToAllPlayers(new SuggestionPassed(p.PlayerName));
                    }
                }
            }
            // If it comes back to us, do we lie or do nothing? I think do nothing is better?
            //sendToAllPlayers(new SuggestionPassed( PlayerList.get(CurrentPlayerIndex).PlayerName));

        }
        else
        {
            sendToClient(sr.UniqueID, new Notification("You've already suggested this turn!"));
        }
    }
    
    
    public void processRefuteSuggestionResponse( RefuteSuggestionResponse rsr )
    {
       System.out.println("Received Refute Suggest Response from " + rsr.PlayerName );
       sendToPlayer( rsr.getToPlayer(), new RefuteSuggestion( rsr.PlayerName, rsr.getCard() ) );
       
    }
    
    
    public void processAccuseRequest( AccuseRequest accuseRequest )
    {
        // prep the AccuseNotification here with who and what they are guessing.
        AccuseNotification accuseNotification = new AccuseNotification(accuseRequest.PlayerName, accuseRequest.AccuseHand);

        // Check to see if the person is correct
        if (EnvelopeHand.isEqual(accuseRequest.AccuseHand))
        {
            // If correct, tell everyone, end game
            accuseNotification.setCorrect(true);
            sendToAllPlayers( accuseNotification );
            sendToAllPlayers( new GameStart(false) );
            ServState = ServerState.Lobby;
        }
        else
        {
            // If incorrect
            accuseNotification.setCorrect(false);
            // Set player to out
            PlayerList.get(CurrentPlayerIndex).PlayerActive = false;

            // Tell everyone what happend
            sendToAllPlayers( accuseNotification );
            // Tell the player who accused the correct answer
            sendToPlayer( PlayerList.get ( CurrentPlayerIndex ).PlayerName, new EnvelopePeakNotification( this.EnvelopeHand ) );
            // Tell the player who accused (incorrectly) that their "Out"
            sendToPlayer( PlayerList.get ( CurrentPlayerIndex ).PlayerName, new OutNotification( PlayerList.get ( CurrentPlayerIndex ).PlayerName ) );
        }
    }

    public void movePlayerByName(CharacterName cn, int x, int y)
    {
        for (Player p : PlayerList){
            if(p.charName == cn)
            {
                p.xPos = x;
                p.yPos = y;
                board.putPlayer(p);
                BoardUpdateSem.release();
            }
        }
    }

    /**
     * Send a message to a specific player
     *
     * @param player - The player to message
     * @param msg - What we're sending
     * @return Whether or not the send was successful
     */
    public boolean sendToPlayer(String player, Message msg)
    {
        for (Player p : PlayerList)  // Find the player
        {
            if(player.equals(p.PlayerName) && p.PlayerConnected)  // Don't try to send to someone not connected
            {
                return ssc.sendToClient(p.ClientID, msg);
            }
        }
        return false;
    }

    /**
     * Send a message to a specific client
     *
     * @param clientID - The client to send to
     * @param msg - What we're sending
     * @return Whether or not the send was successful
     */
    public boolean sendToClient(int clientID, Message msg)
    {
        return ssc.sendToClient(clientID, msg);
    }

    /**
     * Send a message to every connected player
     *
     * @param msg - What we're sending
     * @return Whether or not every send was successful
     */
    public boolean sendToAllPlayers(Message msg)
    {
        boolean retVal = true;
        for (Player p : PlayerList)
        {
            if(p.PlayerConnected)  // Don't try to send to someone not connected
            {
                boolean tempStatus = ssc.sendToClient(p.ClientID, msg);
                if(!tempStatus)
                {
                    retVal = false;  // If one fails, the send is marked as fail. Continue the send though
                }
            }
        }
        return retVal;
    }


    /**
     * Increment the turn counter and let players know who's turn it is
     */
    public void nextPlayer()
    {
        CurrentPlayerIndex = (++CurrentPlayerIndex % PlayerList.size());
        if(PlayerList.get( CurrentPlayerIndex ).PlayerConnected && PlayerList.get( CurrentPlayerIndex ).PlayerActive)
        {
            sendToAllPlayers(new TurnUpdate( PlayerList.get( CurrentPlayerIndex ).PlayerName ) );
            turnTracker.reset();
        }
        else  // If a player is out or DC'd, skip them
        {
            nextPlayer();
        }
    }

    /**
     * Alright, game's over. Kill the server
     */
    public void close()
    {
        ssc.close();  // Kill server comms
        boardUpdateThread.interrupt();  // Kill the board updates
        interrupt();  // Then kill our action processing
    }
}
