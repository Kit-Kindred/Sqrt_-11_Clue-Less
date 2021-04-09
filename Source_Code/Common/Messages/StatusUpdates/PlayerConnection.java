package Common.Messages.StatusUpdates;

/**
 * Sent to all players when a client joins the game
 *
 * @author Kit Kindred
 *
 */
public class PlayerConnection extends StatusUpdate
{
    public String PlayerName;
    public boolean Connected;  // True if connect; false if disconnect

    public PlayerConnection(String name, boolean connected)
    {
        PlayerName = name;
        Connected = connected;
    }
}
