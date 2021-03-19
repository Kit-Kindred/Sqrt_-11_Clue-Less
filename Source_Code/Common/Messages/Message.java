package Common.Messages;

import java.io.Serializable;

/**
 * Only partially complete. Base message class to send between client and server
 *
 * @author Kit Kindred
 *
 */
public class Message implements Serializable
{
    public int UniqueID;

    public Message()
    {
        UniqueID = -2;
    }
}
