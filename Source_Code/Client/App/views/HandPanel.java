package Client.App.views;

import java.awt.EventQueue;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Common.CharacterCard;


/**
 * This panel is positioned in the main game panel. It holds all the player's cards
 * up to 6 cards (which is the max number of cards a player can hold)
 * @author DavidC
 *
 */
public class HandPanel extends JPanel
{

   CardPanel[] cards;
   
   
   /**
    * Create the panel.
    */
   public HandPanel( CardPanel[] cards )
   {
      setSize( 650, 110 );
      setBorder( new TitledBorder( null, "", TitledBorder.LEADING,
         TitledBorder.TOP, null, null ) );
      
      
      /*
       * Test config to just load a few cards into the player's hand
       */
      CardPanel[] cardsTest = new CardPanel[4];
      cardsTest[0] = new CardPanel( new CharacterCard( CharacterCard.CharacterName.COLONEL_MUSTARD ) );
      cardsTest[1] = new CardPanel( new CharacterCard( CharacterCard.CharacterName.COLONEL_MUSTARD ) );
      cardsTest[2] = new CardPanel( new CharacterCard( CharacterCard.CharacterName.COLONEL_MUSTARD ) );
      cardsTest[3] = new CardPanel( new CharacterCard( CharacterCard.CharacterName.COLONEL_MUSTARD ) );
      
      
      // TODO: replace cardsTest with cards after testing.
      for( CardPanel card: cardsTest )
      {
         add( card );
      }

   }

   
}
