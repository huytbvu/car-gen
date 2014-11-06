package com.ericsson.mocs.flockmaster.datacenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.DatacenterSettings;
import com.ericsson.mocs.flockmaster.datacenter.api.DatacenterConnector;
import com.ericsson.mocs.flockmaster.datacenter.api.impl.MarathonConnector;

/**
 * Datacenter connector factory.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class DatacenterConnectorFactory {
    private static final Logger log_ = LoggerFactory.getLogger(DatacenterConnectorFactory.class);
    
    /**
     * Creates a new datacenter connector.
     * 
     * @param settings
     * @return
     */
    public static DatacenterConnector createNewDatacenterConnector(DatacenterSettings settings) {
        DatacenterConnector connector = null;
        switch (settings.getConnector()) {
            case marathon:
                connector = new MarathonConnector(settings);
                break;
            default:
                log_.error("Unknown connector selected: " + settings.getConnector());
        }        
        return connector;
    }
}
