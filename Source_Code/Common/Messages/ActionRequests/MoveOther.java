package Common.Messages.ActionRequests;

import Common.CharacterCard;

public class MoveOther extends ActionRequest
{
    public int xPos;
    public int yPos;
    public CharacterCard.CharacterName cn;

    public MoveOther(int x, int y, CharacterCard.CharacterName name)
    {
        xPos = x;
        yPos = y;
        cn = name;
    }
}
