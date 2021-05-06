package Client.App.views;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Common.Board;
import Common.BoardHallway;
import Common.BoardRoom;


/**
 * This is the panel that houses the game board. It holds a Board object
 * internally and draws a new board each time the board is updated.
 *
 * @author DavidC
 *
 */
public class GameBoardPanel extends JPanel
{

   Board                 board;
   RoomPanel[][]         rooms;
   PropertyChangeSupport pcs;


   public GameBoardPanel()
   {
//      setSize( 875, 420 );
      pcs = new PropertyChangeSupport( this );

      this.board = new Board();
      this.rooms = new RoomPanel[5][5];
      this.updateRooms();

      GridLayout grid = new GridLayout( 5, 5, 5, 5);
      this.setLayout( grid );

      // Add all the rooms to this panel one by one (in order!)

      for( int i = 0; i <= 4; i++ )
      {
         for( int j = 0; j <= 4; j++ )
         {
            if (this.board.board[i][j] instanceof BoardHallway )
            {
                add( rooms[i][j]  );
                System.out.println("Adding... " + this.board.board[i][j].getName());
            }

            else if (board.board[i][j] != null)
            {
                add( rooms[i][j]  );
                System.out.println("Adding... " + this.board.board[i][j].getName());
            }

            else
            {
                add( rooms[i][j]  );
                System.out.println("Adding empty space ");
            }
         }
      }

      revalidate();
      repaint();

   }


   /**
    * Updates the internal Board object to the passed board instance
    *
    * @param board
    */
   public void updateBoard( Board b )
   {
      pcs.firePropertyChange(
         new PropertyChangeEvent( b, "BoardUpate", this.board, b ) );
      this.board = b;
      this.updateRooms();

   }

   /**
   * Updates each of the rooms from the current board object
   */
   public void updateRooms()
   {
       for( int i = 0; i <= 4; i++ )
       {
          for( int j = 0; j <= 4; j++ )
          {
             if (this.board.board[i][j] instanceof BoardHallway )
             {
                 rooms[i][j] = new RoomPanel( (BoardRoom) this.board.board[i][j] );
             }

             else if (board.board[i][j] != null)
             {
                 rooms[i][j] = new RoomPanel( (BoardRoom) this.board.board[i][j] );
             }

             else
             {
                 rooms[i][j] = new RoomPanel( "" );
             }
          }
       }
   }


   /**
    * We redraw the board layout after being given a new board object.
    *
    * @param board
    *        The specific Board instance to draw.
    * @return
    */
   public void drawBoard( Board b )
   {
       this.board = b;
   }



   /*
    * Test main method.
    */
   public static void main( String[] args)
   {

      EventQueue.invokeLater( new Runnable()
      {

         public void run()
         {
            try
            {
               JFrame frame = new JFrame();

               frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
               frame.setBounds( 243, 156, 875, 420 );
               frame.add( new GameBoardPanel() );
               frame.setVisible( true );
            } catch( Exception e )
            {
               e.printStackTrace();
            }}} );
   }


}


/**
 * Listen for any board changes. If we get a new board, we draw it!
 *
 * @author DavidC
 *
 */
class GameBoardListener implements PropertyChangeListener
{

   @Override
   public void propertyChange( PropertyChangeEvent evt )
   {

      if( evt.getPropertyName().equals( "BoardUpate" ) )
      {
         ( (GameBoardPanel) evt.getSource() )
            .drawBoard( (Board) evt.getNewValue() );
      }

   }

}
