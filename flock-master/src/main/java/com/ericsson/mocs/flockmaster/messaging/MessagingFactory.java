package com.ericsson.mocs.flockmaster.messaging;

import com.ericsson.mocs.flockmaster.config.settings.MessagingSettings;
import com.ericsson.mocs.flockmaster.exceptions.MessagingFactoryException;
import com.ericsson.mocs.flockmaster.messaging.receiver.ReceiverConnector;
import com.ericsson.mocs.flockmaster.messaging.receiver.callback.ReceiverCallback;
import com.ericsson.mocs.flockmaster.messaging.receiver.impl.NATSReceiver;

/**
 * Messaging factory.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class MessagingFactory {
    public static ReceiverConnector createNewReceiver(MessagingSettings settings, String subject, ReceiverCallback callback) 
                    throws MessagingFactoryException {
        switch (settings.getConnector()) {
            case nats:
                return new NATSReceiver(settings, subject, callback);
            default:
                throw new MessagingFactoryException("Unknown receiver connector selected");
        }
    }
}
