package Client.App.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Common.Player;
import Server.EnvelopeHand;

import javax.swing.JLabel;


public class EndGameDialog extends JDialog
{

   private final JPanel contentPanel = new JPanel();
   

   /**
    * Slightly fancier constructor that takes the player who won, and the
    * winning cards. We use these in the printed end game text.
    * 
    * @param player
    * @param cards
    */
   public EndGameDialog( Player player, EnvelopeHand cards )
   {
      setBounds( 100, 100, 450, 300 );
      getContentPane().setLayout( new BorderLayout() );
      contentPanel.setLayout( new FlowLayout() );
      contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
      getContentPane().add( contentPanel, BorderLayout.CENTER );

      {
         JLabel gameOverText = new JLabel();
         gameOverText.setText( player.PlayerName + " has correctly accused "
            + cards.getCharacterName() + " of committing the murder in the "
            + cards.getRoomName() + " using the " + cards.getWeaponType()
            + "!" +
            "\n\nThe game is now over.");
         ;
         contentPanel.add( gameOverText );
      }

      {
         JPanel buttonPane = new JPanel();
         buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
         getContentPane().add( buttonPane, BorderLayout.SOUTH );

         {
            JButton okButton = new JButton( "OK" );
            buttonPane.add( okButton );
            getRootPane().setDefaultButton( okButton );
         }

      }

   }

}
