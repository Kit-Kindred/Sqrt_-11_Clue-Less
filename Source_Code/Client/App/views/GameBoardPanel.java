package Client.App.views;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Common.Board;
import Common.BoardRoom;
import Common.BoardHallway;


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
   RoomPanel[][]        rooms;
   PropertyChangeSupport pcs;


   public GameBoardPanel()
   {
//      setSize( 875, 420 );
      pcs = new PropertyChangeSupport( this );

      board = new Board();
      this.rooms = new RoomPanel[5][5];

      GridLayout grid = new GridLayout( 5, 5, 5, 5);
      this.setLayout( grid );

      // Add all the rooms to this panel one by one (in order!)
      for( int i = 0; i <= 4; i++ )
      {
         for( int j = 0; j <= 4; j++ )
         {
            if (board.board[i][j] instanceof BoardHallway )
            {
                rooms[i][j] = new RoomPanel( board.board[i][j].getName() );
               add( rooms[i][j] );
               System.out.println("Adding... " + board.board[i][j].getName());
            }

            else if (board.board[i][j] != null)
            {
                rooms[i][j] = new RoomPanel( board.board[i][j].getName() );
               add( rooms[i][j] );
               System.out.println("Adding... " + board.board[i][j].getName());
            }

            else
            {
                rooms[i][j] = new RoomPanel( "" );
               add( rooms[i][j] );
               System.out.println("Adding empty space ");
            }
         }
      }

      revalidate();
      repaint();

   }


   /**
    * Updates the internal Board object to the passed board instance.
    * Does this by iterating through each RoomPanel and passing it the updateBoard
    * BoardRoom from the board passed
    *
    * @param board
    */
   public void updateBoard( Board b )
   {
      this.board = b;
      this.updateRooms();
      repaint();

   }

   /* Iterates through each of the RoomPanels and updates according to the current
   * board
   *
   */
   public void updateRooms()
   {
       for( int i = 0; i <= 4; i++ )
       {
          for( int j = 0; j <= 4; j++ )
          {
              if (this.board.board[i][j] instanceof BoardRoom)
              {
                  this.rooms[i][j].updateRoom(this.board.board[i][j]);
              }
          }
      }
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
