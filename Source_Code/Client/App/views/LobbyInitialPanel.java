package Client.App.views;

// import javax.swing.JPanel;
// import java.awt.GridBagLayout;
// import java.awt.GridBagConstraints;
// import java.awt.Insets;
// import javax.swing.JTextField;
// import javax.swing.JButton;
// import javax.swing.JLabel;
// import javax.swing.border.TitledBorder;
// import java.awt.JComboBox;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import Server.ClueLessServer;

/**
 * This is the initial panel that is displayed in the middle of the screen
 * when the client is started. This panel has a themes configuration option, a
 * player name text field and a join button. Clicking the join button should
 * change the panel to display the LobbyStatusPanel instead.
 * 
 * @author DavidC
 *
 */
public class LobbyInitialPanel extends JPanel
{
   
   JPanel playerPictureBorderPanel;
   JPanel playerPicture;
   JLabel tempPlayerPictureLabel;
   JComboBox<String> selectCharacter;
   JLabel playerNameLabel;
   JLabel serverIPLabel;
   JLabel serverPortLabel;
   JTextField playerNameTextField;
   JTextField serverIPTextField;
   JTextField serverPortTextField;
   JButton themeConfigButton;
   JButton joinGameButton;
   

   LobbyInitialPanel()
   {

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

      // Label for the Player Name text field
      playerNameLabel     = new JLabel( "Player Name:" );
      GridBagConstraints gbc_playerNameLabel = new GridBagConstraints();
      gbc_playerNameLabel.weighty = 0.0;
      gbc_playerNameLabel.gridheight = 1;
      gbc_playerNameLabel.insets = new Insets( 0, 0, 0, 0 );
      gbc_playerNameLabel.gridx = 2;
      gbc_playerNameLabel.gridy = 1;
      gbc_playerNameLabel.anchor = GridBagConstraints.NORTHWEST;
      this.add( playerNameLabel, gbc_playerNameLabel );

      // PlayerName text field
      playerNameTextField = new JTextField();
      playerNameTextField.setToolTipText( "Enter Player Name" );
      GridBagConstraints gbc_playerNameTextField = new GridBagConstraints();
      gbc_playerNameTextField.gridheight = 1;
      gbc_playerNameTextField.ipady = 0;
      gbc_playerNameTextField.ipadx = 0;
      gbc_playerNameTextField.insets = new Insets( 0, 0, 0, 0 );
      gbc_playerNameTextField.gridx = 2;
      gbc_playerNameTextField.gridy = 2;
      gbc_playerNameTextField.anchor = GridBagConstraints.NORTHWEST;
      gbc_playerNameTextField.fill = GridBagConstraints.HORIZONTAL;
      this.add( playerNameTextField, gbc_playerNameTextField );
      //playerNameTextField.setColumns( 10 );

      // Label for the Player Name text field
      serverIPLabel     = new JLabel( "Server IP:" );
      GridBagConstraints gbc_serverIPLabel = new GridBagConstraints();
      gbc_serverIPLabel.weighty = 0.0;
      gbc_serverIPLabel.gridheight = 1;
      gbc_serverIPLabel.insets = new Insets( 0, 0, 0, 0 );
      gbc_serverIPLabel.gridx = 2;
      gbc_serverIPLabel.gridy = 3;
      gbc_serverIPLabel.anchor = GridBagConstraints.NORTHWEST;
      this.add( serverIPLabel, gbc_serverIPLabel );

      // PlayerName text field
      serverIPTextField = new JTextField();
      serverIPTextField.setToolTipText( "Enter Server IP" );
      GridBagConstraints gbc_serverIPTextField = new GridBagConstraints();
      gbc_serverIPTextField.gridheight = 1;
      gbc_serverIPTextField.ipady = 0;
      gbc_serverIPTextField.ipadx = 0;
      gbc_serverIPTextField.insets = new Insets( 0, 0, 0, 0 );
      gbc_serverIPTextField.gridx = 2;
      gbc_serverIPTextField.gridy = 4;
      gbc_serverIPTextField.anchor = GridBagConstraints.NORTHWEST;
      gbc_serverIPTextField.fill = GridBagConstraints.HORIZONTAL;
      this.add( serverIPTextField, gbc_serverIPTextField );

      // Label for the Player Name text field
      serverPortLabel     = new JLabel( "Server Port:" );
      GridBagConstraints gbc_serverPortLabel = new GridBagConstraints();
      gbc_serverPortLabel.weighty = 0.0;
      gbc_serverPortLabel.gridheight = 1;
      gbc_serverPortLabel.insets = new Insets( 0, 0, 0, 0 );
      gbc_serverPortLabel.gridx = 2;
      gbc_serverPortLabel.gridy = 5;
      gbc_serverPortLabel.anchor = GridBagConstraints.NORTHWEST;
      this.add( serverPortLabel, gbc_serverPortLabel );

      // PlayerName text field
      serverPortTextField = new JTextField();
      serverPortTextField.setToolTipText( "Enter Server Port" );
      GridBagConstraints gbc_serverPortTextField = new GridBagConstraints();
      gbc_serverPortTextField.gridheight = 1;
      gbc_serverPortTextField.ipady = 0;
      gbc_serverPortTextField.ipadx = 0;
      gbc_serverPortTextField.insets = new Insets( 0, 0, 0, 0 );
      gbc_serverPortTextField.gridx = 2;
      gbc_serverPortTextField.gridy = 6;
      gbc_serverPortTextField.anchor = GridBagConstraints.NORTHWEST;
      gbc_serverPortTextField.fill = GridBagConstraints.HORIZONTAL;
      this.add( serverPortTextField, gbc_serverPortTextField );

      // Theme Config button
      themeConfigButton     = new JButton( "Themes" );
      GridBagConstraints gbc_themeConfigButton = new GridBagConstraints();
      gbc_themeConfigButton.gridheight = 1;
      gbc_themeConfigButton.gridwidth = 1;
      //gbc_themeConfigButton.weightx = 0.1;
      gbc_themeConfigButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_themeConfigButton.ipady = 10;
      gbc_themeConfigButton.ipadx = 50;
      gbc_themeConfigButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_themeConfigButton.gridx = 0;
      gbc_themeConfigButton.gridy = 10;
      gbc_themeConfigButton.fill = GridBagConstraints.BOTH;
      this.add( themeConfigButton, gbc_themeConfigButton );

      // Lobby Join Button
      joinGameButton     = new JButton( "Join Game" );
      GridBagConstraints gbc_joinGameButton = new GridBagConstraints();
      gbc_joinGameButton.gridheight = 3;
      //gbc_joinGameButton.weightx = 0.0;
      gbc_joinGameButton.anchor = GridBagConstraints.NORTHWEST;
      gbc_joinGameButton.ipady = 0;
      gbc_joinGameButton.ipadx = 0;
      gbc_joinGameButton.gridx = 2;
      gbc_joinGameButton.gridy = 9;
      gbc_joinGameButton.insets = new Insets( 0, 0, 0, 0 );
      gbc_joinGameButton.fill = GridBagConstraints.BOTH;
      this.add( joinGameButton, gbc_joinGameButton );


   }

}
