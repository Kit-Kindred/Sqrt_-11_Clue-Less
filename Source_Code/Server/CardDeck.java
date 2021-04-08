package Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Common.Player;
import Common.CharacterCard;
import Common.RoomCard;
import Common.WeaponCard;
import Common.CharacterCard.CharacterName;
import Common.RoomCard.RoomName;
import Common.WeaponCard.WeaponType;

/**
 * Helper class that handles everything related to the deck used to play the
 * game. The primary use will likely be shuffling the available cards, and
 * assigning cards to each player
 * 
 * @author DavidC
 *
 */
public class CardDeck
{

   CardDeck()
   {
   }
   
   
   
   
   
   
   
   
   /**
    * Driver method to shuffle all the play cards, and assign them to the solution hand
    * and each player. A player may have more than one card of each type.
    * 
    * Simply, this method shuffles the ArrayLists and assigns the cards sequentially to each player.
    * This method returns the three solution cards that are not assigned to the players.
    */
   public static SolutionHand shuffleAndAssignCards( ArrayList<Player> players )
   {
      int counter = 1; //Iterator pointer to keep track of the cards
      
      // Grab all the names and types of each card
      List< WeaponType > weapons = Arrays.asList( WeaponCard.WeaponType.values() );
      List< CharacterName > characters = Arrays.asList( CharacterCard.CharacterName.values() );
      List< RoomName > rooms = Arrays.asList( RoomCard.RoomName.values() );
      
      // Shuffle the ArrayLists
      Collections.shuffle( weapons );
      Collections.shuffle( characters );
      Collections.shuffle( rooms );
      
      SolutionHand solutionCards = new SolutionHand( characters.get( 0 ), rooms.get( 0 ), weapons.get( 0 ) );
      
      // TODO: assign player cards
      
      return solutionCards;
      
      
   }
}
