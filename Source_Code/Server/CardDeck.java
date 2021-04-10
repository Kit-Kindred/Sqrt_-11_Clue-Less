
package Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Common.Player;
import Common.Card;
import Common.CharacterCard;
import Common.RoomCard;
import Common.WeaponCard;
import Common.CharacterCard.CharacterName;
import Common.Messages.StatusUpdates.PlayerHandUpdate;
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

   
   /**
    * Driver method to shuffle all the play cards, and assign them to the
    * solution hand and each player. A player may have more than one card of
    * each type.
    * 
    * Simply, this method shuffles the ArrayLists and assigns the cards
    * sequentially to each player. This method returns the three solution
    * cards that are not assigned to the players.
    */
   public static SolutionHand shuffleAndAssignCards(
      ArrayList< Player > players )
   {
      int          counter       = 0; // Iterator pointer to keep track of the
                                      // cards
      SolutionHand solutionCards = null;

      /*
       * Grab all the names and types of each card Had to do some list
       * manipulation to get the enums into a modifiable list.
       */
      List< WeaponType >    weaponEnums    = new ArrayList< WeaponType >(
         Arrays.asList( WeaponCard.WeaponType.values() ) );
      List< CharacterName > characterEnums = new ArrayList< CharacterName >(
         Arrays.asList( CharacterCard.CharacterName.values() ) );
      List< RoomName >      roomEnums      = new ArrayList< RoomName >(
         Arrays.asList( RoomCard.RoomName.values() ) );

      System.out.println( characterEnums.size() );

      // Card equivalents of the enums
      ArrayList< CharacterCard > characters = new ArrayList< CharacterCard >();
      ArrayList< RoomCard > rooms = new ArrayList< RoomCard >() ;
      ArrayList< WeaponCard > weapons = new ArrayList< WeaponCard >();

      for( CharacterName c : characterEnums )
      {
         characters.add( new CharacterCard( c ) );
      }

      for( RoomName r : roomEnums )
      {
         rooms.add( new RoomCard( r ) );
      }

      for( WeaponType w : weaponEnums )
      {
         weapons.add( new WeaponCard( w ) );
      }

      // Shuffle the ArrayLists
      Collections.shuffle( weapons );
      Collections.shuffle( characters );
      Collections.shuffle( rooms );

      System.out.println( characters.get( 0 ).getCharacterName() );

      // Remove the top card from each card type and store as solution cards
      try
      {
         solutionCards = new SolutionHand( characters.remove( 0 ),
            rooms.remove( 0 ), weapons.remove( 0 ) );
         
      } catch( Exception e )
      {
         System.out.println( "Error generating solution cards." );
         e.printStackTrace();
         System.exit( 0 );
      }

      // Combine all cards into one larger deck
      List< Card > deck = new ArrayList< Card >();
      deck.addAll( characters );

      deck.addAll( rooms );

      deck.addAll( weapons );
      

      // Shuffle the deck and assign to each player sequentially
      Collections.shuffle( deck );


      /*
       * Loops through the cards and players, assigning the cards to players
       * one by one.
       */
      for( Card card : deck )
      {
         players.get( counter % players.size() ).setCard( card );
         counter++;
      }

      // DEBUG Print statements to show the cards for each player
//      for( Player p : players )
//      {
//         System.out.println( p.PlayerNumber+ " " + p.getHand().toString() );
//      }

      // Return the three cards to be used as the solution cards.
      return solutionCards;

   }

}
