package Common.Messages.StatusUpdates;

import Common.SolutionHand;

public class AccuseNotification extends StatusUpdate
{
    public String PlayerName;
    public SolutionHand AccuseHand;
    public boolean Correct;

    public AccuseNotification()
    {
        super();
    }

    public AccuseNotification(String name, SolutionHand hand)
    {
        this.PlayerName = name;
        this.AccuseHand = hand;
    }

    public AccuseNotification(String name, SolutionHand hand, boolean correct)
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
        outString += " suggested that ";
        outString += this.AccuseHand.getCharacterName();
        outString += " dit it in the ";
        outString += this.AccuseHand.getRoomName();
        outString += " with the ";
        outString += this.AccuseHand.getWeaponType();
        return outString;
    }
}
