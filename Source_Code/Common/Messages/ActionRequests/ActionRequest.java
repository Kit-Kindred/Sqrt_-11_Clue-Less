package Common.Messages.ActionRequests;

import Common.Messages.Message;

/**
 * Stub
 *
 * @author Kit Kindred
 *
 */
public class ActionRequest extends Message
{
    public String PlayerName;  // Every action's coming from a player

    public ActionRequest()
    {
        super();  // Just make a generic message for now
        PlayerName = "";
    }
}
