package Client.App.views;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class LobbyMain extends JFrame
{

   
   private JPanel contentPane;
   private LobbyInitialPanel joinPanel;

   /**
    * Create the frame.
    */
   public LobbyMain()
   {
      setTitle( "ClueLess Application" );
      initLobbyComponents();
//      initGameComponents();
      createEvents();

   }


   /**
    * Creates and initializes the swing components that make up the
    * application.
    */
   public void initLobbyComponents()
   {

      /*
       *  Default/Root JFrame with a JPanel where the contents are laid out.
       *  Defines the main contentPane.
       */
      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      setBounds( 100, 100, 1000, 725 );
      contentPane = new JPanel();
      contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
      setContentPane( contentPane );
      contentPane.setLayout( null );
      
      
      /* Surrounding frame that contains the control frames.
       * This should allow for switching context/views after the player clicks join.
       */
      JPanel mainLobbyFrame = new JPanel();
      mainLobbyFrame.setBounds(243, 156, 500, 362);
      contentPane.add(mainLobbyFrame);
      mainLobbyFrame.setLayout(new CardLayout(0, 0));
      joinPanel = new LobbyInitialPanel(); // Beginning frame that appears first.
      
      LobbyStatusPanel mainLobbyStatusPanel = new LobbyStatusPanel(); // Frame that appears after joining.
      mainLobbyFrame.add( joinPanel, "name_805697666643900" );
      mainLobbyFrame.add( mainLobbyStatusPanel, "name_secondaryPanel" );

      
//      mainLobbyFrame.
      
      

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
      gameControlFrame.setBounds( (this.getBounds().width + this.getX() ) /4 ,
         ( this.getBounds().height - this.getY() )/ 4, this.getWidth() / 2,
         this.getHeight() / 2 );
      contentPane.add( gameControlFrame );
   }


   /**
    * Creates all the event handlers that are needed in the app.
    */
   public void createEvents()
   {
      
      
   }
   

}
