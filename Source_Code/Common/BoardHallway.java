package Common;

import java.io.Serializable;

/**
 * This class defines the hallway type as an extension of the room type
 * 
 * @author Steve Nilla
 */
public class BoardHallway extends BoardRoom implements Serializable
{ 
  /** 
   * Get the occupied state of the hallway
   * 
   * @return occupied
   */
  public boolean isOccupied() 
  {
    return !getPlayers().isEmpty();
  }

  /**
   * Constructor to create Hallway object
   */
  public BoardHallway()
  {
    super("Hallway");
  }

  public BoardHallway(BoardHallway b)
  {
    super(b);
  }
}
