package Client.App.views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import Server.ClueLessServer;


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
   JRadioButton colMusButton;
   JRadioButton msScarButton;
   JRadioButton profPlumButton;
   JRadioButton mrGreenButton;
   JRadioButton mrsWhiteButton;
   JRadioButton mrsPeaButton;

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

      // /*
      //  * Temporary text that goes inside the player picture field to test
      //  * sizing...
      //  */
      // tempPlayerPictureLabel = new JLabel(
      //         "<html>PLAYER PICTURE</html>" );
      // playerPicture.add( tempPlayerPictureLabel );

      tempPlayerPictureLabel = new JLabel(
         "<html>Select Character</html>" );
      playerPicture.add( tempPlayerPictureLabel );

      // // Select character drop down    
      // selectCharacter = new JComboBox<String>();
      // GridBagConstraints gbc_selectCharacter = new GridBagConstraints();
      // gbc_selectCharacter.gridheight = 1;
      // gbc_selectCharacter.ipady = 0;
      // gbc_selectCharacter.ipadx = 0;
      // gbc_selectCharacter.insets = new Insets( 0, 0, 0, 0 );
      // gbc_selectCharacter.gridx = 0;
      // gbc_selectCharacter.gridy = 6;
      // gbc_selectCharacter.anchor = GridBagConstraints.NORTHWEST;
      // gbc_selectCharacter.fill = GridBagConstraints.HORIZONTAL;
      // this.add( selectCharacter, gbc_selectCharacter );

      // // set default available character values
      // for (String charName : ClueLessServer.AvailableCharacters)
      // {
      //    selectCharacter.addItem(charName);
      // }
      // set up character button names
      String colMusString = "Colonel Mustard";
      String msScarString = "Miss Scarlet";
      String profPlumString = "Professor Plum";
      String mrGreenString = "Mr. Green";
      String mrsWhiteString = "Mrs. White";
      String mrsPeaString = "Mrs. Peacock";

      // create button for Colonel Mustard
      colMusButton = new JRadioButton(colMusString);
      //colMusButton.setEnabled(ClueLessServer.AvailableCharacters[0]);
      colMusButton.setActionCommand(colMusString);
      GridBagConstraints gbc_colMusButton = new GridBagConstraints();
      gbc_colMusButton.gridheight = 1;
      gbc_colMusButton.ipady = 0;
      gbc_colMusButton.ipadx = 0;
      gbc_colMusButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_colMusButton.gridx = 0;
      gbc_colMusButton.gridy = 6;
      gbc_colMusButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_colMusButton.fill = GridBagConstraints.HORIZONTAL;
      this.add( colMusButton, gbc_colMusButton );

      // create button for Miss Scarlet
      msScarButton = new JRadioButton(msScarString);
      //msScarButton.setEnabled(ClueLessServer.AvailableCharacters[1]);
      msScarButton.setActionCommand(msScarString);
      GridBagConstraints gbc_msScarButton = new GridBagConstraints();
      gbc_msScarButton.gridheight = 1;
      gbc_msScarButton.ipady = 0;
      gbc_msScarButton.ipadx = 0;
      gbc_msScarButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_msScarButton.gridx = 0;
      gbc_msScarButton.gridy = 7;
      gbc_msScarButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_msScarButton.fill = GridBagConstraints.HORIZONTAL;
      this.add( msScarButton, gbc_msScarButton );

      // create button for Professor Plum
      profPlumButton = new JRadioButton(profPlumString);
      //profPlumButton.setEnabled(ClueLessServer.AvailableCharacters[2]);
      profPlumButton.setActionCommand(profPlumString);
      GridBagConstraints gbc_profPlumButton = new GridBagConstraints();
      gbc_profPlumButton.gridheight = 1;
      gbc_profPlumButton.ipady = 0;
      gbc_profPlumButton.ipadx = 0;
      gbc_profPlumButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_profPlumButton.gridx = 0;
      gbc_profPlumButton.gridy = 8;
      gbc_profPlumButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_profPlumButton.fill = GridBagConstraints.HORIZONTAL;
      this.add( profPlumButton, gbc_profPlumButton );

      // create button for Mr Green
      mrGreenButton = new JRadioButton(mrGreenString);
      //mrGreenButton.setEnabled(ClueLessServer.AvailableCharacters[3]);
      mrGreenButton.setActionCommand(mrGreenString);
      GridBagConstraints gbc_mrGreenButton = new GridBagConstraints();
      gbc_mrGreenButton.gridheight = 1;
      gbc_mrGreenButton.ipady = 0;
      gbc_mrGreenButton.ipadx = 0;
      gbc_mrGreenButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_mrGreenButton.gridx = 0;
      gbc_mrGreenButton.gridy = 9;
      gbc_mrGreenButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_mrGreenButton.fill = GridBagConstraints.HORIZONTAL;
      this.add( mrGreenButton, gbc_mrGreenButton );

      // create button for Mrs White
      mrsWhiteButton = new JRadioButton(mrsWhiteString);
      //mrsWhiteButton.setEnabled(ClueLessServer.AvailableCharacters[4]);
      mrsWhiteButton.setActionCommand(mrsWhiteString);
      GridBagConstraints gbc_mrsWhiteButton = new GridBagConstraints();
      gbc_mrsWhiteButton.gridheight = 1;
      gbc_mrsWhiteButton.ipady = 0;
      gbc_mrsWhiteButton.ipadx = 0;
      gbc_mrsWhiteButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_mrsWhiteButton.gridx = 0;
      gbc_mrsWhiteButton.gridy = 10;
      gbc_mrsWhiteButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_mrsWhiteButton.fill = GridBagConstraints.HORIZONTAL;
      this.add( mrsWhiteButton, gbc_mrsWhiteButton );

      // create button for Mrs Peacock
      mrsPeaButton = new JRadioButton(mrsPeaString);
      //mrsPeaButton.setEnabled(ClueLessServer.AvailableCharacters[5]);
      mrsPeaButton.setActionCommand(mrsPeaString);
      GridBagConstraints gbc_mrsPeaButton = new GridBagConstraints();
      gbc_mrsPeaButton.gridheight = 1;
      gbc_mrsPeaButton.ipady = 0;
      gbc_mrsPeaButton.ipadx = 0;
      gbc_mrsPeaButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_mrsPeaButton.gridx = 0;
      gbc_mrsPeaButton.gridy = 11;
      gbc_mrsPeaButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_mrsPeaButton.fill = GridBagConstraints.HORIZONTAL;
      this.add( mrsPeaButton, gbc_mrsPeaButton );

      // group the radio buttons so only one is active at a time
      ButtonGroup characterButtons = new ButtonGroup();
      characterButtons.add(colMusButton);
      characterButtons.add(msScarButton);
      characterButtons.add(profPlumButton);
      characterButtons.add(mrGreenButton);
      characterButtons.add(mrsWhiteButton);
      characterButtons.add(mrsPeaButton);
      
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
