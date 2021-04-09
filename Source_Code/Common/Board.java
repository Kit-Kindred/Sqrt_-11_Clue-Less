package Common;
import Common.Messages.ActionRequests.MoveRequest;

/**
 * This class defines the set up of the Clue-less board with some useful helper 
 * classes so that other
 * 
 * @author Steve Nilla
 */
public class Board
{
  // set up board
  BoardRoom board[][] = new BoardRoom[5][5];
 
  /**
   * The method to move a player on the board
   *
   * @param player
   * @param move
   */
  public void movePlayer(Player player, MoveRequest move) throws IllegalArgumentException
  {
    // get current player position
    int currX = player.getxPos();
    int currY = player.getyPos();

    // new player x,y positions
    int newX = currX;  // Make sure these have some kind of sensible value no matter what
    int newY = currY;

    // set prospective player location
    switch(move.moveDirection)
    {
      case UP:
        // toggle one space up
        newX = currX;
        newY = currY - 1;
        break;
      case DOWN:
        // toggle one space down
        newX = currX;
        newY = currY + 1;
        break;
      case LEFT:
        // toggle one space to the left
        newX = currX - 1;
        newY = currY;
        break;
      case RIGHT:
        // toggle one space to the right
        newX = currX + 1;
        newY = currY;
        break;
      case STAY:
        // do nothing
        newX = currX;
        newY = currY;
        break;
      case SHORTCUT:
        // move player diagonally across the board when they are in a corner
        if ((currX == 0) && (currY == 0))
        {
          newX = 4;
          newY = 4;
        }
        else if ((currX == 4) && (currY == 4))
        {
          newX = 0;
          newY = 0;
        }
        else if ((currX == 4) && (currY == 0))
        {
          newX = 0;
          newY = 4;
        }  
        else if ((currX == 0) && (currY == 4))
        {
          newX = 4;
          newY = 0;
        }  
        else
        {
          throw new IllegalArgumentException("Invalid move request");
        }
        break;
    }

    // if room is an occupied hallway, player cannot move there
    if (board[currX][currY] instanceof BoardHallway)
    {
      if (((BoardHallway)board[currX][currY]).isOccupied())
      {
        throw new IllegalArgumentException("Hallway is occupied!");
      }
    }

    // try to move the player to the requested position
    try
    {
      player.setxPos(newX);
      player.setyPos(newY);
    }
    catch(IllegalArgumentException e)
    {
      System.out.println(e);
    }

    // remove player from old room and assign to new room
    board[currX][currY].removePlayer(player);
    board[newX][newY].addPlayer(player);
  }

  /**
   * Default class constructor
   */
  public Board()
  {     
    // board row 1 
    board[0][0] = new BoardRoom("Study");
    board[0][1] = new BoardHallway();
    board[0][2] = new BoardRoom("Hall");
    board[0][3] = new BoardHallway();
    board[0][4] = new BoardRoom("Lounge");

    // board row 2
    board[1][0] = new BoardHallway();
    board[1][2] = new BoardHallway();
    board[1][4] = new BoardHallway();

    // board row 3
    board[2][0] = new BoardRoom("Library");
    board[2][1] = new BoardHallway();
    board[2][2] = new BoardRoom("Billiard Room");
    board[2][3] = new BoardHallway();
    board[2][4] = new BoardRoom("Dining Room");

    // board row 4
    board[3][0] = new BoardHallway();
    board[3][2] = new BoardHallway();
    board[3][4] = new BoardHallway();

    // board row 5
    board[4][0] = new BoardRoom("Conservatory");
    board[4][1] = new BoardHallway();
    board[4][2] = new BoardRoom("Ball Room");
    board[4][3] = new BoardHallway();
    board[4][4] = new BoardRoom("Kitchen");
  }

}