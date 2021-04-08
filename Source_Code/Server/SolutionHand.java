package Server;

import Common.*;
import Common.CharacterCard.CharacterName;
import Common.RoomCard.RoomName;
import Common.WeaponCard.WeaponType;

/**
 * Server-based Solution hand class contains the winning combination of cards.
 * We'll keep this in the server package, since the client doesn't need to 
 * know about this.
 */
public class SolutionHand extends PlayerHand
{

   SolutionHand()
   {
   }
   
   
   // Constructor that takes Card Objects
   SolutionHand( CharacterCard character, RoomCard room, WeaponCard weapon )
   {
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
