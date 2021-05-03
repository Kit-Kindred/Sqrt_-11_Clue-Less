package Common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Player Hand class maintains the three cards necessary for each player.
 * Each player will have a hand that consists of a Room, Weapon, and Character
 * cards. The cards assigned are random.
 * @author DavidC
 *
 */
public class PlayerHand implements Serializable
{


   /**
    * An auto-generated Serializable UID. I believe this just needs to be
    * unique compared to all the Serializable objects in the program.
    */
   private static final long serialVersionUID = -3215083740550944062L;

   protected ArrayList<WeaponCard> weapon;
   protected ArrayList<CharacterCard> character;
   protected ArrayList<RoomCard> room;


   // Initialize the arrayLists in the constructor
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
      return this.weapon;
   }

   public ArrayList<CharacterCard> getCharacters()
   {
      return this.character;
   }

   public ArrayList<RoomCard> getRooms()
   {
      return this.room;
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
         //System.out.println("Adding... " + ((CharacterCard) card).getCharacterName() );
         this.setCharacter( (CharacterCard) card );
      }

      else if( card instanceof RoomCard )
      {
         //System.out.println("Adding... " + ((RoomCard) card).getRoomName());
         this.setRoom( (RoomCard) card );
      }

      else if( card instanceof WeaponCard )
      {
         //System.out.println("Adding... " + ((WeaponCard) card).getWeaponType() );
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

   // returns True if we have no cards
   public boolean isEmpty()
   {
      return (character.isEmpty() && room.isEmpty() && weapon.isEmpty());
   }
   

   /**
    * Method to check the number of cards in the player's hand
    * @return Number of cards in the player's hand
    */
   public int numberOfCards()
   {
      return this.character.size() + this.room.size() + this.weapon.size();
   }
   
   
   /**
    * Empties the player's hand.
    */
   public void clearHand()
   {
      this.character.clear();
      this.room.clear();
      this.weapon.clear();
   }
   
   public ArrayList<Card> getCards()
   {
      ArrayList<Card> fullHand = new ArrayList<Card>();
      fullHand.addAll(character);
      fullHand.addAll(weapon);
      fullHand.addAll(room);
      return fullHand;
   }
   
   /**
    * To string method returning a string of all the cards in the hand instance.
    */
   public String toString()
   {
      String outString = "";

      for( CharacterCard c : this.character )
      {
         if( !this.character.isEmpty() )
         {
            outString += c.getCharacterName() + ", ";
         }
      }

      for( RoomCard r: this.room )
      {
         if( !this.room.isEmpty() )
         {
            outString += r.getRoomName() + ", ";
         }
      }

      for( WeaponCard w : this.weapon )
      {
         if( !this.weapon.isEmpty() )
            {
               outString += w.getWeaponType() + ", ";
            }
      }

      // Remove trailing comma and space
      if( outString.endsWith( ", " ))
      {
         outString = outString.substring( 0, outString.lastIndexOf( ", " ) );
      }


      return outString;
   }

}
