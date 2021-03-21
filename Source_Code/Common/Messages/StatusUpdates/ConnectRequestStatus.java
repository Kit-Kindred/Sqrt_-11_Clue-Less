package Common.Messages.StatusUpdates;

/**
 * Sent by the server upon connection request
 * Sent back to the server if connection request rejected
 *
 * @author Kit Kindred
 *
 */
public class ConnectRequestStatus extends StatusUpdate
{
    public boolean Joined;
    public int PlayerID;

    public ConnectRequestStatus(boolean joined, int playerID)
    {
        super();
        Joined = joined;
        PlayerID = playerID;
    }
}
