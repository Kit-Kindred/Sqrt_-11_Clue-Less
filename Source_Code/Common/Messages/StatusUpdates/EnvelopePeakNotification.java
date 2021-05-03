package Common.Messages.StatusUpdates;

import Common.SuggestHand;

public class EnvelopePeakNotification extends StatusUpdate
{
    public SuggestHand EnvelopeHand;

    public EnvelopePeakNotification()
    {
        super();
    }

    public EnvelopePeakNotification(SuggestHand hand)
    {
        this.EnvelopeHand = hand;
    }

    public String toString()
    {
        String outString = "";
        outString += "The correct answer is that ";
        outString += this.EnvelopeHand.getCharacterName();
        outString += " did it in the ";
        outString += this.EnvelopeHand.getRoomName();
        outString += " with the ";
        outString += this.EnvelopeHand.getWeaponType();
        return outString;
    }
}
