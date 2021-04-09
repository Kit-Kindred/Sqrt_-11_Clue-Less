package Common;

import java.util.ArrayList;

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
    
    //public int PlayerNumber;  // Unique playerID assigned by the server. Could probably allow the user to set this and make it a string
    public String PlayerName;  // A client puts its name in the connection request. Server handles whether or not they get to join
    public int ClientID; // Maps players to clients (Since a player persists in the server after the client disconnects)

    public int xPos; // x position of player on the board
    public int yPos; // y position of player on the board
    
    protected PlayerHand hand;
    
    public Player()
    {
        this("", -1);
    }

    public Player(String playerName, int clientID)
    {
        PlayerName = playerName;
        ClientID = clientID;
        PlayerActive = true;
        PlayerConnected = true;
        PlayerTurn = false;
        hand = new PlayerHand();
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
    
    
    /**
     * Sets the player's hand to the cards in the passed PlayerHand instance
     * @param hand The hand object with which to replace the player's hand.
     */
    public void setHand( PlayerHand hand )
    {
       this.hand = hand;
    }
    
    
    /**
     * Setter for a single card in the player's hand. Used to add cards to the 
     * player's hand.
     * @param card The card to add to the player's hand.
     */
    public void setCard( Card card )
    {
       this.hand.setCard( card );
    }
    

    /**
     * Getter for the character cards in the player's hand
     * 
     * @return ArrayList of the character cards in the player's hand. Returns
     *         null if the hand contains no character cards.
     * 
     */
    public ArrayList< CharacterCard > getCharacterCards()
    {
       return this.hand.getCharacters();
    }

    /**
     * Getter for the room cards in the player's hand
     * 
     * @return ArrayList of the room cards in the player's hand. Returns
     *         null if the hand contains no room cards.
     * 
     */
    public ArrayList< RoomCard > getRoomCards()
    {
       return this.hand.getRooms();

    }

    /**
     * Getter for the weapon cards in the player's hand
     * 
     * @return ArrayList of the weapon cards in the player's hand. Returns
     *         null if the hand contains no weapon cards.
     * 
     */
    public ArrayList< WeaponCard > getWeaponCards()
    {
       return this.hand.getWeapons();

    }
    
    
    /**
     * Gets every card in the player's hand and returns them in a formatted string
     * @return
     */
    public String getAllCardsString()
    {
       String outString = "";
       
       outString += "Player cards: " + this.hand.toString();
       
       return outString;
    }
    
    

}
