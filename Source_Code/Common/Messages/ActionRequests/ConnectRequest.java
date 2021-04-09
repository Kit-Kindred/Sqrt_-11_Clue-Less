package Common.Messages.ActionRequests;

/**
 * Sent when a client wants to connect or reconnect to the server
 *
 * @author Kit Kindred
 *
 */
public class ConnectRequest extends ActionRequest
{
    public ConnectRequest()
    {
        super();
    }

    public ConnectRequest(String pn)
    {
        this();
        PlayerName = pn;
    }
}
