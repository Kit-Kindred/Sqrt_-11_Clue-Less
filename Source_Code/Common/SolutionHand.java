package Common;

import Common.*;
import Common.CharacterCard.CharacterName;
import Common.RoomCard.RoomName;
import Common.WeaponCard.WeaponType;

/**
 *  Solution hand class contains a character, weapon, and room.
 */
public class SolutionHand extends PlayerHand
{

   SolutionHand()
   {
      super();
   }


   // Constructor that takes Card Objects
   SolutionHand( CharacterCard character, RoomCard room, WeaponCard weapon )
   {
      super();
      this.character.add( character );
      this.room.add( room );
      this.weapon.add( weapon );
   }


   // Constructor that takes types and names
   SolutionHand( CharacterName character, RoomName room, WeaponType weapon )
   {
      this( new CharacterCard( character ), new RoomCard( room ), new WeaponCard( weapon ) );
   }



}
