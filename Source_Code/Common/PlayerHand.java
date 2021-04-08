package Common;

import java.util.ArrayList;

/**
 * The Player Hand class maintains the three cards necessary for each player.
 * Each player will have a hand that consists of a Room, Weapon, and Character
 * cards. The cards assigned are random.
 * @author DavidC
 *
 */
public class PlayerHand
{

   
   protected ArrayList<WeaponCard> weapon;
   protected ArrayList<CharacterCard> character;
   protected ArrayList<RoomCard> room;
   
   
   public PlayerHand()
   {  
   }
   
   
   /**
    * Getters for each of the specific player cards
    *
    */
   
   public ArrayList<WeaponCard> getWeapon()
   {
      return this.weapon;
   }
   
   public ArrayList<CharacterCard> getCharacter()
   {
      return this.character;
   }
   
   public ArrayList<RoomCard> getRoom()
   {
      return this.room;
   }
   
   
   
   /**
    * Setters for each of the specific player cards
    * 
    */
   
   public void setWeapon( WeaponCard weapon )
   {
      this.weapon.add( weapon );
   }
   
   public void setCharacter( CharacterCard character )
   {
      this.character.add( character );
   }
   
   public void setRoom( RoomCard room )
   {
      this.room.add( room );
   }
   
   
   // Sets all the specific cards in one shot
   public void setPlayerCards( CharacterCard character, RoomCard room, WeaponCard weapon )
   {
      this.setCharacter( character );
      this.setRoom( room );
      this.setWeapon( weapon );
   }
   
   
   
}
