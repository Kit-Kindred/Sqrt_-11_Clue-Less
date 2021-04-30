package Client.App.views;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import Common.Card;
import Common.CharacterCard;
import Common.RoomCard;
import Common.WeaponCard;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;


public class CardPanel extends JComponent 
{
   
   Card card;
   String cardName;
   JPanel cardContent;
   BufferedImage picture;
   JLabel cardPicture;
   JLabel label;
   PropertyChangeSupport propertyChange;
   Boolean selectable = false; // Controls whether or not this has a "clickable" border
   Boolean selected = false;
   
   
   public CardPanel( Card card )
   {
      setLayout(new BorderLayout(0, 0));
      setSize( 175, 225 );


      
      
      /*
       * Currently, we use the enum values to map the images. We need to make sure
       * the images for each character, room, weapon all have names that match their respective enums.
       */
      processCard( card );
      File root = null;
      try
      {
         root = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
         picture = ImageIO.read( new File( root, "/../Source_Code/Client/App/views/" + cardName + ".png") );
      } 
      catch( Exception e )
      {
         e.printStackTrace();
         System.out.println(root);
      }
      

      createComponents();
      createEvents();
      

   } // End CardPanel Constructor
   
   
   /**
    * This method handles processing of the card passed to create this cardPanel
    */
   public void processCard( Card card )
   {
      if( card instanceof CharacterCard )
      {
         this.cardName = ((CharacterCard) card).getCharacterName();
//         System.out.println( ((CharacterCard) card).getCharacterName() );
      }
      
      if( card instanceof RoomCard )
      {
         this.cardName = ((RoomCard) card).getRoomName();
      }
      
      if( card instanceof WeaponCard )
      {
         this.cardName = ((WeaponCard) card).getWeaponType();
      }
   }
   
   
   public void deselect()
   {
      selected = false;
   }
   
   
   // Uses PropertyChangeSupport to fire the event
   public void toggleSelectable()
   {
//      Boolean old = selectable;
      propertyChange.firePropertyChange( new PropertyChangeEvent( this, "selectable", this.selectable, !this.selectable ) );
      selectable = !selectable;
   }
   
   
   public void toggleBorder()
   {
      if( selected )
      {
         this.setBorder(UIManager.getBorder("Tree.editorBorder"));
      }
      else
      {
         this.setBorder( null );
      }
   }

   

   

   
   
   public void createComponents()
   {
      
      cardContent = new JPanel();
      cardContent.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
      add(cardContent);
      
      // Start adding our components into the panel
      cardPicture = new JLabel();
      cardPicture.setBorder(UIManager.getBorder("Tree.editorBorder"));
      
      
      /*
       *  TEST
       *  These two buttons are just simulating network events that SHOULD happen in the future.
       *  TODO: remove this testing section once network interface has been integrated.
       */
      
      JButton button = new JButton("selected");
      button.addActionListener( new ActionListener()
         {

            @Override
            public void actionPerformed( ActionEvent e )
            {
               if( selectable )
               {
                  setBorder( new BevelBorder(BevelBorder.RAISED, null, null, null, null) );
                  if( !selected )
                  {
                     selected = true;
                  }
                  else
                  {
                     selected = false;
                  }
               }
               
               else
               {
                  selected = false ;
               }
               
               toggleBorder();
               System.out.println("Selecated changed to " + selected);

            }
            
         }
         );
      
      
      JButton button2 = new JButton("selectable");
      button2.addActionListener( new ActionListener()
         {

            @Override
            public void actionPerformed( ActionEvent e )
            {               
               toggleSelectable();
               System.out.println("Selectable changed to " + selectable);

            }
            
         }
         );
      
      
      /*
       *  TEST END
       */
      
      
      // Get the card name and add/align EVERYTHING
      label = new JLabel( cardName );
      label.setHorizontalAlignment(SwingConstants.CENTER);
      GroupLayout gl_cardContent = new GroupLayout(cardContent);
      gl_cardContent.setHorizontalGroup(
         gl_cardContent.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_cardContent.createSequentialGroup()
               .addContainerGap()
               .addGroup(gl_cardContent.createParallelGroup(Alignment.TRAILING)
                  .addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                  .addComponent(cardPicture, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 125, Short.MAX_VALUE))
               .addComponent( button ).addComponent( button2 ) // TODO: Remove these buttons after testing.
               .addContainerGap())
      );
      gl_cardContent.setVerticalGroup(
         gl_cardContent.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_cardContent.createSequentialGroup()
               .addContainerGap()
               .addComponent(cardPicture, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
               .addComponent(label).addComponent( button ).addComponent( button2 ) // TODO: Remove these buttons after testing.
               .addGap(10))
      );
      
      Image dimg = picture.getScaledInstance( 150, 220,
         Image.SCALE_SMOOTH );
      
      cardPicture.setIcon( new ImageIcon( dimg ) );
      
      cardContent.setLayout(gl_cardContent);
   }


   
   public void createEvents()
   {
      
      // We need to create our own listener for the selectable boolean
      propertyChange = new PropertyChangeSupport(this);
      propertyChange.addPropertyChangeListener( "selectable", new SelectableListener() );
      
      
      
      /* This is how we make this card panel selectable.
       * This doesn't currently do anything, but network stuff for suggestions
       * should go here.
       */
      MouseListener ml = new MouseAdapter()
      {
          @Override
          public void mousePressed(MouseEvent e)
          {
              CardPanel panel = (CardPanel) e.getSource();

              if( panel.selectable )
              {
                 System.out.println("Valid");
              }
             
              else
              {
                 System.out.println("Invalid");
              }
              
          }
      };
      
      this.addMouseListener( ml );
   }
   
   
   
   /**
    * Create the main GUI component and launch the game.
    */
   public static void main( String [] args)
   {
      File root = null;
      try
      {
         root = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
      } catch( URISyntaxException e1 )
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      
      System.out.println(root);
      EventQueue.invokeLater( new Runnable()
      {

         public void run()
         {
            try
            {
               JFrame frame = new JFrame();
               frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
               frame.setBounds( 100, 100, 1000, 725 );

               CardPanel gui = new CardPanel( new CharacterCard( CharacterCard.CharacterName.COLONEL_MUSTARD ) );
               frame.add( gui );
               frame.setVisible( true );
               
            } catch( Exception e )
            {
               e.printStackTrace();
            }

         }

      } );
      
   }
   
   
   
}

class SelectableListener implements PropertyChangeListener
{

   @Override
   public void propertyChange( PropertyChangeEvent evt )
   {
      CardPanel cardPanel = (CardPanel) evt.getSource();
      
      if ( evt.getPropertyName().equals("selectable") ) {
         System.out.println(evt.getNewValue().toString() );
      }
      
      if( (boolean) evt.getNewValue() )
      {
         cardPanel.label.setBorder( new BevelBorder(BevelBorder.RAISED, null, null, null, null) );
      }
      
      else
      {
         cardPanel.label.setBorder( null );
         cardPanel.deselect();
         cardPanel.toggleBorder();
      }


   }
   
}
