package Common.Messages.StatusUpdates;

/**
 * Sent to all players when a client joins the game
 *
 * @author Kit Kindred
 *
 */
public class PlayerConnection extends StatusUpdate
{
    public int PlayerID;
    public boolean Connected;  // True if connect; false if disconnect

    public PlayerConnection(int pid, boolean connected)
    {
        PlayerID = pid;
        Connected = connected;
    }
}
