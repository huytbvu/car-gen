package com.ericsson.mocs.flockmaster.service.description;

import java.util.List;

/**
 * Flock service description.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class FlockDatacenterServices {
    private List<FlockServiceDescription> apps;

    public List<FlockServiceDescription> getApps() {
        return apps;
    }

    public void setApps(List<FlockServiceDescription> apps) {
        this.apps = apps;
    }
}
