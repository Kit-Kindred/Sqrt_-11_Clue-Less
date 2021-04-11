package Common.Messages.StatusUpdates;

import Common.SolutionHand;

public class EnvelopePeakNotification extends Notification
{
    public SolutionHand EnvelopeHand;

    public EnvelopePeakNotification()
    {
        super();
    }

    public EnvelopePeakNotification(SolutionHand hand)
    {
        this.EnvelopeHand = hand;
    }

    public String toString()
    {
        String outString = "";
        outString += "The correct answer is that ";
        outString += this.EnvelopeHand.getCharacterName();
        outString += " dit it in the ";
        outString += this.EnvelopeHand.getRoomName();
        outString += " with the ";
        outString += this.EnvelopeHand.getWeaponType();
        return outString;
    }
}
