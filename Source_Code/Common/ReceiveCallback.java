package Common;

/**
 * No function pointers in Java, so we pass an interface with an overridden function instead
 *
 * @author Kit Kindred
 *
 */
public interface ReceiveCallback
{
    /**
     * Function that needs to be overridden by the client/server to define message handling
     *
     * @param msg - The received message
     */
    void onRecv(Message msg);
}
