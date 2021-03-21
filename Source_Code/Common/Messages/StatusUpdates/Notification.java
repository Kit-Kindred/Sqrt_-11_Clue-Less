package Common.Messages.StatusUpdates;

/**
 * Essentially used for generic server printing
 * Maybe should be removed later? Maybe not
 *
 * @author Kit Kindred
 *
 */
public class Notification extends StatusUpdate
{
    public String NotificationText;
    public Notification()
    {
        this("");
    }
    public Notification(String notifText)
    {
        super();
        NotificationText = notifText;
    }
}
