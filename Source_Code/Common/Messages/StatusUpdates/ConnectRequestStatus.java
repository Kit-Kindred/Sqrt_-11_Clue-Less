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
    public String PlayerName;

    public ConnectRequestStatus(boolean joined, String playerName)
    {
        super();
        Joined = joined;
        PlayerName = playerName;
    }
}
