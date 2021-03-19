package Common;

import Common.SocketComms;

/**
 * No function pointers in Java, so we pass an interface with an overridden function instead
 *
 * @author Kit Kindred
 *
 */
public interface DisconnectCallback
{
    /**
     * Function that needs to be overridden by the client/server to define disconnect handling
     *
     * @param uid - The userID of the SocketComms that disconnected
     */
    void onDisconnect(int uid);
}
