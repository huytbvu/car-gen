package com.ericsson.mocs.flockmaster.datacenter.api;

import com.ericsson.mocs.flockmaster.rest.api.services.StartServiceResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceResponse;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * Datacenter connector interface.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public interface DatacenterConnector {
    StartServiceResponse startService(FlockServiceDescription service);
    StopServiceResponse stopService(String serviceId);
    void restartService(FlockServiceDescription service);
}
