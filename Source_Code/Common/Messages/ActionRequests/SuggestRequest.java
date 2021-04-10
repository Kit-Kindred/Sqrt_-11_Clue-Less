package Common.Messages.ActionRequests;

import Common.CharacterCard;
import Common.RoomCard;
import Common.WeaponCard;
import Common.PlayerHand;
import Common.SolutionHand;
import Common.Messages.ActionRequests.ActionRequest;

public class SuggestRequest extends ActionRequest
{
    public String PlayerName;
    public SolutionHand Hand;

    public SuggestRequest()
    {
        super();
    }

    public SuggestRequest(String name, SolutionHand h)
    {
        this.PlayerName = name;
        this.Hand = h;
    }

    public String toString()
    {
        String outString = "";
        outString += this.PlayerName;
        outString += "wants to suggest that ";
        outString += this.Hand.getCharacters().get(0).getCharacterName();
        outString += " dit it in the ";
        outString += this.Hand.getRooms().get(0).getRoomName();
        outString += " with the ";
        outString += this.Hand.getWeapons().get(0).getWeaponType();
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
                if (playerCard.getCharacterName() == this.Hand.getCharacters().get(0).getCharacterName())
                {
                    outHand.setCard(playerCard);
                }
        }

        for ( RoomCard playerCard : playerHand.getRooms())
        {
                if (playerCard.getRoomName() == this.Hand.getRooms().get(0).getRoomName())
                {
                    outHand.setCard(playerCard);
                }
        }

        for ( WeaponCard playerCard : playerHand.getWeapons())
        {
                if (playerCard.getWeaponType() == this.Hand.getWeapons().get(0).getWeaponType())
                {
                    outHand.setCard(playerCard);
                }
        }

        return outHand;
    }
}
