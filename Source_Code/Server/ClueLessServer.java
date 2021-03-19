package Server;

import Common.*;
import Common.Messages.*;
import Common.Messages.ActionRequests.ActionRequest;
import Common.Messages.ActionRequests.ConnectRequest;
import Common.Messages.ActionRequests.GameStartRequest;
import Common.Messages.StatusUpdates.*;

import java.util.ArrayList;
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
                    sendToAllPlayers(new PlayerConnection(dc.PlayerNumber, false));  // Let everyone know
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
                        sendToAllPlayers(new PlayerConnection(p.PlayerNumber, false));  // Let everyone know
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
        // TODO: Actual in-game action requests
    }


    /**
     * A connected client wants to join the game
     *
     * @param cr - Message that contains info about the joining client
     */
    public void processConnectRequest(ConnectRequest cr)
    {
        if(cr.PlayerID != -1)  // Potentially reconnecting client; allowed any time
        {
            synchronized (PlayerList)
            {
                for (Player p : PlayerList)
                {
                    if(cr.PlayerID == p.PlayerNumber)  // If a connected player resends this, just update anyway
                    {
                        p.ClientID = cr.UniqueID;  // Assign the new client to the existing player
                        p.PlayerConnected = true;  // Player is connected again
                        sendToPlayer(p.PlayerNumber, new ConnectRequestStatus(true, p.PlayerNumber));  // Let player know they're in
                        return;
                    }
                }
            }
        }
        else  // New connection attempt
        {
            if (ServState == ServerState.Lobby && PlayerList.size() < MaxPlayers)  // Can join right now
            {
                synchronized (PlayerList)
                {
                    // Yay, new player!
                    sendToAllPlayers(new PlayerConnection(NextPlayerNumber, true));  // Notify existing players
                    PlayerList.add(new Player(NextPlayerNumber, cr.UniqueID));
                    sendToPlayer(NextPlayerNumber, new ConnectRequestStatus(true, NextPlayerNumber));  // Let player know they're in
                    NextPlayerNumber++;
                }
            }
            else  // Can't join; reject the request
            {
                sendToClient(cr.UniqueID, new ConnectRequestStatus(false, -1));
            }
        }
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
                }
                else
                {
                    sendToClient(gsr.UniqueID, new Notification("Need at least " + MinPlayers + " players!"));
                }
            }
            else
            {
                sendToClient(gsr.UniqueID, new Notification("Only player " + PlayerList.get(0).PlayerNumber + " can start the game"));
            }
        }
        else
        {
            sendToClient(gsr.UniqueID, new Notification("Game in-progress!"));
        }
    }

    /**
     * Send a message to a specific player
     *
     * @param player - The playerID to message
     * @param msg - What we're sending
     * @return Whether or not the send was successful
     */
    public boolean sendToPlayer(int player, Message msg)
    {
        for (Player p : PlayerList)  // Find the player
        {
            if(player == p.PlayerNumber && p.PlayerConnected)  // Don't try to send to someone not connected
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
     * Alright, game's over. Kill the server
     */
    public void close()
    {
        ssc.close();  // Kill server comms
        interrupt();  // Then kill our action processing
    }
}
