package Client.App.views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ActionPanel extends JPanel
{
    public MoveDPad dPad;

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
        /*gbl_this.columnWidths = new int[] {10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10,
                                           10, 60, 10, 10};*/
        gbl_this.columnWidths = new int[] {10, 60, 10, 60, 10, 60, 10, 60, 10 };
        this.setLayout( gbl_this );

        // Dpad
        dPad     = new MoveDPad();
        GridBagConstraints gbc_dPad = new GridBagConstraints();
        gbc_dPad.gridheight = 1;
        gbc_dPad.gridwidth = 1;
        gbc_dPad.anchor = GridBagConstraints.NORTHWEST;
        gbc_dPad.ipady = 0;
        gbc_dPad.ipadx = 0;
        gbc_dPad.gridx = 7;
        gbc_dPad.gridy = 1;
        gbc_dPad.insets = new Insets( 0, 0, 0, 0 );
        gbc_dPad.fill = GridBagConstraints.BOTH;
        this.add( dPad, gbc_dPad );

        // Accuse Button
        accuseButton     = new JButton( "Accuse" );
        GridBagConstraints gbc_accuseButton = new GridBagConstraints();
        gbc_accuseButton.gridheight = 1;
        gbc_accuseButton.gridwidth = 1;
        gbc_accuseButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_accuseButton.ipady = 0;
        gbc_accuseButton.ipadx = 0;
        gbc_accuseButton.gridx = 1;
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
        gbc_suggestButton.gridx = 3;
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
        gbc_endTurnButton.gridx = 5;
        gbc_endTurnButton.gridy = 1;
        gbc_endTurnButton.insets = new Insets( 0, 0, 0, 0 );
        gbc_endTurnButton.fill = GridBagConstraints.BOTH;
        this.add( endTurnButton, gbc_endTurnButton );
    }
}
