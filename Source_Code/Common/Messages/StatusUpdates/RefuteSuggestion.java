package Common.Messages.StatusUpdates;

import Common.Card;
import Common.CharacterCard;
import Common.RoomCard;
import Common.WeaponCard;


public class RefuteSuggestion extends StatusUpdate
{

    private static final long serialVersionUID = 1451986645648465346L;
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
        outString += " refuted your suggestion with ";
        if (this.Refutation instanceof CharacterCard)
        {
            outString += ((CharacterCard) this.Refutation).getCharacterName();
        }
        else if (this.Refutation instanceof RoomCard)
        {
            outString += ((RoomCard) this.Refutation).getRoomName();
        }
        else if (this.Refutation instanceof WeaponCard)
        {
            outString += ((WeaponCard) this.Refutation).getWeaponType();
        }
        else
        {
            outString += "?SOMETHING?";
        }
        return outString;
    }
}
