package Common.Messages.ActionRequests;

public class SelectCharacterRequest extends ActionRequest
{
    public String RequestedCharacter;

    public SelectCharacterRequest(String from, String character)
    {
        super(from);
        RequestedCharacter = character;
    }
}
