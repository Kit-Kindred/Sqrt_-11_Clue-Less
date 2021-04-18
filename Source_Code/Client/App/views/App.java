package Client.App.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;


public class App extends JFrame
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
               App frame = new App();
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
   public App()
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

      // Default/Root JFrame with a JPanel where the contents are laid out
      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      setBounds( 100, 100, 1000, 725 );
      contentPane = new JPanel();
      contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
      setContentPane( contentPane );
      contentPane.setLayout( null );

      JPanel mainLobbyControlFrame = new JPanel();
      mainLobbyControlFrame.setBounds( (this.getBounds().width - (this.getX()/4) ) /4 ,
         ( this.getBounds().height - this.getY() )/ 4, this.getWidth() / 2,
         this.getHeight() / 2 );
      contentPane.add( mainLobbyControlFrame );
      
      /* This adds a border around the controls. Once we decide to add some sort of backdrop, we should probably remove this.
       * 
       */
      mainLobbyControlFrame.setBorder( new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null) );

      /*
       * Define a GridBag layout that lays the foundation for the lobby
       * screen. GridBag is better here because we can have differently sized
       * components while maintaining the grid structure.
       */
      GridBagLayout gbl_mainLobbyControlFrame = new GridBagLayout();
      gbl_mainLobbyControlFrame.rowHeights = new int[] {0, 0, 0, 0, 0};
      mainLobbyControlFrame.setLayout( gbl_mainLobbyControlFrame );
      
            
      // Basic Board that goes around the player picture
      JPanel playerPictureBorderPanel = new JPanel();
      playerPictureBorderPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_playerPictureBorderPanel = new GridBagConstraints();
      gbc_playerPictureBorderPanel.gridheight = 3;
      gbc_playerPictureBorderPanel.ipady = 100;
      gbc_playerPictureBorderPanel.ipadx = 50;
      gbc_playerPictureBorderPanel.insets = new Insets(15, 25, 15, 25);
      gbc_playerPictureBorderPanel.gridx = 0;
      gbc_playerPictureBorderPanel.gridy = 1;
      mainLobbyControlFrame.add(playerPictureBorderPanel, gbc_playerPictureBorderPanel);
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
      gbc_playerNameLabel.anchor = GridBagConstraints.SOUTH;
      gbc_playerNameLabel.insets = new Insets(0, 0, 5, 0);
      gbc_playerNameLabel.gridx = 3;
      gbc_playerNameLabel.gridy = 2;
      mainLobbyControlFrame.add(playerNameLabel, gbc_playerNameLabel);
      
      
      // PlayerName text field
      playerNameTextField = new JTextField();
      playerNameTextField.setToolTipText("Enter Player Name");
      GridBagConstraints gbc_playerNameTextField = new GridBagConstraints();
      gbc_playerNameTextField.ipady = 15;
      gbc_playerNameTextField.ipadx = 75;
      gbc_playerNameTextField.insets = new Insets(0, 5, 10, 0);
      gbc_playerNameTextField.gridx = 3;
      gbc_playerNameTextField.gridy = 3;
      mainLobbyControlFrame.add(playerNameTextField, gbc_playerNameTextField);
      playerNameTextField.setColumns(10);
      
      
      // Theme Config button
      JButton themeConfigButton = new JButton("Themes");
      GridBagConstraints gbc_themeConfigButton = new GridBagConstraints();
      gbc_themeConfigButton.anchor = GridBagConstraints.SOUTHWEST;
      gbc_themeConfigButton.ipady = 10;
      gbc_themeConfigButton.ipadx = 50;
      gbc_themeConfigButton.insets = new Insets(0, 25, 25, 5);
      gbc_themeConfigButton.gridx = 0;
      gbc_themeConfigButton.gridy = 6;
      mainLobbyControlFrame.add(themeConfigButton, gbc_themeConfigButton);
      
            
      // Lobby Join Button
      JButton            joinGameButton     = new JButton( "Join Game" );
      GridBagConstraints gbc_joinGameButton = new GridBagConstraints();
      gbc_joinGameButton.anchor = GridBagConstraints.SOUTHEAST;
      gbc_joinGameButton.ipady = 50;
      gbc_joinGameButton.ipadx = 75;
      gbc_joinGameButton.gridx = 3;
      gbc_joinGameButton.gridy = 6;
      gbc_joinGameButton.insets = new Insets(25, 50, 15, 15);
      mainLobbyControlFrame.add( joinGameButton, gbc_joinGameButton );
      
      
      /* We set ourselves visible as soon as components are created. 
       * When the game starts, we can make lobby components invisible, and then
       * make game components visible, giving the appearance of a transition.
       * 
       * TODO: Come up with a better way to transition between game states.
       */
      this.setVisible( true );
   }
   
   
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
