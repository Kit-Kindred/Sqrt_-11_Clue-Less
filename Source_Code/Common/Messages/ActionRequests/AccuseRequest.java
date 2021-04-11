package Common.Messages.ActionRequests;

import Common.SolutionHand;

public class AccuseRequest extends ActionRequest
{
    public String PlayerName;
    public SolutionHand AccuseHand;

    public AccuseRequest()
    {
        super();
    }

    public AccuseRequest(String name, SolutionHand hand)
    {
        this.PlayerName = name;
        this.AccuseHand = hand;
    }
}
