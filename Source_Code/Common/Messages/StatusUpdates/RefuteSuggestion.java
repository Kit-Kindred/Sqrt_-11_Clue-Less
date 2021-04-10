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

    public String toString()
    {
        String outString = "";
        outString += this.PlayerName;
        outString += " refuted your suggestion with "
        if (this.Refutation instanceof CharacterCard)
        {
            outString += this.Refutation.getCharacterName();
        }
        else if (this.refutation instanceof RoomCard)
        {
            outString += this.Refutation.getRoomName();
        }
        else if (this.refutation instanceof WeaponCard)
        {
            outString += this.Refutation.getWeaponType();
        }
        else
        {
            outString += "?SOMETHING?"
        }
        return outString
    }
}
