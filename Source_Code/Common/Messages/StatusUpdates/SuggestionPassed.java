package Common.Messages.StatusUpdates;

/**
 * Sent to all players when a client suggestion is unable to be refuted

 */
public class SuggestionPassed extends StatusUpdate
{
    public String PlayerName;

    public SuggestionPassed(String name)
    {
        this.PlayerName = name;
    }
}
