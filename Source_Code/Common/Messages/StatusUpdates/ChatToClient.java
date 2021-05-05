package Common.Messages.StatusUpdates;

public class ChatToClient extends StatusUpdate
{
    public String ChatMessage;
    public String SendingPlayer;
    public boolean ToAll;

    public ChatToClient(String from, String msg, boolean toAll)
    {
        super();
        SendingPlayer = from;
        ChatMessage = msg;
        ToAll = toAll;
    }
}
