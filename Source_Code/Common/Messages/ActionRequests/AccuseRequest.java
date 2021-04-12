package Common.Messages.ActionRequests;

import Common.SuggestHand;

public class AccuseRequest extends ActionRequest
{
    public SuggestHand AccuseHand;

    public AccuseRequest()
    {
        super();
    }

    public AccuseRequest(String name, SuggestHand hand)
    {
        this.PlayerName = name;
        this.AccuseHand = hand;
    }
}
