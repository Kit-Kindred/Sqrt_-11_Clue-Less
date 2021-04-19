package Client.App.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;


public class LobbyInitial extends JFrame
{

   private JPanel contentPane;
   private JTextField playerNameTextField;


   /**
    * Launch the application.
    */
   public static void main( String[] args )
   {
      EventQueue.invokeLater( new Runnable()
      {

         public void run()
         {
            try
            {
               LobbyInitial frame = new LobbyInitial();
//               frame.setVisible( true );
            } catch( Exception e )
            {
               e.printStackTrace();
            }

         }

      } );

   }

   /**
    * Create the frame.
    */
   public LobbyInitial()
   {
      setTitle( "ClueLess Application" );
      initLobbyComponents();
      //initGameComponents();
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
      JPanel mainLobbyJoinFrame = new JPanel(); // Beginning frame that appears first.
      JPanel mainLobbyStatusFrame = new JPanel(); // Frame that appears after joining.
      mainLobbyFrame.add(mainLobbyJoinFrame, "name_805697666643900");
      mainLobbyFrame.add( mainLobbyStatusFrame, "name_secondaryFrame" );
      
      /* This adds a border around the controls. Once we decide to add some sort of backdrop, we should probably remove this.
       * 
       */
      mainLobbyJoinFrame.setBorder( new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null) );

      /*
       * Define a GridBag layout that lays the foundation for the lobby
       * screen. GridBag is better here because we can have differently sized
       * components while maintaining the grid structure.
       */
      GridBagLayout gbl_mainLobbyJoinFrame = new GridBagLayout();
      gbl_mainLobbyJoinFrame.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
      mainLobbyJoinFrame.setLayout( gbl_mainLobbyJoinFrame );
      
            
      // Basic Board that goes around the player picture
      JPanel playerPictureBorderPanel = new JPanel();
      playerPictureBorderPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_playerPictureBorderPanel = new GridBagConstraints();
      gbc_playerPictureBorderPanel.gridheight = 5;
      gbc_playerPictureBorderPanel.ipady = 100;
      gbc_playerPictureBorderPanel.ipadx = 50;
      gbc_playerPictureBorderPanel.insets = new Insets(15, 25, 15, 25);
      gbc_playerPictureBorderPanel.gridx = 0;
      gbc_playerPictureBorderPanel.gridy = 1;
      mainLobbyJoinFrame.add(playerPictureBorderPanel, gbc_playerPictureBorderPanel);
      GridBagLayout gbl_playerPictureBorderPanel = new GridBagLayout();
      playerPictureBorderPanel.setLayout(gbl_playerPictureBorderPanel);
      
      
      // Generic JPanel to house the player picture that will go here
      JPanel playerPicture = new JPanel();
      GridBagConstraints gbc_playerPicture = new GridBagConstraints();
      gbc_playerPicture.gridx = 0;
      gbc_playerPicture.gridy = 1;
      playerPictureBorderPanel.add(playerPicture, gbc_playerPicture);
      
      /* Temporary text that goes inside the player picture field to
       * test sizing...
       */
      JLabel tempPlayerPictureLabel = new JLabel("<html>PLAYER PICTURE</html>");
      playerPicture.add(tempPlayerPictureLabel);
      
            
      // Label for the Player Name text field
      JLabel playerNameLabel = new JLabel("Player Name:");
      GridBagConstraints gbc_playerNameLabel = new GridBagConstraints();
      gbc_playerNameLabel.weighty = 1.0;
      gbc_playerNameLabel.gridheight = 3;
      gbc_playerNameLabel.insets = new Insets(0, 0, 5, 0);
      gbc_playerNameLabel.gridx = 3;
      gbc_playerNameLabel.gridy = 2;
      mainLobbyJoinFrame.add(playerNameLabel, gbc_playerNameLabel);
      
      
      // PlayerName text field
      playerNameTextField = new JTextField();
      playerNameTextField.setToolTipText("Enter Player Name");
      GridBagConstraints gbc_playerNameTextField = new GridBagConstraints();
      gbc_playerNameTextField.gridheight = 2;
      gbc_playerNameTextField.ipady = 15;
      gbc_playerNameTextField.ipadx = 75;
      gbc_playerNameTextField.insets = new Insets(10, 5, 10, 0);
      gbc_playerNameTextField.gridx = 3;
      gbc_playerNameTextField.gridy = 4;
      mainLobbyJoinFrame.add(playerNameTextField, gbc_playerNameTextField);
      playerNameTextField.setColumns(10);
      
      
      // Theme Config button
      JButton themeConfigButton = new JButton("Themes");
      GridBagConstraints gbc_themeConfigButton = new GridBagConstraints();
      gbc_themeConfigButton.gridheight = 2;
      gbc_themeConfigButton.anchor = GridBagConstraints.SOUTH;
      gbc_themeConfigButton.ipady = 10;
      gbc_themeConfigButton.ipadx = 50;
      gbc_themeConfigButton.insets = new Insets(0, 25, 25, 5);
      gbc_themeConfigButton.gridx = 0;
      gbc_themeConfigButton.gridy = 7;
      mainLobbyJoinFrame.add(themeConfigButton, gbc_themeConfigButton);
      
            
      // Lobby Join Button
      JButton            joinGameButton     = new JButton( "Join Game" );
      GridBagConstraints gbc_joinGameButton = new GridBagConstraints();
      gbc_joinGameButton.gridheight = 3;
      gbc_joinGameButton.anchor = GridBagConstraints.SOUTH;
      gbc_joinGameButton.ipady = 50;
      gbc_joinGameButton.ipadx = 75;
      gbc_joinGameButton.gridx = 3;
      gbc_joinGameButton.gridy = 6;
      gbc_joinGameButton.insets = new Insets(25, 50, 15, 15);
      mainLobbyJoinFrame.add( joinGameButton, gbc_joinGameButton );
      
      // Anonymous class implementation of ActionListener to keep things relatively contained.
      joinGameButton.addActionListener( new ActionListener()
         {
            @Override
            public void actionPerformed( ActionEvent e )
            {
               mainLobbyFrame.setVisible( false );
               mainLobbyFrame.transferFocus();
//               mainLobbyFrame.req
               System.out.println("Clicked Join.");
            }
         
         });
      
      
      /* We set ourselves visible as soon as components are created. 
       * When the game starts, we can make lobby components invisible, and then
       * make game components visible, giving the appearance of a transition.
       * 
       * TODO: Come up with a better way to transition between game states.
       */
      this.setVisible( true );
   }
   
   
   // Currently unused.
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
   private void createEvents()
   {

   }

   
   
}
