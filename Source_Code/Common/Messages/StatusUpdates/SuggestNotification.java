package Common.Messages.StatusUpdates;

import Common.SolutionHand;
import Common.Messages.StatusUpdates.StatusUpdate;
import Common.Messages.ActionRequests.SuggestRequest;
/**
 * Sent to all players when a client guesses

 */
public class SuggestNotification extends StatusUpdate
{
    public String PlayerName;
    public SolutionHand Hand;

    public SuggestNotification()
    {
        super();
    }

    public SuggestNotification(SuggestRequest sr)
    {
        this.PlayerName = sr.PlayerName;
        this.Hand = sr.Hand;
    }

    public String toString()
    {
        String outString = "";
        outString += this.PlayerName;
        outString += " suggested that ";
        outString += this.Hand.getCharacters().get(0).getCharacterName();
        outString += " dit it in the ";
        outString += this.Hand.getRooms().get(0).getRoomName();
        outString += " with the ";
        outString += this.Hand.getWeapons().get(0).getWeaponType();
        return outString;
    }
}
