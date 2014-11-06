package com.ericsson.mocs.flockmaster.config.settings;

import com.ericsson.mocs.flockmaster.config.common.NetworkAddress;
import com.ericsson.mocs.flockmaster.datacenter.type.DatacenterConnectorType;

/**
 * Datacenter description.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class DatacenterSettings {
    private String id;
    private DatacenterConnectorType connector;
    private NetworkAddress address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DatacenterConnectorType getConnector() {
        return connector;
    }

    public void setConnector(DatacenterConnectorType connector) {
        this.connector = connector;
    }

    public NetworkAddress getAddress() {
        return address;
    }

    public void setAddress(NetworkAddress address) {
        this.address = address;
    }
}
