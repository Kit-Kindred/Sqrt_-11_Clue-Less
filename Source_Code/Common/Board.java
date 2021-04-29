package Common;
import Common.Messages.ActionRequests.MoveRequest;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines the set up of the Clue-less board with some useful helper 
 * classes so that other
 * 
 * @author Steve Nilla
 */
public class Board implements Serializable
{
  /**
   * Make board object serializable
   */
  private static final long serialVersionUID = 1792196563360444459L;
  
  // set up board
  public BoardRoom board[][] = new BoardRoom[5][5];
 
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

    // invalid index range given
    if (!((newX >= 0) && (newX <= 4)) | !((newY >= 0) && (newY <= 4)))
    {
      throw new IllegalArgumentException("Invalid move request");
    }

    // if room is an occupied hallway, player cannot move there
    if (board[newY][newX] instanceof BoardHallway)
    {
      if (((BoardHallway)board[newY][newX]).isOccupied())
      {
        throw new IllegalArgumentException("Hallway is occupied!");
      }
    }

    // try to move the player to the requested position
    try
    {
      // set new position
      player.setxPos(newX);
      player.setyPos(newY);

      // remove player from old room and assign to new room
      board[currY][currX].removePlayer(player);
      board[newY][newX].addPlayer(player);
    }
    catch(IllegalArgumentException | NullPointerException e)
    {
      throw new IllegalArgumentException("Invalid move request");
    }
  }

  public boolean inRoom(Player p)
  {
    return !(board[p.yPos][p.xPos] instanceof BoardHallway) && (board[p.yPos][p.xPos] != null);
  }

  public RoomCard.RoomName boardTextToRoomEnum(BoardRoom b)
  {
    switch(b.name)
    {
      case "Study" ->
              {
                return RoomCard.RoomName.values()[8];
              }
      case "Hall" ->
              {
                return RoomCard.RoomName.values()[4];
              }
      case "Lounge"->
              {
                return RoomCard.RoomName.values()[7];
              }
      case "Library"->
              {
                return RoomCard.RoomName.values()[6];
              }
      case "Billiard Room"->
              {
                return RoomCard.RoomName.values()[1];
              }
      case "Dining Room"->
              {
                return RoomCard.RoomName.values()[3];
              }
      case "Conservatory"->
              {
                return RoomCard.RoomName.values()[2];
              }
      case "Ball Room"->
              {
                return RoomCard.RoomName.values()[0];
              }
      case "Kitchen"->
              {
                return RoomCard.RoomName.values()[5];
              }
      default -> {return null;}  // Shouldn't be possible
    }
  }

  public void putPlayer(Player p)
  {
    board[p.getyPos()][p.getxPos()].addPlayer(p);
  }

  public void putPlayers(ArrayList<Player> players)
  {
    for (Player p : players)
    {
      board[p.getyPos()][p.getxPos()].addPlayer(p);
    }
    //printBoard();
  }

  /**
   * Please ignore how ugly this is...
   */
  public void printBoard()
  {
    String[] study = {"    ", "    ", "    ", "    ", "    ", "    ", };
    String[] hall = {"    ", "    ", "    ", "    ", "    ", "    ", };
    String[] lounge = {"    ", "    ", "    ", "    ", "    ", "    ", };

    String[] library = {"    ", "    ", "    ", "    ", "    ", "    ", };
    String[] billiard = {"    ", "    ", "    ", "    ", "    ", "    ", };
    String[] dining = {"    ", "    ", "    ", "    ", "    ", "    ", };

    String[] conservatory = {"    ", "    ", "    ", "    ", "    ", "    ", };
    String[] ballroom = {"    ", "    ", "    ", "    ", "    ", "    ", };
    String[] kitchen = {"    ", "    ", "    ", "    ", "    ", "    ", };

    String[][] hallways = {
            {"","    ","","    ",""},
            {"    ","","    ","","    "},
            {"","    ","","    ",""},
            {"    ","","    ","","    "},
            {"","    ","","    ",""}
    };

    for (int y = 0; y < board.length; y++)
    {
      for(int x = 0; x < board[0].length; x++)
      {
        if(board[y][x] instanceof BoardHallway)
        {
          if(((BoardHallway) board[y][x]).isOccupied())
          {
            hallways[y][x] = CharacterCard.characterTwoLetter(board[y][x].players.get(0).charName);
          }
        }
        else if (board[y][x] != null)
        {
          switch(board[y][x].name)
          {
            case "Study" ->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        study[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Hall" ->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        hall[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Lounge"->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        lounge[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Library"->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        library[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Billiard Room"->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        billiard[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Dining Room"->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        dining[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Conservatory"->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        conservatory[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Ball Room"->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        ballroom[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
            case "Kitchen"->
                    {
                      for(int i = 0; i < board[y][x].players.size(); i++)
                      {
                        kitchen[i] = CharacterCard.characterTwoLetter(board[y][x].players.get(i).charName);
                      }
                    }
          }
        }

      }
    }

    String boardStr = "====================================================================\n" +
            "====================================================================\n" +
            "||   study    |xxxxxxxxxxxx|    hall    |xxxxxxxxxxxx|   lounge   ||\n" +
            "|| " + study[0] +  "  " + study[1] +  " |============| " + hall[0] +  "  " + hall[1] +  " |============| " + lounge[0] +  "  " + lounge[1] +  " ||\n" +
            "|| " + study[2] +  "  " + study[3] +  "      " + hallways[0][1] + "      " + hall[2] +  "  " + hall[3] +  "      " + hallways[0][3] + "      " + lounge[2] +  "  " + lounge[3] +  " ||\n" +
            "|| " + study[4] +  "  " + study[5] +  " |============| " + hall[4] +  "  " + hall[5] +  " |============| " + lounge[4] +  "  " + lounge[5] +  " ||\n" +
            "||====    ====|xxxxxxxxxxxx|====    ====|xxxxxxxxxxxx|====    ====||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||xxx|" + hallways[1][0] +   "|xxxxxxxxxxxxxxxxxxxx|" + hallways[1][2] + "|xxxxxxxxxxxxxxxxxxxx|" + hallways[1][4] + "|xxx||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||====    ====|xxxxxxxxxxxx|====    ====|xxxxxxxxxxxx|====    ====||\n" +
            "||  library   |xxxxxxxxxxxx|  billiard  |xxxxxxxxxxxx|   dining   ||\n" +
            "|| " + library[0] +  "  " + library[1] +  " |============| " + billiard[0] +  "  " + billiard[1] +  " |============| " + dining[0] +  "  " + dining[1] +  " ||\n" +
            "|| " + library[2] +  "  " + library[3] +  "      " + hallways[2][1] + "      " + billiard[2] +  "  " + billiard[3] +  "      " + hallways[2][3] + "      " + dining[2] +  "  " + dining[3] +  " ||\n" +
            "|| " + library[4] +  "  " + library[5] +  " |============| " + billiard[4] +  "  " + billiard[5] +  " |============| " + dining[4] +  "  " + dining[5] +  " ||\n" +
            "||====    ====|xxxxxxxxxxxx|====    ====|xxxxxxxxxxxx|====    ====||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||xxx|" + hallways[3][0] +   "|xxxxxxxxxxxxxxxxxxxx|" + hallways[3][2] + "|xxxxxxxxxxxxxxxxxxxx|" + hallways[3][4] + "|xxx||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||xxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxxxxxxxxxxxxxxxxxxx|    |xxx||\n" +
            "||====    ====|xxxxxxxxxxxx|====    ====|xxxxxxxxxxxx|====    ====||\n" +
            "|| " + conservatory[0] +  "  " + conservatory[1] +  " |============| " + ballroom[0] +  "  " + ballroom[1] +  " |============| " + kitchen[0] +  "  " + kitchen[1] +  " ||\n" +
            "|| " + conservatory[2] +  "  " + conservatory[3] +  " |============| " + ballroom[2] +  "  " + ballroom[3] +  " |============| " + kitchen[2] +  "  " + kitchen[3] +  " ||\n" +
            "|| " + conservatory[4] +  "  " + conservatory[5] +  "      " + hallways[4][1] + "      " + ballroom[4] +  "  " + ballroom[5] +  "      " + hallways[4][3] + "      " + kitchen[4] +  "  " + kitchen[5] +  " ||\n" +
            "||conservatory|============|  ballroom  |============|   kitchen  ||\n" +
            "||============|xxxxxxxxxxxx|============|xxxxxxxxxxxx|============||\n" +
            "====================================================================\n";
    System.out.println(boardStr);
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

  public Board(Board b)
  {
    for (int y = 0; y < 5; y++)
    {
      for (int x = 0; x < 5; x++)
      {
        if (b.board[y][x] instanceof BoardHallway)
        {
          board[y][x] = new BoardHallway((BoardHallway) b.board[y][x]);
        }
        else if (b.board[y][x] != null)
        {
          board[y][x] = new BoardRoom(b.board[y][x]);
        }
      }
    }
  }
}