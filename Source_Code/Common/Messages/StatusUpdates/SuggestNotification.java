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
}
