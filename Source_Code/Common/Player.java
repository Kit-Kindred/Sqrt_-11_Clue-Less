package Common;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * INCOMPLETE
 *
 * @author Kit Kindred & Steve Nilla
 *
 */
public class Player implements Serializable
{
    private static final long serialVersionUID = -9155126928314372511L; // make serialized id
    public boolean PlayerActive;  // true if the player is in; false if the player is out
    public boolean PlayerConnected;  // true if the player is connected; false otherwise
    public boolean PlayerTurn; // True is it's the player's turn. This should be set by the server.
    
    //public int PlayerNumber;  // Unique playerID assigned by the server. Could probably allow the user to set this and make it a string
    public String PlayerName;  // A client puts its name in the connection request. Server handles whether or not they get to join
    public int ClientID; // Maps players to clients (Since a player persists in the server after the client disconnects)

    public int xPos; // x position of player on the board
    public int yPos; // y position of player on the board
    
    protected PlayerHand hand;

    public CharacterCard.CharacterName charName;

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

    public Player(String playerName, int clientID, boolean conn, boolean act)
    {
        PlayerName = playerName;
        ClientID = clientID;
        PlayerActive = act;
        PlayerConnected = conn;
        PlayerTurn = false;
        hand = new PlayerHand();
    }

    public Player(Player other)
    {
        PlayerName = other.PlayerName;
        ClientID = other.ClientID;
        PlayerActive = other.PlayerActive;
        PlayerConnected = other.PlayerConnected;
        PlayerTurn = other.PlayerTurn;
        hand = other.getHand();
        xPos = other.xPos;
        yPos = other.yPos;
        charName = other.charName;
    }
    
    public void setPlayerTurn( boolean turn )
    {
       this.PlayerTurn = turn;
    }


    public void assignCharacter(CharacterCard.CharacterName name)
    {
        charName = name;
        switch(name)
        {
            case MR_GREEN ->
                    {
                        xPos = 1;
                        yPos = 4;
                    }
            case MRS_WHITE ->
                    {
                        xPos = 3;
                        yPos = 4;
                    }
            case MRS_PEACOCK ->
                    {
                        xPos = 0;
                        yPos = 3;
                    }
            case MISS_SCARLET ->
                    {
                        xPos = 3;
                        yPos = 0;
                    }
            case PROFESSOR_PLUM ->
                    {
                        xPos = 0;
                        yPos = 1;
                    }
            case COLONEL_MUSTARD ->
                    {
                        xPos = 4;
                        yPos = 1;
                    }
        }
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
     * Getter for the player-specific hand object
     * @return The PlayerHand object associated to the player.
     */
    public PlayerHand getHand()
    {
       return this.hand;
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
    
    public String getCharacterName()
    {
       
       String str = String.valueOf( this.charName );
       str = str.toLowerCase();
       if( str.contains( "_" ) )
       {
          str = str.replace( "_", "\s" );
       }
    
       StringBuilder output = new StringBuilder( str );
       int i = 0;
       do {
          output.replace(i, i + 1, output.substring(i,i + 1).toUpperCase());
          i =  output.indexOf(" ", i) + 1;
       } while (i > 0 && i < output.length());
       
       return output.toString();

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
