package com.ericsson.mocs.flockmaster.scheduling.api;

import com.ericsson.mocs.flockmaster.datacenter.DatacenterManager;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * Scheduler API.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public interface ServicePlacement {
    FlockDatacenter placeService(DatacenterManager datacenterManager,
                                 FlockServiceDescription serviceDescription) throws Exception;
}
