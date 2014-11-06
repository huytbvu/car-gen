package com.ericsson.mocs.flockmaster.config.settings;

import java.io.Serializable;
import java.util.Map;

import com.ericsson.mocs.flockmaster.config.common.Authentication;
import com.ericsson.mocs.flockmaster.config.common.NetworkAddress;
import com.ericsson.mocs.flockmaster.messaging.connector.MessagingConnectorType;

/**
 * Messaging settings.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class MessagingSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private MessagingConnectorType connector_;
    private Authentication authentication_;
    private NetworkAddress address_;
    private Map<String, String> queues_;
    
    public MessagingSettings() { }
    
    public NetworkAddress getAddress() {
        return address_;
    }

    public void setAddress(NetworkAddress address) {
        address_ = address;
    }

    public Authentication getAuthentication() {
        return authentication_;
    }

    public void setAuthentication(Authentication authentication) {
        authentication_ = authentication;
    }

    public Map<String, String> getQueues() {
        return queues_;
    }

    public void setQueues(Map<String, String> queues) {
        queues_ = queues;
    }

    public MessagingConnectorType getConnector() {
        return connector_;
    }

    public void setConnector(MessagingConnectorType connector) {
        connector_ = connector;
    }
}