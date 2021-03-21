package Common.Messages.StatusUpdates;

/**
 * Sent when the server starts or ends the game
 *
 * @author Kit Kindred
 *
 */
public class GameStart extends StatusUpdate
{
    public boolean GameStarting;  // true if game starting; false if ending

    public GameStart()
    {
        this(true);
    }

    public GameStart(boolean starting)
    {
        GameStarting = starting;
    }
}
