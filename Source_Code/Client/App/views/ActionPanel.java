package Client.App.views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ActionPanel extends JPanel
{
    public JButton moveLeftButton;
    public JButton moveRightButton;
    public JButton moveUpButton;
    public JButton moveDownButton;
    public JButton moveShortcutButton;

    public JButton accuseButton;
    public JButton suggestButton;
    public JButton endTurnButton;

    public ActionPanel()
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
        gbl_this.rowHeights = new int[] { 10, 80, 10};
        gbl_this.columnWidths = new int[] {10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10};
        this.setLayout( gbl_this );

        //Move Buttons
        //Move Left
        moveLeftButton     = new JButton( "Move Left" );
        GridBagConstraints gbc_moveLeftButton = new GridBagConstraints();
        gbc_moveLeftButton.gridheight = 1;
        gbc_moveLeftButton.gridwidth = 1;
        gbc_moveLeftButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveLeftButton.ipady = 0;
        gbc_moveLeftButton.ipadx = 0;
        gbc_moveLeftButton.gridx = 1;
        gbc_moveLeftButton.gridy = 1;
        gbc_moveLeftButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveLeftButton.fill = GridBagConstraints.BOTH;
        this.add( moveLeftButton, gbc_moveLeftButton );

        //Move Right
        moveRightButton     = new JButton( "Move Right" );
        GridBagConstraints gbc_moveRightButton = new GridBagConstraints();
        gbc_moveRightButton.gridheight = 1;
        gbc_moveRightButton.gridwidth = 1;
        gbc_moveRightButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveRightButton.ipady = 0;
        gbc_moveRightButton.ipadx = 0;
        gbc_moveRightButton.gridx = 3;
        gbc_moveRightButton.gridy = 1;
        gbc_moveRightButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveRightButton.fill = GridBagConstraints.BOTH;
        this.add( moveRightButton, gbc_moveRightButton );

        //Move Up
        moveUpButton     = new JButton( "Move Up" );
        GridBagConstraints gbc_moveUpButton = new GridBagConstraints();
        gbc_moveUpButton.gridheight = 1;
        gbc_moveUpButton.gridwidth = 1;
        gbc_moveUpButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveUpButton.ipady = 0;
        gbc_moveUpButton.ipadx = 0;
        gbc_moveUpButton.gridx = 5;
        gbc_moveUpButton.gridy = 1;
        gbc_moveUpButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveUpButton.fill = GridBagConstraints.BOTH;
        this.add( moveUpButton, gbc_moveUpButton );

        //Move Down
        moveDownButton     = new JButton( "Move Down" );
        GridBagConstraints gbc_moveDownButton = new GridBagConstraints();
        gbc_moveDownButton.gridheight = 1;
        gbc_moveDownButton.gridwidth = 1;
        gbc_moveDownButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveDownButton.ipady = 0;
        gbc_moveDownButton.ipadx = 0;
        gbc_moveDownButton.gridx = 7;
        gbc_moveDownButton.gridy = 1;
        gbc_moveDownButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveDownButton.fill = GridBagConstraints.BOTH;
        this.add( moveDownButton , gbc_moveDownButton );

        //Move Shortcut
        moveShortcutButton     = new JButton( "Move Shortcut" );
        GridBagConstraints gbc_moveShortcutButton = new GridBagConstraints();
        gbc_moveShortcutButton.gridheight = 1;
        gbc_moveShortcutButton.gridwidth = 1;
        gbc_moveShortcutButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveShortcutButton.ipady = 0;
        gbc_moveShortcutButton.ipadx = 0;
        gbc_moveShortcutButton.gridx = 9;
        gbc_moveShortcutButton.gridy = 1;
        gbc_moveShortcutButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveShortcutButton.fill = GridBagConstraints.BOTH;
        this.add( moveShortcutButton, gbc_moveShortcutButton );

        // Accuse Button
        accuseButton     = new JButton( "Accuse" );
        GridBagConstraints gbc_accuseButton = new GridBagConstraints();
        gbc_accuseButton.gridheight = 1;
        gbc_accuseButton.gridwidth = 1;
        gbc_accuseButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_accuseButton.ipady = 0;
        gbc_accuseButton.ipadx = 0;
        gbc_accuseButton.gridx = 11;
        gbc_accuseButton.gridy = 1;
        gbc_accuseButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_accuseButton.fill = GridBagConstraints.BOTH;
        this.add( accuseButton, gbc_accuseButton );

        // Suggest Button
        suggestButton     = new JButton( "Suggest" );
        GridBagConstraints gbc_suggestButton = new GridBagConstraints();
        gbc_suggestButton.gridheight = 1;
        gbc_suggestButton.gridwidth = 1;
        gbc_suggestButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_suggestButton.ipady = 0;
        gbc_suggestButton.ipadx = 0;
        gbc_suggestButton.gridx = 13;
        gbc_suggestButton.gridy = 1;
        gbc_suggestButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_suggestButton.fill = GridBagConstraints.BOTH;
        this.add( suggestButton, gbc_suggestButton );

        // End Turn Button
        endTurnButton     = new JButton( "End Turn" );
        GridBagConstraints gbc_endTurnButton = new GridBagConstraints();
        gbc_endTurnButton.gridheight = 1;
        gbc_endTurnButton.gridwidth = 1;
        gbc_endTurnButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_endTurnButton.ipady = 0;
        gbc_endTurnButton.ipadx = 0;
        gbc_endTurnButton.gridx = 15;
        gbc_endTurnButton.gridy = 1;
        gbc_endTurnButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_endTurnButton.fill = GridBagConstraints.BOTH;
        this.add( endTurnButton, gbc_endTurnButton );
    }
}
