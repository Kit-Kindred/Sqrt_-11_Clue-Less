package Client.App.views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class MoveDPad extends JPanel
{
    public BasicArrowButton moveLeftButton;
    public BasicArrowButton moveRightButton;
    public BasicArrowButton moveUpButton;
    public BasicArrowButton moveDownButton;
    public JButton moveShortcutButton;

    public MoveDPad()
    {
        this.setBorder( new TitledBorder( null, "", TitledBorder.LEADING,
                TitledBorder.TOP, null, null ) );

        /*
         * Define a GridBag layout that lays the foundation for the main
         * screen. GridBag is better here because we can have differently sized
         * components while maintaining the grid structure.
         */
        GridBagLayout gbl_this = new GridBagLayout();
        gbl_this.rowHeights = new int[] { 20, 20, 20};
        gbl_this.columnWidths = new int[] { 20, 20, 20 };
        this.setLayout( gbl_this );

        //Move Buttons
        //Move Left
        moveLeftButton     = new BasicArrowButton( SwingConstants.WEST );
        GridBagConstraints gbc_moveLeftButton = new GridBagConstraints();
        gbc_moveLeftButton.gridheight = 1;
        gbc_moveLeftButton.gridwidth = 1;
        gbc_moveLeftButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveLeftButton.ipady = 0;
        gbc_moveLeftButton.ipadx = 0;
        gbc_moveLeftButton.gridx = 0;
        gbc_moveLeftButton.gridy = 1;
        gbc_moveLeftButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveLeftButton.fill = GridBagConstraints.BOTH;
        this.add( moveLeftButton, gbc_moveLeftButton );

        //Move Right
        moveRightButton     = new BasicArrowButton( SwingConstants.EAST );
        GridBagConstraints gbc_moveRightButton = new GridBagConstraints();
        gbc_moveRightButton.gridheight = 1;
        gbc_moveRightButton.gridwidth = 1;
        gbc_moveRightButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveRightButton.ipady = 0;
        gbc_moveRightButton.ipadx = 0;
        gbc_moveRightButton.gridx = 2;
        gbc_moveRightButton.gridy = 1;
        gbc_moveRightButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveRightButton.fill = GridBagConstraints.BOTH;
        this.add( moveRightButton, gbc_moveRightButton );

        //Move Up
        moveUpButton     = new BasicArrowButton( SwingConstants.NORTH );
        GridBagConstraints gbc_moveUpButton = new GridBagConstraints();
        gbc_moveUpButton.gridheight = 1;
        gbc_moveUpButton.gridwidth = 1;
        gbc_moveUpButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveUpButton.ipady = 0;
        gbc_moveUpButton.ipadx = 0;
        gbc_moveUpButton.gridx = 1;
        gbc_moveUpButton.gridy = 0;
        gbc_moveUpButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveUpButton.fill = GridBagConstraints.BOTH;
        this.add( moveUpButton, gbc_moveUpButton );

        //Move Down
        moveDownButton     = new BasicArrowButton( SwingConstants.SOUTH );
        GridBagConstraints gbc_moveDownButton = new GridBagConstraints();
        gbc_moveDownButton.gridheight = 1;
        gbc_moveDownButton.gridwidth = 1;
        gbc_moveDownButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveDownButton.ipady = 0;
        gbc_moveDownButton.ipadx = 0;
        gbc_moveDownButton.gridx = 1;
        gbc_moveDownButton.gridy = 2;
        gbc_moveDownButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveDownButton.fill = GridBagConstraints.BOTH;
        this.add( moveDownButton , gbc_moveDownButton );

        //Move Shortcut
        moveShortcutButton     = new JButton( "SC" );
        moveShortcutButton.setToolTipText("Take a shortcut!");
        GridBagConstraints gbc_moveShortcutButton = new GridBagConstraints();
        gbc_moveShortcutButton.gridheight = 1;
        gbc_moveShortcutButton.gridwidth = 1;
        gbc_moveShortcutButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_moveShortcutButton.ipady = 0;
        gbc_moveShortcutButton.ipadx = 0;
        gbc_moveShortcutButton.gridx = 1;
        gbc_moveShortcutButton.gridy = 1;
        gbc_moveShortcutButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_moveShortcutButton.fill = GridBagConstraints.BOTH;
        this.add( moveShortcutButton, gbc_moveShortcutButton );

    }

    public void enableDpad(boolean enable)
    {
        moveLeftButton.setEnabled(enable);
        moveRightButton.setEnabled(enable);
        moveUpButton.setEnabled(enable);
        moveDownButton.setEnabled(enable);
        moveShortcutButton.setEnabled(enable);
    }
}
