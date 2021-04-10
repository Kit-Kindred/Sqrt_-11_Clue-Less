package Server;

import Common.*;
import Common.WeaponCard.WeaponType;
import Common.CharacterCard.CharacterName;
import Common.RoomCard.RoomName;
import Common.Messages.*;
import Common.Messages.ActionRequests.ActionRequest;
import Common.Messages.ActionRequests.ConnectRequest;
import Common.Messages.ActionRequests.GameStartRequest;
import Common.Messages.ActionRequests.MoveRequest;
import Common.Messages.ActionRequests.SuggestRequest;
import Common.Messages.StatusUpdates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        // TODO: Error-check this
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

    /**
     * What ID we'll give the next player to join
     */
    private int NextPlayerNumber;


    /**
     * The index of the player who's turn it is currently. This index corresponds to the
     * PlayerList ArrayList.
     */
    private int CurrentPlayerIndex;

    /**
     * Initialize the ClueLessServer. Set up game state
     */
    public ClueLessServer(int port)
    {
        ServerPort = port;
        ServState = ServerState.Lobby;  // When server starts, it's in the lobby state
        NextPlayerNumber = 1;  // One-index players
    }


    /**
     * Main processing thread. Start the ServerSocketComms, then wait
     * until something starts asking us to do something
     */
    public void run()
    {
        ssc = new ServerSocketComms(ServerPort, this::recvCallback, this::onClientDisconnect);  // Initialize SSC
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
     * TODO: Maybe complete for now?
     *
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
                    sendToAllPlayers(new PlayerConnection(dc.PlayerName, false));  // Let everyone know
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
                        sendToAllPlayers(new PlayerConnection(p.PlayerName, false));  // Let everyone know
                    }
                }
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
        if(actionRequest instanceof ConnectRequest)  // Process this one regardless of turn order
        {
            processConnectRequest((ConnectRequest) actionRequest);
        }
        if(actionRequest instanceof GameStartRequest)
        {
            processGameStartRequest((GameStartRequest) actionRequest);
        }

        if(actionRequest instanceof MoveRequest)
        {
           processMoveRequest((MoveRequest) actionRequest);
        }

        if(actionRequest instanceof SuggestRequest)
        {
            processSuggestRequest((SuggestRequest) actionRequest);
        }
        // TODO: Actual in-game action requests
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
                            // TODO: Update the player on the current status of the game
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
                    sendToAllPlayers(new PlayerConnection(cr.PlayerName, true));  // Notify existing players
                    PlayerList.add(new Player(cr.PlayerName, cr.UniqueID));
                    sendToClient(cr.UniqueID, new ConnectRequestStatus(true, cr.PlayerName));  // Let player know they're in
                    NextPlayerNumber++;
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
    public void processGameStartRequest(GameStartRequest gsr)
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
                    CardDeck.shuffleAndAssignCards( PlayerList );

                    sendToClient( PlayerList.get( CurrentPlayerIndex ).ClientID, new TurnUpdate(true) );


                    // Send the player hands to their respective clients
                    for( Player p : PlayerList )
                    {
                       //System.out.println( p.PlayerName + " " + p.getHand().toString() );
                       sendToClient( p.ClientID, new PlayerHandUpdate( p.getHand() ) );
                    }

                }
                else
                {
                    sendToClient(gsr.UniqueID, new Notification("Need at least " + MinPlayers + " players!"));
                }
            }
            else
            {
                sendToClient(gsr.UniqueID, new Notification("Only " + PlayerList.get(0).PlayerName + " can start the game"));
            }
        }
        else
        {
            sendToClient(gsr.UniqueID, new Notification("Game in-progress!"));
        }
    }


    public void processMoveRequest( MoveRequest mr )
    {
       /* First check to see if the action request is from the player who holds the
        * active turn status...
        */
       if( PlayerList.get( CurrentPlayerIndex ).PlayerName.equals(mr.PlayerName))
       {

          // TODO: update player location

          // Announce the move to all players
          sendToAllPlayers( new Notification("Player " + mr.PlayerName
             + " moved " + mr.moveDirection) );


          // Update player turn status, and move to next player
          sendToPlayer( PlayerList.get( CurrentPlayerIndex ).PlayerName, new TurnUpdate( false ) );
          nextPlayer();
          sendToPlayer( PlayerList.get( CurrentPlayerIndex ).PlayerName, new TurnUpdate( true ) );
       }

    }

    public void processSuggestRequest( SuggestRequest sr )
    {
        if( PlayerList.get( CurrentPlayerIndex ).PlayerName.equals(sr.PlayerName))
        {
            // TODO - Extend to give players choice of which card to use to refute

            sendToAllPlayers( new SuggestNotification( sr ) );

            // TODO - update to start at the next player in the list
            for (Player p : PlayerList )
            {

                if ( p.getCharacterCards().contains(sr.Hand.getCharacters() ) )
                {
                    sendToPlayer( PlayerList.get ( CurrentPlayerIndex ).PlayerName, new RefuteSuggestion(p.PlayerName, sr.Hand.getCharacters() ) );
                    sendToAllPlayers( new SuggestionWrong( sr.PlayerName, p.PlayerName ) );
                    break;
                }
                else if ( p.getRoomCards().contains(sr.Hand.getRooms() ) )
                {
                    sendToPlayer( PlayerList.get ( CurrentPlayerIndex ).PlayerName, new RefuteSuggestion(p.PlayerName, sr.Hand.getRooms() ) );
                    sendToAllPlayers( new SuggestionWrong( sr.PlayerName, p.PlayerName ) );
                    break;
                }
                else if ( p.getWeaponCards().contains(sr.Hand.getWeapons() ) )
                {
                    sendToPlayer( PlayerList.get ( CurrentPlayerIndex ).PlayerName, new RefuteSuggestion(p.PlayerName, sr.Hand.getWeapons() ) );
                    sendToAllPlayers( new SuggestionWrong( sr.PlayerName, p.PlayerName ) );
                    break;
                }
                else
                {
                    sendToAllPlayers( SuggestionPassed( sr.PlayerName, p.PlayerName ) );
                }
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
     * Smarter way to increment the current player index so that we don't run
     * into out of bounds errors.
     */
    public void nextPlayer()
    {
       if( CurrentPlayerIndex >= PlayerList.size() - 1 )
       {
          CurrentPlayerIndex = 0;
       }

       else
       {
          CurrentPlayerIndex ++;
       }
    }



    /**
     * Alright, game's over. Kill the server
     */
    public void close()
    {
        ssc.close();  // Kill server comms
        interrupt();  // Then kill our action processing
    }



    /**
     * Inner class where the game logic can be isolated. I haven't come up with a
     * great way to incorporate this yet.
     */
//    class GameDriver
//    {
//
//       /**
//        * Boolean to keep track of when the game is over. If the game logic gets too
//        * complicated, we could always default to using break statements.
//        */
//       private boolean ongoingGame;
//
//       /**
//        * The index of the player who's turn it is currently. This index corresponds to the
//        * PlayerList ArrayList.
//        */
//       private int CurrentPlayerIndex;
//
//
//
//       public GameDriver( ArrayList<Player> Players )
//       {
//          this.ongoingGame = true;
//          startGame( Players );
//       }
//
//
//       /**
//        * Game driving logic goes here for the most part
//        */
//       public void startGame( ArrayList<Player> Players )
//       {
//          while( ongoingGame )
//          {
//             sendToClient( Players.get( this.CurrentPlayerIndex ).ClientID, new TurnUpdate(true) );
//
//             this.CurrentPlayerIndex ++;
//          }
//
//       }
//    }

}
