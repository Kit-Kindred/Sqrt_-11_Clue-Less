package Client.App.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import Common.Player;
import Common.RoomCard;
import Common.SuggestHand;
import Server.EnvelopeHand;

import javax.swing.JLabel;


public class EndGameDialog extends JDialog
{

   private final JPanel contentPanel = new JPanel();
   private JFrame parent;
   private String player;
   private SuggestHand hand;
   private JButton okButton;
   private JLabel gameOverText;
   

   /**
    * Slightly fancier constructor that takes the player who won, and the
    * winning cards. We use these in the printed end game text.
    * 
    * @param player
    * @param cards
    */
   public EndGameDialog( JFrame frame, String title )
   {
      super(frame, title, true);
      this.parent = frame;
      gameOverText = new JLabel();
      okButton = new JButton( "OK" );
      getContentPane().setLayout( new BorderLayout() );
   }
   
   
   /**
    * We set these once we figure out who made the correct accusation.
    */
   public void setContent()
   {

      setBounds( parent.getBounds().x + parent.getWidth()/2, 
         parent.getBounds().y + parent.getHeight()/2, 600, 300 );
      contentPanel.setLayout( new FlowLayout() );
      contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
      getContentPane().add( contentPanel, BorderLayout.CENTER );

      {
         gameOverText.setText( "<html>" + player + " has correctly accused "
            + hand.getCharacterName() + " of committing the murder in the "
            + hand.getRoomName() + " using the " + hand.getWeaponType()
            + "!" +
            "\n\nThe game is now over." + "</html>");
         ;
         contentPanel.add( gameOverText );
      }

      {
         JPanel buttonPane = new JPanel();
         buttonPane.setLayout( new FlowLayout( FlowLayout.CENTER ) );
         getContentPane().add( buttonPane, BorderLayout.SOUTH );

         {
            buttonPane.add( okButton );
            getRootPane().setDefaultButton( okButton );
         }
         
         okButton.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e )
            {
               setVisible(false);
            }
            
         });

      }
   }
   
   
   public void addPlayer( String player )
   {
      this.player = player;
   }
   
   
   public void addSolution( SuggestHand hand )
   {
      this.hand = hand;
   }
   
   public void open()
   {
       setVisible(true);
   }
   
   public void addButtonListener( ActionListener actionListener )
   {
      this.okButton.addActionListener( actionListener );
   }
}
