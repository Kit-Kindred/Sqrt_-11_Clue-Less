package Common.Messages.ActionRequests;

import Common.SolutionHand;
import Common.Messages.ActionRequests.ActionRequest;

public class SuggestRequest extends ActionRequest
{
    public String PlayerName;
    public SolutionHand Hand;

    public SuggestRequest()
    {
        super();
    }

    public SuggestRequest(String name, SolutionHand h)
    {
        this.PlayerName = name;
        this.Hand = h;
    }
}
