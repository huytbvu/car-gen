package com.ericsson.mocs.flockmaster.scheduling.api.impl;

import java.util.List;

import com.ericsson.mocs.flockmaster.datacenter.DatacenterManager;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.scheduling.api.ServicePlacement;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * Round robin scheduler implementation.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class RoundRobinServicePlacement implements ServicePlacement {
    // The index
    private int index_;

    // Constructor
    public RoundRobinServicePlacement() {
        index_ = 0;
    }
    
    @Override
    public FlockDatacenter placeService(DatacenterManager datacenterManager, 
                                        FlockServiceDescription description) throws Exception {
        List<FlockDatacenter> datacenters = datacenterManager.getDatacenters();
        index_ = index_ % datacenters.size();
        FlockDatacenter datacenterDescription = datacenters.get(index_);
        index_ ++;
        return datacenterDescription;
    }
}
