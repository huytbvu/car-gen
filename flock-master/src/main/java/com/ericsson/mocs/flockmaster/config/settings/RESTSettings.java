package com.ericsson.mocs.flockmaster.config.settings;

import com.ericsson.mocs.flockmaster.config.common.NetworkAddress;

/**
 * REST API settings.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class RESTSettings {
    // The network address
    private NetworkAddress address_;

    /**
     * Returns the network address.
     * 
     * @return The network address
     */
    public NetworkAddress getAddress() {
        return address_;
    }

    /**
     * Sets the network address.
     * 
     * @param address   The network address
     */
    public void setAddress(NetworkAddress address) {
        address_ = address;
    }
}
