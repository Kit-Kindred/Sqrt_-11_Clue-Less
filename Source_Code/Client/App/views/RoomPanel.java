package Client.App.views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;

import Common.BoardRoom;
import Common.Player;

public class RoomPanel extends JComponent implements SelectablePanel
{

   BoardRoom room;
   String roomName;
   JPanel content;
   BufferedImage picture;
   JLabel roomPicture;
   JLabel label;
   private final PropertyChangeSupport pcs;
   Boolean selectable = false; // Controls whether or not this has a "clickable" border
   Boolean selected = false;

   RoomPanel( String name )
   {

      this.room = new BoardRoom( name );
      this.roomName = name;
      setLayout( new BorderLayout(0, 0) );
      setSize( 75, 90 );
      setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0) );

      pcs = new PropertyChangeSupport(this);


      /* There are occasional "blanks" on the board that don't have a room or hallway.
       * For these, we just want an empy panel
       */
      if( !name.equals( "" ) )
      {
         File root = null;
         try
         {
            root = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());

            //picture = ImageIO.read( new File( root, "../../../../Sqrt_-11_Clue-Less/Source_Code/Client/App/Resources/Cards/" + cardName + ".png") );
            picture = ImageIO.read( new File( root, "/../Source_Code/Client/App/Resources/Rooms/" + roomName + ".png") );

         }
         catch( Exception e )
         {
            e.printStackTrace();
            System.out.println(root + "\n" + roomName);
         }

         createComponents();
         createEvents();
      }

   }

   private void createComponents()
   {
      content = new JPanel();
      content.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
      add(content);

      // Start adding our components into the panel
      roomPicture = new JLabel();
      roomPicture.setBorder(UIManager.getBorder("Tree.editorBorder"));

      if( roomName.contains( "Hallway" ) )
      {
         roomName = "Hallway";
      }

      // Get the card name and add/align EVERYTHING
      label = new JLabel( "<html>" + roomName + "</html>" );
      label.setHorizontalAlignment(SwingConstants.CENTER);
      GroupLayout gl_cardContent = new GroupLayout(content);
      gl_cardContent.setHorizontalGroup(
         gl_cardContent.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_cardContent.createSequentialGroup()
               .addGap(1)
               .addGroup(gl_cardContent.createParallelGroup(Alignment.TRAILING)
                  .addComponent(label, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 73, Short.MAX_VALUE)
                  .addComponent(roomPicture, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
               .addGap(1))
      );
      gl_cardContent.setVerticalGroup(
         gl_cardContent.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_cardContent.createSequentialGroup()
               .addGap(1)
               .addComponent(roomPicture, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
               .addComponent(label)
               .addGap(1))
      );

      Image dimg = picture.getScaledInstance( 75, 90,
         Image.SCALE_SMOOTH );

      roomPicture.setIcon( new ImageIcon( dimg ) );

      content.setLayout(gl_cardContent);
   }

   private void createEvents()
   {

      // We need to create our own listener for the selectable boolean
      addPropertyChangeListener( "selectable", new SelectableListener() );

      /*
       * Listen for the user clicks. We'll use this to help with movement
       */
      MouseListener ml = new MouseAdapter()
      {
          @Override
          public void mousePressed(MouseEvent e)
          {
              RoomPanel panel = (RoomPanel) e.getSource();

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

   public void updateRoom(BoardRoom br)
   {
       String roomName = br.name;
       for (Player p : br.players )
       {
           File root = null;
           try
           {
              root = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());

              //picture = ImageIO.read( new File( root, "../../../../Sqrt_-11_Clue-Less/Source_Code/Client/App/Resources/Cards/" + cardName + ".png") );
              picture = ImageIO.read( new File( root, "/../Source_Code/Client/App/Resources/Rooms/" + roomName + ".png") );
              Image dimg = picture.getScaledInstance( 75, 90, Image.SCALE_SMOOTH );
              roomPicture.setIcon( new ImageIcon( dimg ) );
           }
           catch( Exception e )
           {
              e.printStackTrace();
              System.out.println(root + "\n" + roomName);
           }
       }


       repaint();
   }


   @Override
   public void toggleSelectable()
   {
      selectable = !selectable;
   }

   @Override
   public void select()
   {
      selected = true;
      this.setBorder(new BevelBorder(BevelBorder.LOWERED));
   }

   @Override
   public void deselect()
   {
      setBorder(null);
      selected = false;
   }



}
