package Client.App.views;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

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

   // Ideally we dont use this one
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

   // New Constructor to take a BoardRoom in
   RoomPanel( BoardRoom br )
   {

      this.room = br;
      this.roomName = br.name;
      setLayout( new BorderLayout(0, 0) );
      setSize( 75, 90 );
      setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0) );

      pcs = new PropertyChangeSupport(this);


      /* There are occasional "blanks" on the board that don't have a room or hallway.
       * For these, we just want an empy panel
       */
      if( !br.name.equals( "" ) )
      {
         File root = null;
         try
         {
            root = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());

            //picture = ImageIO.read( new File( root, "../../../../Sqrt_-11_Clue-Less/Source_Code/Client/App/Resources/Cards/" + cardName + ".png") );
            picture = ImageIO.read( new File( root, "/../Source_Code/Client/App/Resources/Rooms/" + roomName + ".png") );
            addPlayerPieces();
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

      Image dimg = picture.getScaledInstance( 75, 90, Image.SCALE_SMOOTH );

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

   // method to impose character pieces on top of the room picture
   public void addPlayerPieces()
   {
       // If there are no players here, do nothing
       if ( !this.room.players.isEmpty() )
       {
           // For each character
           for (int ii = 0; ii < this.room.players.size(); ii++)
           {
               File root = null;

               try
               {
                    root = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());

                    // grab the picture
                    BufferedImage pieceImage = ImageIO.read( new File( root, "/../Source_Code/Client/App/Resources/Pieces/" + this.room.players.get(ii).charName + ".png") );
                    pieceImage = resizeImage(pieceImage, this.picture.getWidth() / 2 , this.picture.getHeight() / 3 );

                    // paste it on the correct spot
                    Graphics2D g2d_roomImage = this.picture.createGraphics();
                    g2d_roomImage.drawImage(this.picture, 0, 0, null);
                    g2d_roomImage.drawImage(pieceImage, (ii / 3) * this.picture.getWidth() / 2, (ii % 3) * this.picture.getHeight() / 3, null);
                    g2d_roomImage.dispose();
               }
               catch( Exception e )
               {
                  e.printStackTrace();
                  System.out.println(root + "\n" + roomName);
               }
           }
       }
   }

   // stole this real quick https://www.baeldung.com/java-resize-image
   private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
       BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
       Graphics2D graphics2D = resizedImage.createGraphics();
       graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
       graphics2D.dispose();
       return resizedImage;
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
