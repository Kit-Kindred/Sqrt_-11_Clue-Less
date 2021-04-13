package Common;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines the Room type with some useful helper methods
 * 
 * @author Steve Nilla
 */
public class BoardRoom implements Serializable
{
  /**
   * Make BoardRoom object serializable
   */
  private static final long serialVersionUID = 3116940914504957764L;
  String name; // name of the room
  ArrayList<Player> players; // players in a room 

  /**
   * Set the name of the room
   * 
   * @param name
   */
  public void setName(String name) 
  {
    this.name = name;
  }

  /**
   * Get the name of the room
   * 
   * @return name
   */
  public String getName() 
  {
    return name;
  }

  /**
   * Get the players that are occupying a room
   * 
   * @return players
   */
  public List<Player> getPlayers() 
  {
    return players;
  }

  /**
   * Add a player to a room
   * 
   * @param player
   */
  public void addPlayer(Player player)
  {
    players.add(player);
  }

  /**
   * Remove a player from a room
   * 
   * @param player
   */
  public void removePlayer(Player player)
  {
    players.remove(player);
  }

  /**
   * Constructor to create Room object
   * 
   * @param name
   */
  public BoardRoom(String name)
  {
    setName(name);
    this.players = new ArrayList<Player>();
  }
}
