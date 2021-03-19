package Common.Messages.ActionRequests;

/**
 * Sent when a client wants to connect or reconnect to the server
 *
 * @author Kit Kindred
 *
 */
public class ConnectRequest extends ActionRequest
{
    public int PlayerID;
    public ConnectRequest()
    {
        super();
        PlayerID = -1;
    }

    public ConnectRequest(int pid)
    {
        this();
        PlayerID = pid;
    }
}
