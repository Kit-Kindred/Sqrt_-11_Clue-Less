package Common;

/**
 * INCOMPLETE
 *
 * @author Kit Kindred & Steve Nilla
 *
 */
public class Player
{
    public boolean PlayerActive;  // true if the player is in; false if the player is out
    public boolean PlayerConnected;  // true if the player is connected; false otherwise
    public boolean PlayerTurn; // True is it's the player's turn. This should be set by the server.
    
    public int PlayerNumber;  // Unique playerID assigned by the server. Could probably allow the user to set this and make it a string
    public int ClientID; // Maps players to clients (Since a player persists in the server after the client disconnects)

    public int xPos; // x position of player on the board
    public int yPos; // y position of player on the board
    
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

    /**
     * Set the x position of the player
     * 
     * @param xPos
     */
    public void setxPos(int xPos) throws IllegalArgumentException
    {
        if ((xPos >= 0) && (xPos <= 4))
        {
            this.xPos = xPos;
        }
        else
        {
            throw new IllegalArgumentException("Invalid Move");
        }
    }

    /** 
     * Get the x position of the player
     * 
     * @return xPos
     */
    public int getxPos() 
    {
        return xPos;
    }

    /**
     * Set the y position of the player
     * 
     * @param yPos
     */
    public void setyPos(int yPos) throws IllegalArgumentException
    {
        if ((yPos >= 0) && (yPos <= 4))
        {
            this.yPos = yPos;
        }
        else
        {
            throw new IllegalArgumentException("Invalid Move");
        }
    }

    /**
     * Get the y position of a player
     * 
     * @return yPos
     */
    public int getyPos() 
    {
        return yPos;
    }
}
