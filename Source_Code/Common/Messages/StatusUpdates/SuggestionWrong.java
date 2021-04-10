package Common.Messages.StatusUpdates;

/**
 * Sent to all players when a client guesses wrong

 */
public class SuggestionWrong extends StatusUpdate
{
    public String GuesserName;
    public String RefuterName;

    public SuggestionWrong(String gName, String rName)
    {
        this.GuesserName = gName;
        this.RefuterName = rName;
    }
}
