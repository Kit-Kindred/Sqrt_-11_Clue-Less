package Common.Messages.StatusUpdates;

import Common.Card;

public class RefuteSuggestion extends StatusUpdate
{
    public String PlayerName;
    public Card Refutation;

    public RefuteSuggestion()
    {
        super();
    }

    public RefuteSuggestion( String name, Card c )
    {
        super();
        this.PlayerName = name;
        this.Refutation = c;
    }
}
