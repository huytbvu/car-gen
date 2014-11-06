package com.ericsson.mocs.flockmaster.messaging.receiver.impl;

import nats.client.Message;
import nats.client.MessageIterator;
import nats.client.Nats;
import nats.client.NatsConnector;
import nats.client.Subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.MessagingSettings;
import com.ericsson.mocs.flockmaster.messaging.receiver.ReceiverConnector;
import com.ericsson.mocs.flockmaster.messaging.receiver.callback.ReceiverCallback;

/**
 * NATS receiver.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class NATSReceiver extends ReceiverConnector {
    // The logging facility
    private static final Logger log_ = LoggerFactory.getLogger(NATSReceiver.class);
   
    // Nats connection
    private Nats nats_;
    
    // Subscription
    private Subscription subscription_;

    // Callback
    private ReceiverCallback callback_;
    
    /**
     * NATS writer.
     * 
     * @param settings     The settings
     * @param subject      The subject
     * @param callback     The callback
     */
    public NATSReceiver(MessagingSettings settings, String subject, ReceiverCallback callback) {
        log_.info("Initializing NATS receiver");
        callback_ = callback; 
        nats_ = new NatsConnector().addHost(generateConnectionString(settings)).connect();
        subscription_ = nats_.subscribe(subject);
    }
    
    /**
     * Generates a connection string
     * 
     * @param settings  The settings
     * @return          The connection string
     */
    private String generateConnectionString(MessagingSettings settings) {
        return "nats://" + settings.getAuthentication().getUsername() + ":" + settings.getAuthentication().getPassword() + 
                        "@" + settings.getAddress().getIp() + ":" + settings.getAddress().getPort();
    }
       
    // Run method
    public void run() {
        MessageIterator iterator = subscription_.iterator();
        while (true)
        {
            log_.debug("Waiting for messages");
            Message message = iterator.next();
            callback_.onMessageReceived(message.getBody());
        }
    }
}
