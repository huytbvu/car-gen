package com.ericsson.mocs.flockmaster.database.api;

import java.util.List;

import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.DatabaseException;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackDescription;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackType;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * The database interface.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public interface Database {
    // Datacenter management methods
    void addDatacenter(FlockDatacenter datacenter);
    FlockDatacenter getDatacenter(String datacenterId);
    List<FlockDatacenter> getDatacenters() throws Exception;
    
    // Service management methods
    void addService(FlockServiceDescription service, String datacenterId) 
                    throws DatabaseException;
    FlockServiceDescription getService(String serviceId);
    void removeService(String serviceId);
    FlockDatacenter getDatacenterForService(String serviceId);
    boolean hasService(String serviceId);
    
    // Callbacks
    void addServiceCallback(CallbackDescription callbackDescription)
                    throws DatabaseException;
    void removeServiceCallback(String serviceId, CallbackType callbackType) 
                    throws DatabaseException;
}
