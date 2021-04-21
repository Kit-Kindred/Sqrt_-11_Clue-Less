package Client.App.views;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;


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
      gbl_this.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
      this.setLayout( gbl_this );

      // Basic Board that goes around the player picture
      JPanel playerPictureBorderPanel = new JPanel();
      playerPictureBorderPanel.setBorder( new TitledBorder( null, "",
         TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
      GridBagConstraints gbc_playerPictureBorderPanel = new GridBagConstraints();
      gbc_playerPictureBorderPanel.gridheight = 5;
      gbc_playerPictureBorderPanel.ipady = 100;
      gbc_playerPictureBorderPanel.ipadx = 50;
      gbc_playerPictureBorderPanel.insets = new Insets( 15, 25, 15, 25 );
      gbc_playerPictureBorderPanel.gridx = 0;
      gbc_playerPictureBorderPanel.gridy = 1;
      this.add( playerPictureBorderPanel, gbc_playerPictureBorderPanel );
      GridBagLayout gbl_playerPictureBorderPanel = new GridBagLayout();
      playerPictureBorderPanel.setLayout( gbl_playerPictureBorderPanel );

      // Generic JPanel to house the player picture that will go here
      JPanel             playerPicture     = new JPanel();
      GridBagConstraints gbc_playerPicture = new GridBagConstraints();
      gbc_playerPicture.gridx = 0;
      gbc_playerPicture.gridy = 1;
      playerPictureBorderPanel.add( playerPicture, gbc_playerPicture );

      /*
       * Temporary text that goes inside the player picture field to test
       * sizing...
       */
      JLabel tempPlayerPictureLabel = new JLabel(
         "<html>PLAYER PICTURE</html>" );
      playerPicture.add( tempPlayerPictureLabel );

      // Label for the Player Name text field
      JLabel             playerNameLabel     = new JLabel( "Player Name:" );
      GridBagConstraints gbc_playerNameLabel = new GridBagConstraints();
      gbc_playerNameLabel.weighty = 1.0;
      gbc_playerNameLabel.gridheight = 3;
      gbc_playerNameLabel.insets = new Insets( 0, 0, 5, 0 );
      gbc_playerNameLabel.gridx = 3;
      gbc_playerNameLabel.gridy = 2;
      this.add( playerNameLabel, gbc_playerNameLabel );

      // PlayerName text field
      JTextField playerNameTextField = new JTextField();
      playerNameTextField.setToolTipText( "Enter Player Name" );
      GridBagConstraints gbc_playerNameTextField = new GridBagConstraints();
      gbc_playerNameTextField.gridheight = 2;
      gbc_playerNameTextField.ipady = 15;
      gbc_playerNameTextField.ipadx = 75;
      gbc_playerNameTextField.insets = new Insets( 10, 5, 10, 0 );
      gbc_playerNameTextField.gridx = 3;
      gbc_playerNameTextField.gridy = 4;
      this.add( playerNameTextField, gbc_playerNameTextField );
      playerNameTextField.setColumns( 10 );

      // Theme Config button
      JButton            themeConfigButton     = new JButton( "Themes" );
      GridBagConstraints gbc_themeConfigButton = new GridBagConstraints();
      gbc_themeConfigButton.gridheight = 2;
      gbc_themeConfigButton.anchor = GridBagConstraints.SOUTH;
      gbc_themeConfigButton.ipady = 10;
      gbc_themeConfigButton.ipadx = 50;
      gbc_themeConfigButton.insets = new Insets( 0, 25, 25, 5 );
      gbc_themeConfigButton.gridx = 0;
      gbc_themeConfigButton.gridy = 7;
      this.add( themeConfigButton, gbc_themeConfigButton );

      // Lobby Join Button
      JButton            joinGameButton     = new JButton( "Join Game" );
      GridBagConstraints gbc_joinGameButton = new GridBagConstraints();
      gbc_joinGameButton.gridheight = 3;
      gbc_joinGameButton.anchor = GridBagConstraints.SOUTH;
      gbc_joinGameButton.ipady = 50;
      gbc_joinGameButton.ipadx = 75;
      gbc_joinGameButton.gridx = 3;
      gbc_joinGameButton.gridy = 6;
      gbc_joinGameButton.insets = new Insets( 25, 50, 15, 15 );
      this.add( joinGameButton, gbc_joinGameButton );

      // Anonymous class implementation of ActionListener to keep things
      // relatively contained.
      joinGameButton.addActionListener( new ActionListener()
      {

         @Override
         public void actionPerformed( ActionEvent e )
         {
            // mainLobbyFrame.setVisible( false );
            // mainLobbyFrame.transferFocus();
            // mainLobbyFrame.req
            System.out.println( "Clicked Join." );

            // TODO: We need to use this actionListener to create and send the
            // focus to a LobbyStatusPanel after the player clicks join.
            
            setVisible( false );
            LobbyStatusPanel statusPanel = new LobbyStatusPanel();
            statusPanel.setVisible( true );

         }

      } );

   }

}
