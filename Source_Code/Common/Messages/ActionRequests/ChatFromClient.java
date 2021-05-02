package Common.Messages.ActionRequests;

public class ChatFromClient extends ActionRequest
{
    public String ChatMessage;
    public String DestinationPlayer;
    public boolean ToAll;

    public ChatFromClient(String from, String to, String msg, boolean toAll)
    {
        super(from);
        DestinationPlayer = to;
        ChatMessage = msg;
        ToAll = toAll;
    }
}
