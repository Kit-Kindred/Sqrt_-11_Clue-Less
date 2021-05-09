package Client.App.views;

import Common.*;
import Common.Messages.StatusUpdates.RefuteSuggestion;
import Common.Messages.StatusUpdates.RefuteSuggestionPicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RefuteDialog extends JDialog
{
    HandPanel Cards;
    JFrame parent;

    RefuteDialog(JFrame frame, String title, RefuteSuggestionPicker rs)
    {
        super(frame, title, true);
        parent = frame;
        
        GridBagLayout gbl_this = new GridBagLayout();
        gbl_this.columnWidths = new int[] { 500 };
        gbl_this.rowHeights = new int[] {115};
        setLayout( gbl_this );

        addCardPanel();

        Cards.addPropertyChangeListener("CardClicked", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                cardClicked((Card) evt.getNewValue());
            }
        });

        setBounds( parent.getBounds().x + parent.getWidth()/4, 
           parent.getBounds().y + parent.getHeight()/4, 500, 200);

        for (RoomCard rc : rs.getHand().getRooms())
        {
            Cards.addCard(rc);
        }
        for (CharacterCard cc : rs.getHand().getCharacters())
        {
            Cards.addCard(cc);
        }
        for (WeaponCard wc : rs.getHand().getWeapons())
        {
            Cards.addCard(wc);
        }
        Cards.setCardsSelectable();

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                defaultIfNoSelect();
            }
        });

        setVisible(true);
    }

    public void defaultIfNoSelect()
    {
        if(Cards.SelectedCard == null)
        {
            Cards.defaultSelect();
        }
    }

    public void cardClicked(Card card)
    {
        Cards.SelectedCard = new CardPanel(card);
        setVisible(false);
    }

    public Card getRefutationChoice()
    {
        if(Cards.SelectedCard != null)
        {
            return Cards.SelectedCard.card;
        }
        return null;
    }

    private void addCardPanel()
    {
        Cards = new HandPanel();

        GridBagConstraints gbc_cardPanel = new GridBagConstraints();
        gbc_cardPanel.gridheight = 1;
        gbc_cardPanel.gridwidth = 2;
        gbc_cardPanel.ipady = 0;
        gbc_cardPanel.ipadx = 0;
        gbc_cardPanel.insets = new Insets( 0, 0, 0, 0 );
        gbc_cardPanel.gridx = 0;
        gbc_cardPanel.gridy = 0;
        gbc_cardPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_cardPanel.fill = GridBagConstraints.BOTH;
        this.add( Cards, gbc_cardPanel );
    }
}
