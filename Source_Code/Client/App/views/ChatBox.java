package Client.App.views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChatBox extends JPanel
{
    public JTextField sendBox;
    public JComboBox<String> sendTo;

    public ChatBox()
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
        gbl_this.rowHeights = new int[] { 30 };
        gbl_this.columnWidths = new int[] {65, 5, 155};
        this.setLayout( gbl_this );

        sendTo = new JComboBox<String>();

        GridBagConstraints gbc_sendTo = new GridBagConstraints();
        gbc_sendTo.gridheight = 1;
        gbc_sendTo.ipady = 0;
        gbc_sendTo.ipadx = 0;
        gbc_sendTo.insets = new Insets( 0, 0, 0, 0 );
        gbc_sendTo.gridx = 0;
        gbc_sendTo.gridy = 0;
        gbc_sendTo.anchor = GridBagConstraints.NORTHWEST;
        gbc_sendTo.fill = GridBagConstraints.HORIZONTAL;
        this.add( sendTo, gbc_sendTo );

        // PlayerName text field
        sendBox = new JTextField();
        sendBox.setToolTipText( "Type to chat!" );
        GridBagConstraints gbc_sendBox = new GridBagConstraints();
        gbc_sendBox.gridheight = 1;
        gbc_sendBox.ipady = 0;
        gbc_sendBox.ipadx = 0;
        gbc_sendBox.insets = new Insets( 0, 0, 0, 0 );
        gbc_sendBox.gridx = 2;
        gbc_sendBox.gridy = 0;
        gbc_sendBox.anchor = GridBagConstraints.NORTHWEST;
        gbc_sendBox.fill = GridBagConstraints.HORIZONTAL;
        this.add( sendBox, gbc_sendBox );

    }
}
