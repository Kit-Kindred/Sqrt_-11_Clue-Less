package Common;

import Common.*;
import Common.CharacterCard.CharacterName;
import Common.RoomCard.RoomName;
import Common.WeaponCard.WeaponType;

import java.io.Serializable;



/**
 *  Solution hand class contains a character, weapon, and room.
 */
public class SolutionHand implements Serializable
{
    public CharacterCard character;
    public RoomCard room;
    public WeaponCard weapon;

   public SolutionHand()
   {
   }


   // Constructor that takes Card Objects
   public SolutionHand( CharacterCard c, RoomCard r, WeaponCard w )
   {
      this.character = c;
      this.room  = r;
      this.weapon = w;
   }


   // Constructor that takes types and names
   public SolutionHand( CharacterName character, RoomName room, WeaponType weapon )
   {
      this( new CharacterCard( character ), new RoomCard( room ), new WeaponCard( weapon ) );
   }

   public CharacterCard getCharacterCard()
   {
       return this.character;
   }

   public RoomCard getRoomCard()
   {
       return this.room;
   }

   public WeaponCard getWeaponCard()
   {
       return this.weapon;
   }

   public String getCharacterName()
   {
       return this.character.getCharacterName();
   }

   public String getRoomName()
   {
       return this.room.getRoomName();
   }

   public String getWeaponType()
   {
       return this.weapon.getWeaponType();
   }

   public CharacterName getCharacterEnum()
   {
       return this.character.getCharacterEnum();
   }

   public RoomName getRoomEnum()
   {
       return this.room.getRoomEnum();
   }

   public WeaponType getWeaponEnum()
   {
       return this.weapon.getWeaponEnum();
   }

}
