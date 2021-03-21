package Common.Messages.ActionRequests;

/*
* Might be a better way to do this, but for now using the Card class' enums
* rather than doing our own here.
*/
import CharacterCard;
import CharacterCard.CharacterName;
import RoomCard;
import RoomCard.RoomName;
import WeaponCard;
import WeaponCard.WeaponType;

/*
* class to send a suggest request
*/
public class SuggestRequest extends ActionRequest
{
    public CharacterCard.CharacterName character;
    public RoomCard.RoomName room;
    public WeaponCard.WeaponType weapon;

    public SuggestRequest( int PlayerID, CharacterCard.CharacterName character,
                            RoomCard.RoomName room, WeaponCard.WeaponType weapon )
    {
        super(PlayerID)
        this.character = character;
        this.room = room;
        this.weapon = weapon;
    }
}
