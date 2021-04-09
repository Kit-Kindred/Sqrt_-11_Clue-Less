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
   
   
   // Initalize the arrayLists in the constructor
   public PlayerHand()
   {  
      weapon = new ArrayList<WeaponCard>();
      character = new ArrayList<CharacterCard>();
      room = new ArrayList<RoomCard>();
   }
   
   
   /**
    * Getters for each of the specific player cards
    *
    */
   
   public ArrayList<WeaponCard> getWeapons()
   {
      if ( this.weapon.size() > 0 )
      {
         return this.weapon;
      }
      
      else
      {
         return null;
      }
   }
   
   public ArrayList<CharacterCard> getCharacters()
   {
      if ( this.character.size() > 0 )
      {
         return this.character;
      }
      
      else
      {
         return null;
      }
   }
   
   public ArrayList<RoomCard> getRooms()
   {
      if ( this.room.size() > 0 )
      {
         return this.room;
      }
      
      else
      {
         return null;
      }
   }
   
   
   /**
    * Generic Card setter. Adds the passed card to the correct list in the hand
    * object.
    * @param card The card to be added to the hand.
    */
   public void setCard( Card card )
   {

      if( card instanceof CharacterCard )
      {
         System.out.println("Adding... " + ((CharacterCard) card).getCharacterName() );
         this.setCharacter( (CharacterCard) card );
      }
         
      else if( card instanceof RoomCard )
      {
         System.out.println("Adding... " + ((RoomCard) card).getRoomName());
         this.setRoom( (RoomCard) card );
      }
      
      else if( card instanceof WeaponCard )
      {
         System.out.println("Adding... " + ((WeaponCard) card).getWeaponType() );
         this.setWeapon( (WeaponCard) card );
      }
      
      else
      {
         System.out.println("Could not add the card to the player's hand.");
      }

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
   
   
   /**
    * To string method returning a string of all the cards in the hand instance.
    */
   public String toString()
   {
      String outString = "";
      
      for( CharacterCard c : this.character )
      {
         outString += c.getCharacterName() + ", ";
         
      }

      for( RoomCard r: this.room )
      {
         outString += r.getRoomName() + ", ";
      }

      for( WeaponCard w : this.weapon )
      {
         outString += w.getWeaponType() + ", ";
      }
      
      // Remove trailing comma and space
      outString = outString.substring( 0, outString.length() - 2 );
      
      return outString;
   }
   
}