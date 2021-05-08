package Client.App.views;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Common.*;


/**
 * This panel is positioned in the main game panel. It holds all the player's cards
 * up to 6 cards (which is the max number of cards a player can hold)
 * @author DavidC
 *
 */
public class HandPanel extends JPanel
{

   ArrayList<CardPanel> Cards;
   //SuggestHand Selection;  // Can only ever have one of each selected, so this works well
   PropertyChangeSupport pcs = new PropertyChangeSupport(this);
   CardPanel SelectedCard;

   /**
    * Create the panel.
    */
   public HandPanel()
   {
      setSize( 650, 110 );
      setBorder( new TitledBorder( null, "", TitledBorder.LEADING,
         TitledBorder.TOP, null, null ) );
      
      /*
       * Test config to just load a few cards into the player's hand
       */
      Cards = new ArrayList<CardPanel>();
      SelectedCard = null;
      //Selection = new SuggestHand((CharacterCard) null, (RoomCard) null, (WeaponCard) null);  // Casts to resolve constructor
      //pcs = new PropertyChangeSupport(this);

   }

   public void addCard(Card card)
   {
      CardPanel cp = new CardPanel(card);

      cp.addPropertyChangeListener("Select", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            System.out.println("In handler cc");
            cardClicked((CardPanel)evt.getNewValue());
         }
      });
      System.out.println("Card added");
      Cards.add(cp);
      add(cp);
   }

   public void cardClicked(CardPanel cp)
   {
      System.out.println("Card clicked");
      // Do this one no matter what
      if(cp.selected)
      {
         cp.deselect();
         SelectedCard = null;
      }
      else
      {
         if(cp.selectable)
         {
            cp.select();
            if(SelectedCard != null) {
               SelectedCard.deselect();
            }
            SelectedCard = cp;
         }
      }
      pcs.firePropertyChange("CardClicked", null, cp.card);
   }

   public void setCardsSelectable()
   {
      for (CardPanel cp : Cards)
      {
         cp.selectable = true;
      }
   }

   public void emptySelection()
   {
      SelectedCard = null;
      for (CardPanel cp : Cards)
      {
         cp.selectable = false;
         cp.deselect();
      }
   }

   public void defaultSelect()
   {
      if (SelectedCard == null)
      {
         SelectedCard = Cards.get(0);
      }
   }

   public void removeAllCards()
   {
      Cards.clear();
   }

   public void addPropertyChangeListener(String val, PropertyChangeListener pcl)
   {
      pcs.addPropertyChangeListener(val, pcl);
   }

   /*   This was wrong, but leaving in case I want to reference it for something else
   public boolean canAccuse()
   {
      return SelectedCharacter != null && SelectedWeapon != null && SelectedRoom != null;
   }

   public boolean canSuggest()
   {
      return SelectedCharacter != null && SelectedWeapon != null && SelectedRoom == null;
   }

   public SuggestHand getSuggest(RoomCard currentRoom)
   {
      if (canSuggest())
      {
         return new SuggestHand((CharacterCard) SelectedCharacter.card, currentRoom, (WeaponCard) SelectedWeapon.card);
      }
      else
      {
         return null;
      }
   }

   public SuggestHand getAccuse()
   {
      if(canAccuse())
      {
         return new SuggestHand((CharacterCard) SelectedCharacter.card, (RoomCard) SelectedRoom.card, (WeaponCard) SelectedWeapon.card);
      }
      else
      {
         return null;
      }
   }*/
}
