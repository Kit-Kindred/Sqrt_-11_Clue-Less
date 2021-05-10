package Client.App.views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class MainPanel extends JPanel
{
    JPanel boardPictureBorderPanel;
    GameBoardPanel boardPicture;
    JLabel tempBoardPictureLabel;

    HandPanel cardsPictureBorderPanel;
    JPanel cardsPicture;
    JLabel tempCardsPictureLabel;

    JPanel logBorderPanel;
    LogPanel theLog;

    JPanel chatBoxPanel;
    ChatBox chatBox;

    JPanel actionBorderPanel;
    ActionPanel actionPanel;

    public MainPanel()
    {
        /*
         * This adds a border around the controls. Once we decide to add some
         * sort of backdrop, we should probably remove this.
         *
         */
        this.setBorder( new TitledBorder( null, "", TitledBorder.LEADING,
                TitledBorder.TOP, null, null ) );

        /*
         * Define a GridBag layout that lays the foundation for the main
         * screen. GridBag is better here because we can have differently sized
         * components while maintaining the grid structure.
         */
        GridBagLayout gbl_this = new GridBagLayout();
        gbl_this.rowHeights = new int[] { 10, 400, 10, 10, 23, 115, 30};
        gbl_this.columnWidths = new int[] {10, 400, 50, 175, 150, 10, 30 };
        this.setLayout( gbl_this );

        //setBackground(Color.GREEN);

        // Basic Board that goes around the player picture
        boardPictureBorderPanel = new GameBoardPanel();
        boardPictureBorderPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_boardPictureBorderPanel = new GridBagConstraints();
        gbc_boardPictureBorderPanel.gridheight = 3;
        gbc_boardPictureBorderPanel.gridwidth = 2;
        gbc_boardPictureBorderPanel.ipady = 0;
        gbc_boardPictureBorderPanel.ipadx = 0;
        gbc_boardPictureBorderPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_boardPictureBorderPanel.gridx = 1;
        gbc_boardPictureBorderPanel.gridy = 1;
        gbc_boardPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_boardPictureBorderPanel.fill = GridBagConstraints.BOTH;

        boardPicture = new GameBoardPanel();
        this.add( boardPicture, gbc_boardPictureBorderPanel );
//        GridBagLayout gbl_playerPictureBorderPanel = new GridBagLayout();
//        boardPictureBorderPanel.setLayout( gbl_playerPictureBorderPanel );

        // Generic JPanel to house the player picture that will go here
//        boardPicture     = new JPanel();
//        boardPictureBorderPanel.add( boardPicture );

        /*
         * Temporary text that goes inside the player picture field to test
         * sizing...
         */
//        tempBoardPictureLabel = new JLabel(
//                "<html>BOARD PICTURE</html>" );
//        boardPicture.add( tempBoardPictureLabel );


        // Basic Board that goes around the player picture
        cardsPictureBorderPanel = new HandPanel();
//        cardsPictureBorderPanel.setBorder( new TitledBorder( null, "",
//                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_cardsPictureBorderPanel = new GridBagConstraints();
        gbc_cardsPictureBorderPanel.gridheight = 1;
        gbc_cardsPictureBorderPanel.gridwidth = 1;
        gbc_cardsPictureBorderPanel.ipady = 0;
        gbc_cardsPictureBorderPanel.ipadx = 0;
        gbc_cardsPictureBorderPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_cardsPictureBorderPanel.gridx = 1;
        gbc_cardsPictureBorderPanel.gridy = 5;
        gbc_cardsPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_cardsPictureBorderPanel.fill = GridBagConstraints.BOTH;
        this.add( cardsPictureBorderPanel, gbc_cardsPictureBorderPanel );
        GridBagLayout gbl_cardsPictureBorderPanel = new GridBagLayout();
        boardPictureBorderPanel.setLayout( gbl_cardsPictureBorderPanel );

        // Generic JPanel to house the player picture that will go here
//        cardsPicture     = new JPanel();
//        cardsPictureBorderPanel.add( cardsPicture );

        /*
         * Temporary text that goes inside the player picture field to test
         * sizing...
         */
//        tempCardsPictureLabel = new JLabel(
//                "<html>CARDS PICTURE</html>" );
//        cardsPicture.add( tempCardsPictureLabel );

        // JPanel to hold the log
        logBorderPanel = new JPanel();
        logBorderPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_logPictureBorderPanel = new GridBagConstraints();
        gbc_logPictureBorderPanel.gridheight = 1;
        gbc_logPictureBorderPanel.gridwidth = 2;
        gbc_logPictureBorderPanel.ipady = 0;
        gbc_logPictureBorderPanel.ipadx = 0;
        gbc_logPictureBorderPanel.insets = new Insets( 0, 35, 0, 15);
        gbc_logPictureBorderPanel.gridx = 3;
        gbc_logPictureBorderPanel.gridy = 1;
        gbc_logPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_logPictureBorderPanel.fill = GridBagConstraints.BOTH;
        this.add( logBorderPanel, gbc_logPictureBorderPanel );
        logBorderPanel.setLayout( new BorderLayout() );

        // The log panel
        theLog     = new LogPanel();

        logBorderPanel.add( theLog );

        // JPanel to hold the chat box
        chatBoxPanel = new JPanel();
        chatBoxPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_chatBoxPanel = new GridBagConstraints();
        gbc_chatBoxPanel.gridheight = 1;
        gbc_chatBoxPanel.gridwidth = 2;
        gbc_chatBoxPanel.ipady = 0;
        gbc_chatBoxPanel.ipadx = 0;
        gbc_chatBoxPanel.insets = new Insets( 0, 35, 0, 15 );
        gbc_chatBoxPanel.gridx = 3;
        gbc_chatBoxPanel.gridy = 3;
        gbc_chatBoxPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_chatBoxPanel.fill = GridBagConstraints.BOTH;
        this.add( chatBoxPanel, gbc_chatBoxPanel );
        chatBoxPanel.setLayout( new BorderLayout() );

        // The log panel
        chatBox     = new ChatBox();

        chatBoxPanel.add( chatBox );

        // Basic Board that goes around the player picture
        actionBorderPanel = new JPanel();
        actionBorderPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_actionBorderPanel = new GridBagConstraints();
        gbc_actionBorderPanel.gridheight = 1;
        gbc_actionBorderPanel.gridwidth = 3;
        gbc_actionBorderPanel.ipady = 0;
        gbc_actionBorderPanel.ipadx = 0;
        gbc_actionBorderPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_actionBorderPanel.gridx = 2;
        gbc_actionBorderPanel.gridy = 5;
        gbc_actionBorderPanel.anchor = GridBagConstraints.CENTER;
//        gbc_actionBorderPanel.fill = GridBagConstraints.BOTH;
        this.add( actionBorderPanel, gbc_actionBorderPanel );
        actionBorderPanel.setLayout( new BorderLayout() );

        // Generic JPanel to house the player picture that will go here
        actionPanel     = new ActionPanel();
        actionBorderPanel.add( actionPanel );
    }
}
