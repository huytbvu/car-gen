package com.ericsson.mocs.flockmaster.messaging.receiver.callback;

/**
 * Receiver callback.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public interface ReceiverCallback {
    void onMessageReceived(String body);
}
