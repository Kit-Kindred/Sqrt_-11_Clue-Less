package Common.Messages.ActionRequests;

import Common.CharacterCard;
import Common.RoomCard;
import Common.WeaponCard;
import Common.PlayerHand;
import Common.SuggestHand;
import Common.Messages.ActionRequests.ActionRequest;

public class SuggestRequest extends ActionRequest
{
    public String PlayerName;
    public SuggestHand Hand;
    public int xPos;
    public int yPos;

    public SuggestRequest()
    {
        super();
    }

    public SuggestRequest(String name, SuggestHand h, int x, int y)
    {
        this.PlayerName = name;
        this.Hand = h;
        xPos = x;
        yPos = y;
    }

    public String toString()
    {
        String outString = "";
        outString += this.PlayerName;
        outString += "wants to suggest that ";
        outString += this.Hand.getCharacterName();
        outString += " did it in the ";
        outString += this.Hand.getRoomName();
        outString += " with the ";
        outString += this.Hand.getWeaponType();
        return outString;
    }

    // operation to check if a given player hand has any cards in it that will
    // refute this suggestion request. If it does, it outputs those cards (as a
    // new hand.) If not, it returns an empty hand.
    public PlayerHand checkRefutations( PlayerHand playerHand )
    {
        PlayerHand outHand = new PlayerHand();

        //probably a better way to do this than to loop through
        for ( CharacterCard playerCard : playerHand.getCharacters())
        {
                if (playerCard.getCharacterEnum() == this.Hand.getCharacterEnum())
                {
                    outHand.setCard(playerCard);
                }
        }

        for ( RoomCard playerCard : playerHand.getRooms())
        {
                if (playerCard.getRoomEnum() == this.Hand.getRoomEnum())
                {
                    outHand.setCard(playerCard);
                }
        }

        for ( WeaponCard playerCard : playerHand.getWeapons())
        {
                if (playerCard.getWeaponEnum() == this.Hand.getWeaponEnum())
                {
                    outHand.setCard(playerCard);
                }
        }

        return outHand;
    }
    
}
