package Common;

import java.io.Serializable;

/**
 * Only partially complete. Base message class to send between client and server
 *
 * @author Kit Kindred
 *
 */
public class Message implements Serializable
{
    /**
     * Not final
     */
    public enum MessageType
    {
        Unknown,
        StatusUpdate,
        ActionRequest
    }

    /**
     * What type of message is this? Used for processing after messages have been received
     */
    public MessageType type;

    /**
     * Default constructor. Probably shouldn't ever use this
     */
    public Message()
    {
        type = MessageType.Unknown;
    }

    /**
     * Useful constructor
     *
     * @param t - What type of message this is
     *
     */
    public Message(MessageType t)
    {
        type = t;
    }
}
