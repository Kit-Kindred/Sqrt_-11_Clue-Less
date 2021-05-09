package Client.App.views;

import Common.CharacterCard;
import Common.RoomCard;
import Common.SuggestHand;
import Common.WeaponCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AccuseDialog extends JDialog
{

    HandPanel RoomCards;
    HandPanel WeaponCards;
    HandPanel CharacterCards;
    private JFrame parent;

    boolean pressedAccuse;

    AccuseDialog(JFrame frame, String title)
    {
        super(frame, title, true);
        parent = frame;

        GridBagLayout gbl_this = new GridBagLayout();
        gbl_this.columnWidths = new int[] { 500, 500 };
        gbl_this.rowHeights = new int[] {115, 10, 115, 10, 115, 30 };
        setLayout( gbl_this );

        addCharacters();
        addRooms();
        addWeapons();

        addButtons();

        //add( new JLabel ("Click button to continue."));
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onClose();
            }
        });

        pressedAccuse = false;

        setSize(1000,600);
    }

    public void open()
    {
        setBounds( parent.getBounds().x + parent.getWidth()/4, 
          parent.getBounds().y + parent.getHeight()/4, 1000, 600);
        setVisible(true);
    }

    public SuggestHand getAccuseHand()
    {
        if(RoomCards.SelectedCard != null && WeaponCards.SelectedCard != null && CharacterCards.SelectedCard != null)
        {
            pressedAccuse = false;
            return new SuggestHand(
                    (CharacterCard)CharacterCards.SelectedCard.card,
                    (RoomCard) RoomCards.SelectedCard.card,
                    (WeaponCard) WeaponCards.SelectedCard.card);
        }
        return null;
    }

    private void addRooms()
    {
        RoomCards = new HandPanel();
        for (RoomCard.RoomName rn : RoomCard.RoomName.values())
        {
            RoomCards.addCard(new RoomCard(rn));
        }
        RoomCards.setCardsSelectable();

        GridBagConstraints gbc_roomPanel = new GridBagConstraints();
        gbc_roomPanel.gridheight = 1;
        gbc_roomPanel.gridwidth = 2;
        gbc_roomPanel.ipady = 0;
        gbc_roomPanel.ipadx = 0;
        gbc_roomPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_roomPanel.gridx = 0;
        gbc_roomPanel.gridy = 2;
        gbc_roomPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_roomPanel.fill = GridBagConstraints.BOTH;
        this.add( RoomCards, gbc_roomPanel );
    }

    private void addCharacters()
    {
        CharacterCards = new HandPanel();
        for (CharacterCard.CharacterName cn : CharacterCard.CharacterName.values())
        {
            CharacterCards.addCard(new CharacterCard(cn));
        }
        CharacterCards.setCardsSelectable();

        GridBagConstraints gbc_characterPanel = new GridBagConstraints();
        gbc_characterPanel.gridheight = 1;
        gbc_characterPanel.gridwidth = 2;
        gbc_characterPanel.ipady = 0;
        gbc_characterPanel.ipadx = 0;
        gbc_characterPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_characterPanel.gridx = 0;
        gbc_characterPanel.gridy = 0;
        gbc_characterPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_characterPanel.fill = GridBagConstraints.BOTH;
        this.add( CharacterCards, gbc_characterPanel );
    }

    private void addWeapons()
    {
        WeaponCards = new HandPanel();
        for (WeaponCard.WeaponType wt : WeaponCard.WeaponType.values())
        {
            WeaponCards.addCard(new WeaponCard(wt));
        }
        WeaponCards.setCardsSelectable();

        GridBagConstraints gbc_weaponPanel = new GridBagConstraints();
        gbc_weaponPanel.gridheight = 1;
        gbc_weaponPanel.gridwidth = 2;
        gbc_weaponPanel.ipady = 0;
        gbc_weaponPanel.ipadx = 0;
        gbc_weaponPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_weaponPanel.gridx = 0;
        gbc_weaponPanel.gridy = 4;
        gbc_weaponPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_weaponPanel.fill = GridBagConstraints.BOTH;
        this.add( WeaponCards, gbc_weaponPanel );
    }

    private void addButtons()
    {
        JButton accept = new JButton ("Accuse");
        accept.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                // Only close this if the accusation is valid
                if(RoomCards.SelectedCard != null && WeaponCards.SelectedCard != null && CharacterCards.SelectedCard != null)
                {
                    pressedAccuse = true;
                    onClose();
                }
            }
        });

        GridBagConstraints gbc_accept = new GridBagConstraints();
        gbc_accept.gridheight = 1;
        gbc_accept.gridwidth = 1;
        gbc_accept.ipady = 0;
        gbc_accept.ipadx = 0;
        gbc_accept.insets = new Insets( 0, 0, 0, 0 );
        gbc_accept.gridx = 0;
        gbc_accept.gridy = 5;
        gbc_accept.anchor = GridBagConstraints.CENTER;
        this.add( accept, gbc_accept );

        JButton cancel = new JButton ("Cancel");
        cancel.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                onClose();
            }
        });

        GridBagConstraints gbc_cancel = new GridBagConstraints();
        gbc_cancel.gridheight = 1;
        gbc_cancel.gridwidth = 1;
        gbc_cancel.ipady = 0;
        gbc_cancel.ipadx = 0;
        gbc_cancel.insets = new Insets( 0, 0, 0, 0 );
        gbc_cancel.gridx = 1;
        gbc_cancel.gridy = 5;
        gbc_cancel.anchor = GridBagConstraints.CENTER;
        this.add( cancel, gbc_cancel );
    }

    public void onClose()
    {
        if (!pressedAccuse)
        {
            //System.out.println("Cancel pressed");
            if(RoomCards.SelectedCard != null)
            {
                RoomCards.SelectedCard.deselect();
                RoomCards.SelectedCard = null;
            }
            if(WeaponCards.SelectedCard != null)
            {
                WeaponCards.SelectedCard.deselect();
                WeaponCards.SelectedCard = null;
            }
            if(CharacterCards.SelectedCard != null)
            {
                CharacterCards.SelectedCard.deselect();
                CharacterCards.SelectedCard = null;
            }
        }
        pressedAccuse = false;
        setVisible(false);
    }

}
