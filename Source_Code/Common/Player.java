package Common;

/**
 * INCOMPLETE
 *
 * @author Kit Kindred
 *
 */
public class Player
{
    public boolean PlayerActive;  // true if the player is in; false if the player is out
    public boolean PlayerConnected;  // true if the player is connected; false otherwise
    public boolean PlayerTurn; // True is it's the player's turn. This should be set by the server.
    
    public int PlayerNumber;  // Unique playerID assigned by the server. Could probably allow the user to set this and make it a string
    public int ClientID; // Maps players to clients (Since a player persists in the server after the client disconnects)

    
    
    public Player()
    {
        this(-1, -1);
    }

    public Player(int playerNumber, int clientID)
    {
        PlayerNumber = playerNumber;
        ClientID = clientID;
        PlayerActive = true;
        PlayerConnected = true;
        PlayerTurn = false;
    }
    
    
    public void setPlayerTurn( boolean turn )
    {
       this.PlayerTurn = turn;
    }
    
    
}
