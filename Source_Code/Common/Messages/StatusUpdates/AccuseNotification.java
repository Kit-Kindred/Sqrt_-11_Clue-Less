package Common.Messages.StatusUpdates;

import Common.SuggestHand;

public class AccuseNotification extends StatusUpdate
{
    public String PlayerName;
    public SuggestHand AccuseHand;
    public boolean Correct;

    public AccuseNotification()
    {
        super();
    }

    public AccuseNotification(String name, SuggestHand hand)
    {
        this.PlayerName = name;
        this.AccuseHand = hand;
    }

    public AccuseNotification(String name, SuggestHand hand, boolean correct)
    {
        this.PlayerName = name;
        this.AccuseHand = hand;
        this.Correct = correct;
    }

    public void setCorrect(boolean c)
    {
        this.Correct = c;
    }

    public String toString()
    {
        String outString = "";
        outString += this.PlayerName;
        outString += " accused ";
        outString += this.AccuseHand.getCharacterName();
        outString += " of committing the murder in the ";
        outString += this.AccuseHand.getRoomName();
        outString += " with the ";
        outString += this.AccuseHand.getWeaponType();
        return outString;
    }
}
