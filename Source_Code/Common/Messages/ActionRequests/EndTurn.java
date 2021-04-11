package Common.Messages.ActionRequests;

public class EndTurn extends ActionRequest
{
    public EndTurn()
    {
        this("");
    }

    public EndTurn(String playerName)
    {
        super(playerName);
    }
}
