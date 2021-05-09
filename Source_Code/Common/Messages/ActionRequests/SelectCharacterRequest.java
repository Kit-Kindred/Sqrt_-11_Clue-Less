package Common.Messages.ActionRequests;

import Common.CharacterCard;

public class SelectCharacterRequest extends ActionRequest
{
    public CharacterCard.CharacterName RequestedCharacter;

    public SelectCharacterRequest(String from, CharacterCard.CharacterName character)
    {
        super(from);
        RequestedCharacter = character;
    }
}
