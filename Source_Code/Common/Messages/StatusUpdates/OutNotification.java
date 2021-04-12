package Common.Messages.StatusUpdates;

public class OutNotification extends StatusUpdate
{
    public String PlayerName;

    public OutNotification()
    {
        super();
    }

    public OutNotification(String name)
    {
        this.PlayerName = name;
    }
}
