package com.ericsson.mocs.flockmaster.config.settings;

import java.util.List;

/**
 * Datacenter settings.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class CloudSettings {
    private List<DatacenterSettings> datacenters_;
    
    public List<DatacenterSettings> getDatacenters() {
        return datacenters_;
    }

 
    public void setDatacenters(List<DatacenterSettings> datacenters) {
        datacenters_ = datacenters;
    }
}
