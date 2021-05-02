package Client.App.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


/**
 * This panel will contain useful lobby information such as the player's
 * character, other players who have joined the game, and this panel will
 * allow users to attempt to start the game.
 * 
 * @author DavidC
 *
 */
public class LobbyStatusPanel extends JPanel
{
   
   JPanel playerPictureBorderPanel;
   JPanel playerPicture;
   JLabel playerNameDescription;
   JLabel tempPlayerPictureLabel;
   JLabel playerNameLabel;
   JButton startGameButton;
   
   
   public LobbyStatusPanel()
   {
      this.setFocusable( true );
      
      /*
       * This adds a border around the controls. Once we decide to add some
       * sort of backdrop, we should probably remove this.
       * 
       */
      this.setBorder( new TitledBorder( null, "", TitledBorder.LEADING,
         TitledBorder.TOP, null, null ) );
      
      
      /*
       * Define a GridBag layout that lays the foundation for the lobby
       * screen. GridBag is better here because we can have differently sized
       * components while maintaining the grid structure.
       */
      GridBagLayout gbl_this = new GridBagLayout();
      gbl_this.rowHeights = new int[] { 30, 10, 30, 10, 30, 10, 30, 30, 0, 30, 30 };  // 8 for expansion
      gbl_this.columnWidths = new int[] {120, 70, 120 };
      this.setLayout( gbl_this );

      // Basic Board that goes around the player picture
      playerPictureBorderPanel = new JPanel();
      playerPictureBorderPanel.setBorder( new TitledBorder( null, "",
              TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
      GridBagConstraints gbc_playerPictureBorderPanel = new GridBagConstraints();
      gbc_playerPictureBorderPanel.gridheight = 5;
      gbc_playerPictureBorderPanel.gridwidth = 1;
      gbc_playerPictureBorderPanel.ipady = 0;
      gbc_playerPictureBorderPanel.ipadx = 0;
      gbc_playerPictureBorderPanel.insets = new Insets( 0, 0, 0, 0 );
      gbc_playerPictureBorderPanel.gridx = 0;
      gbc_playerPictureBorderPanel.gridy = 1;
      gbc_playerPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
      gbc_playerPictureBorderPanel.fill = GridBagConstraints.BOTH;
      this.add( playerPictureBorderPanel, gbc_playerPictureBorderPanel );
      GridBagLayout gbl_playerPictureBorderPanel = new GridBagLayout();
      playerPictureBorderPanel.setLayout( gbl_playerPictureBorderPanel );

      // Generic JPanel to house the player picture that will go here
      playerPicture     = new JPanel();
      playerPictureBorderPanel.add( playerPicture );

      /*
       * Temporary text that goes inside the player picture field to test
       * sizing...
       */
      tempPlayerPictureLabel = new JLabel(
              "<html>PLAYER PICTURE</html>" );
      playerPicture.add( tempPlayerPictureLabel );

      // Label for the Player Name text field
      playerNameDescription     = new JLabel( "Player Name:" );
      GridBagConstraints gbc_playerNameDescription = new GridBagConstraints();
      gbc_playerNameDescription.weighty = 0.0;
      gbc_playerNameDescription.gridheight = 1;
      gbc_playerNameDescription.insets = new Insets( 0, 0, 0, 0 );
      gbc_playerNameDescription.gridx = 2;
      gbc_playerNameDescription.gridy = 1;
      gbc_playerNameDescription.anchor = GridBagConstraints.NORTHWEST;
      this.add( playerNameDescription, gbc_playerNameDescription );
      
      
      // PlayerName text field
      playerNameLabel = new JLabel();
      GridBagConstraints gbc_playerNameLabel = new GridBagConstraints();
      gbc_playerNameLabel.gridheight = 1;
      gbc_playerNameLabel.ipady = 0;
      gbc_playerNameLabel.ipadx = 0;
      gbc_playerNameLabel.insets = new Insets( 0, 0, 0, 0 );
      gbc_playerNameLabel.gridx = 2;
      gbc_playerNameLabel.gridy = 2;
      this.add( playerNameLabel, gbc_playerNameLabel );
      
      
      /// Lobby Join Button
      startGameButton     = new JButton( "Start Game" );
      GridBagConstraints gbc_startGameButton = new GridBagConstraints();
      gbc_startGameButton.gridheight = 3;
      //gbc_startGameButton.weightx = 0.0;
      gbc_startGameButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_startGameButton.ipady = 0;
      gbc_startGameButton.ipadx = 0;
      gbc_startGameButton.gridx = 2;
      gbc_startGameButton.gridy = 9;
      gbc_startGameButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_startGameButton.fill = GridBagConstraints.BOTH;
      this.add( startGameButton, gbc_startGameButton );
      
      
   }
   
   
}
