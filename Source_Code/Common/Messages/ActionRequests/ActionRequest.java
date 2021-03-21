package Common;

/**
 * Stub
 *
 * @author Kit Kindred
 *
 */
public class ActionRequest extends Message
{

    public int PlayerID;
    public ActionRequest()
    {
        super(MessageType.ActionRequest);  // Just make a generic message labelled as an AR for now
    }

    public ActionRequest(int pid)
    {
        super(MessageType.ActionRequest);  // Just make a generic message labelled as an AR for now
        this.PlayerID = pid
    }
}
