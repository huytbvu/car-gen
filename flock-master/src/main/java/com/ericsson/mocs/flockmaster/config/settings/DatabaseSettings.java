package com.ericsson.mocs.flockmaster.config.settings;

import com.ericsson.mocs.flockmaster.config.common.NetworkAddress;


/**
 * Database settings.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class DatabaseSettings {
    private DatabaseConnectorType connector_;
    private NetworkAddress address_;
    
    public enum DatabaseConnectorType {
        memory,
        etcd
    }

    public DatabaseConnectorType getConnector() {
        return connector_;
    }

    public void setConnector(DatabaseConnectorType connector) {
        connector_ = connector;
    }

    public NetworkAddress getAddress() {
        return address_;
    }

    public void setAddress(NetworkAddress address) {
        this.address_ = address;
    }
}
