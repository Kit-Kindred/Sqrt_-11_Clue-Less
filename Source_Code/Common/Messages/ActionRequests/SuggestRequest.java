package Common.Messages.ActionRequests;

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
}
