package Client.App.views;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class LobbyMain extends JFrame
{

   private JPanel            contentPane;
   private JPanel            mainLobbyPanel;
   private LobbyInitialPanel joinPanel;
   private LobbyStatusPanel  statusPanel;
   protected String          playerName;


   /**
    * Create the frame.
    */
   public LobbyMain()
   {
      setTitle( "ClueLess Application" );
      initLobbyComponents();
      // initGameComponents();
      createEvents();

   }


   /**
    * Creates and initializes the swing components that make up the
    * application.
    */
   public void initLobbyComponents()
   {

      /*
       * Default/Root JFrame with a JPanel where the contents are laid out.
       * Defines the main contentPane.
       */
      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      setBounds( 100, 100, 1000, 725 );
      contentPane = new JPanel();
      contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
      setContentPane( contentPane );
      contentPane.setLayout( null );

      /*
       * Surrounding frame that contains the control frames. This should allow
       * for switching context/views after the player clicks join.
       */
      mainLobbyPanel = new JPanel();
      mainLobbyPanel.setBounds( 243, 156, 500, 362 );
      contentPane.add( mainLobbyPanel );

      // Create our two views.
      joinPanel = new LobbyInitialPanel(); // Beginning frame that appears
                                           // first.
      statusPanel = new LobbyStatusPanel(); // Frame that appears after
                                            // joining.

      // Set the card layout and add the views to it
      CardLayout card = new CardLayout( 0, 0 );
      mainLobbyPanel.setLayout( card );
      mainLobbyPanel.add( joinPanel, "name_805697666643900" );
      mainLobbyPanel.add( statusPanel, "name_secondaryPanel" );

   }


   // Currently unused
   // Remove this once I find out how to start the game state.
   public void initGameComponents()
   {
      contentPane = new JPanel();
      contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
      setContentPane( contentPane );
      contentPane.setLayout( null );

      JPanel gameControlFrame = new JPanel();
      gameControlFrame.setBounds(
         ( this.getBounds().width + this.getX() ) / 4,
         ( this.getBounds().height - this.getY() ) / 4, this.getWidth() / 2,
         this.getHeight() / 2 );
      contentPane.add( gameControlFrame );

   }


   /**
    * Creates all the event handlers that are needed in the app. Temporarily
    * using this as an initializer for the ActionListeners as well.
    */
   public void createEvents()
   {

      /*
       * Transitions the pre-joined state to the joined state. Doesn't handle
       * errors just yet - always assumes a successful join.
       */
      joinPanel.joinGameButton.addActionListener( new ActionListener()
      {

         @Override
         public void actionPerformed( ActionEvent e )
         {
            System.out.println( "Clicked Join." );
            // TODO: We need to put the join request to the server here and
            // add error checking just in case.

            playerName = joinPanel.playerNameTextField.getText();
            statusPanel.playerNameLabel.setText( playerName );
            ( (CardLayout) mainLobbyPanel.getLayout() )
               .next( mainLobbyPanel );

         }

      } );

   }

}
