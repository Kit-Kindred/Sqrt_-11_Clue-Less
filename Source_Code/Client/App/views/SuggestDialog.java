package Client.App.views;

import Common.CharacterCard;
import Common.RoomCard;
import Common.SuggestHand;
import Common.WeaponCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestDialog extends JDialog
{
    HandPanel WeaponCards;
    HandPanel CharacterCards;
    RoomCard CurrentRoom;
    private JFrame parent;

    SuggestDialog(JFrame frame, String title)
    {
        super(frame, title, true);
        this.parent = frame;

        GridBagLayout gbl_this = new GridBagLayout();
        gbl_this.columnWidths = new int[] { 500, 500 };
        gbl_this.rowHeights = new int[] {115, 10, 115, 30 };
        setLayout( gbl_this );

        CurrentRoom = null;

        addCharacters();
        addWeapons();
        addButtons();

        //add( new JLabel ("Click button to continue."));

        setSize(1000,400);
    }

    public void open(RoomCard currentRoom)
    {
        CurrentRoom = currentRoom;
        setBounds( parent.getBounds().x + parent.getWidth()/4, 
           parent.getBounds().y + parent.getHeight()/4, 1000, 400);
        setVisible(true);
    }

    public SuggestHand getSuggestHand()
    {
        if(WeaponCards.SelectedCard != null && CharacterCards.SelectedCard != null)
        {
            return new SuggestHand(
                    (CharacterCard)CharacterCards.SelectedCard.card,
                    CurrentRoom,
                    (WeaponCard) WeaponCards.SelectedCard.card);
        }
        return null;
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
        gbc_weaponPanel.gridy = 2;
        gbc_weaponPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_weaponPanel.fill = GridBagConstraints.BOTH;
        this.add( WeaponCards, gbc_weaponPanel );
    }

    private void addButtons()
    {
        JButton accept = new JButton ("Suggest");
        accept.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                // Only close this if the suggestion is valid
                if(WeaponCards.SelectedCard != null && CharacterCards.SelectedCard != null)
                {
                    setVisible(false);
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
        gbc_accept.gridy = 3;
        gbc_accept.anchor = GridBagConstraints.CENTER;
        this.add( accept, gbc_accept );

        JButton cancel = new JButton ("Cancel");
        cancel.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                if(WeaponCards.SelectedCard != null)
                {
                    WeaponCards.SelectedCard.deselect();
                }
                if(CharacterCards.SelectedCard != null)
                {
                    CharacterCards.SelectedCard.deselect();
                }
                setVisible(false);
            }
        });

        GridBagConstraints gbc_cancel = new GridBagConstraints();
        gbc_cancel.gridheight = 1;
        gbc_cancel.gridwidth = 1;
        gbc_cancel.ipady = 0;
        gbc_cancel.ipadx = 0;
        gbc_cancel.insets = new Insets( 0, 0, 0, 0 );
        gbc_cancel.gridx = 1;
        gbc_cancel.gridy = 3;
        gbc_cancel.anchor = GridBagConstraints.CENTER;
        this.add( cancel, gbc_cancel );
    }
}
