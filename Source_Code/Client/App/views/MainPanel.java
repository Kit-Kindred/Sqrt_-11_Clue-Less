package Client.App.views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class MainPanel extends JPanel
{
    JPanel boardPictureBorderPanel;
    JPanel boardPicture;
    JLabel tempBoardPictureLabel;

    JPanel cardsPictureBorderPanel;
    JPanel cardsPicture;
    JLabel tempCardsPictureLabel;

    JPanel logBorderPanel;
    LogPanel theLog;
    //JLabel tempLogPictureLabel;


    JPanel actionPictureBorderPanel;
    JPanel actionPicture;
    JLabel tempActionPictureLabel;

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
        gbl_this.rowHeights = new int[] { 33, 390, 33, 150, 33};
        gbl_this.columnWidths = new int[] {33, 450, 34, 191, 34, 225, 33 };
        this.setLayout( gbl_this );

        //setBackground(Color.GREEN);

        // Basic Board that goes around the player picture
        boardPictureBorderPanel = new JPanel();
        boardPictureBorderPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_playerPictureBorderPanel = new GridBagConstraints();
        gbc_playerPictureBorderPanel.gridheight = 1;
        gbc_playerPictureBorderPanel.gridwidth = 3;
        gbc_playerPictureBorderPanel.ipady = 0;
        gbc_playerPictureBorderPanel.ipadx = 0;
        gbc_playerPictureBorderPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_playerPictureBorderPanel.gridx = 1;
        gbc_playerPictureBorderPanel.gridy = 1;
        gbc_playerPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_playerPictureBorderPanel.fill = GridBagConstraints.BOTH;
        this.add( boardPictureBorderPanel, gbc_playerPictureBorderPanel );
        GridBagLayout gbl_playerPictureBorderPanel = new GridBagLayout();
        boardPictureBorderPanel.setLayout( gbl_playerPictureBorderPanel );

        // Generic JPanel to house the player picture that will go here
        boardPicture     = new JPanel();
        boardPictureBorderPanel.add( boardPicture );

        /*
         * Temporary text that goes inside the player picture field to test
         * sizing...
         */
        tempBoardPictureLabel = new JLabel(
                "<html>BOARD PICTURE</html>" );
        boardPicture.add( tempBoardPictureLabel );


        // Basic Board that goes around the player picture
        cardsPictureBorderPanel = new JPanel();
        cardsPictureBorderPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_cardsPictureBorderPanel = new GridBagConstraints();
        gbc_cardsPictureBorderPanel.gridheight = 1;
        gbc_cardsPictureBorderPanel.gridwidth = 1;
        gbc_cardsPictureBorderPanel.ipady = 0;
        gbc_cardsPictureBorderPanel.ipadx = 0;
        gbc_cardsPictureBorderPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_cardsPictureBorderPanel.gridx = 1;
        gbc_cardsPictureBorderPanel.gridy = 3;
        gbc_cardsPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_cardsPictureBorderPanel.fill = GridBagConstraints.BOTH;
        this.add( cardsPictureBorderPanel, gbc_cardsPictureBorderPanel );
        GridBagLayout gbl_cardsPictureBorderPanel = new GridBagLayout();
        boardPictureBorderPanel.setLayout( gbl_cardsPictureBorderPanel );

        // Generic JPanel to house the player picture that will go here
        cardsPicture     = new JPanel();
        cardsPictureBorderPanel.add( cardsPicture );

        /*
         * Temporary text that goes inside the player picture field to test
         * sizing...
         */
        tempCardsPictureLabel = new JLabel(
                "<html>CARDS PICTURE</html>" );
        cardsPicture.add( tempCardsPictureLabel );

        // JPanel to hold the log
        logBorderPanel = new JPanel();
        logBorderPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_logPictureBorderPanel = new GridBagConstraints();
        gbc_logPictureBorderPanel.gridheight = 1;
        gbc_logPictureBorderPanel.gridwidth = 1;
        gbc_logPictureBorderPanel.ipady = 0;
        gbc_logPictureBorderPanel.ipadx = 0;
        gbc_logPictureBorderPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_logPictureBorderPanel.gridx = 5;
        gbc_logPictureBorderPanel.gridy = 1;
        gbc_logPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_logPictureBorderPanel.fill = GridBagConstraints.BOTH;
        this.add( logBorderPanel, gbc_logPictureBorderPanel );
        logBorderPanel.setLayout( new BorderLayout() );

        // The log panel
        theLog     = new LogPanel();

        logBorderPanel.add( theLog );


        // Basic Board that goes around the player picture
        actionPictureBorderPanel = new JPanel();
        actionPictureBorderPanel.setBorder( new TitledBorder( null, "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagConstraints gbc_actionPictureBorderPanel = new GridBagConstraints();
        gbc_actionPictureBorderPanel.gridheight = 1;
        gbc_actionPictureBorderPanel.gridwidth = 3;
        gbc_actionPictureBorderPanel.ipady = 0;
        gbc_actionPictureBorderPanel.ipadx = 0;
        gbc_actionPictureBorderPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_actionPictureBorderPanel.gridx = 3;
        gbc_actionPictureBorderPanel.gridy = 3;
        gbc_actionPictureBorderPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_actionPictureBorderPanel.fill = GridBagConstraints.BOTH;
        this.add( actionPictureBorderPanel, gbc_actionPictureBorderPanel );
        GridBagLayout gbl_actionPictureBorderPanel = new GridBagLayout();
        boardPictureBorderPanel.setLayout( gbl_actionPictureBorderPanel );

        // Generic JPanel to house the player picture that will go here
        actionPicture     = new JPanel();
        actionPictureBorderPanel.add( actionPicture );

        /*
         * Temporary text that goes inside the player picture field to test
         * sizing...
         */
        tempActionPictureLabel = new JLabel(
                "<html>ACTION PICTURE</html>" );
        actionPicture.add( tempActionPictureLabel );
    }
}
