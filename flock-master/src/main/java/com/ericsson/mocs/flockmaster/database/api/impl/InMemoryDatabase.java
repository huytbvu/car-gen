package com.ericsson.mocs.flockmaster.database.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.database.api.Database;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.DatabaseException;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackDescription;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackType;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * In-memory database implementation.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 *
 */
public class InMemoryDatabase implements Database {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(InMemoryDatabase.class);
    
    // Service to data center ids mappings
    private Map<String, String> services_;
    
    // Datacenter id to description mapping
    private Map<String, FlockDatacenter> datacenters_;
    
    /**
     * Constructor.
     */
    public InMemoryDatabase() {
        services_ = new ConcurrentHashMap<String, String>();
        datacenters_ = new ConcurrentHashMap<String, FlockDatacenter>();
    }
    
    /**
     * Adds datacenters.
     * 
     * @param datacenters   The list of datacenters
     */
    public void addDatacenter(FlockDatacenter datacenter) {
        String datacenterId = datacenter.getSettings().getId();
        if (datacenters_.containsKey(datacenterId)) {
            log_.warn("Datacenter already exist!");
            return;
        }
        datacenters_.put(datacenterId, datacenter);
    }
    
    /**
     * Adds a service.
     * 
     * @param service   The service description
     * @throws DatabaseException 
     */
    public void addService(FlockServiceDescription service, String datacenterId)
                    throws DatabaseException {
        String serviceId = service.getId(); 
        log_.debug("Adding service: " + serviceId); 
        
        if (services_.containsKey(serviceId)) {
            throw new DatabaseException("Service already exist!");
        }

        FlockDatacenter datacenter = datacenters_.get(datacenterId);
        if (datacenter == null) {
            throw new DatabaseException("No datacenter found!");
        }
        
        // Add service to data center
        datacenter.getServices().put(serviceId, service);
        // Update service to datacenter mapping
        services_.put(serviceId, datacenter.getSettings().getId());
    }
    
    /**
     * Returns a particular service.
     * 
     * @param serviceId    The service id
     * @return             The flock service description
     */
    public FlockServiceDescription getService(String serviceId) {
        log_.debug("Returning service description for: " + serviceId);
        if (!services_.containsKey(serviceId)) {
            log_.warn("No service mapping found for this service!");
            return null;
        }
        String datacenterId = services_.get(serviceId);
        if (!datacenters_.containsKey(datacenterId)) {
            log_.warn("No datacenter found");
            return null;
        }
        
        return datacenters_.get(datacenterId).getServices().get(serviceId);
    }
        
    /**
     * Returns a data center description.
     * 
     * @param datacenterId    The datacenter id
     * @return                The flock datacenter description
     */
    public FlockDatacenter getDatacenter(String datacenterId) {
        log_.debug("Getting description for datacenter: " + datacenterId);
        return datacenters_.get(datacenterId);
    }
    
    /**
     * Returns the available datacenters.
     * 
     * @return  The list of datacenters
     */
    public List<FlockDatacenter> getDatacenters() {
        log_.debug("Returning list of datacenters");
        List<FlockDatacenter> datacenters = new ArrayList<FlockDatacenter>(datacenters_.values());
        return datacenters;
    }

    @Override
    public void removeService(String serviceId) {
        log_.debug("Removing service: " + serviceId);
        String datacenterId = services_.get(serviceId);
        FlockDatacenter datacenter = datacenters_.get(datacenterId);
        // Remove service
        datacenter.getServices().remove(serviceId);
        // Remove mapping
        services_.remove(serviceId);
    }

    /**
     * Returns the datacenter description associated with a service.
     * 
     * @param serviceId     The service id
     */
    @Override
    public FlockDatacenter getDatacenterForService(String serviceId) {
        return datacenters_.get(services_.get(serviceId));
    }

    @Override
    public boolean hasService(String serviceId) {
        if (!services_.containsKey(serviceId)) {
            log_.warn("No service mapping found for this service!");
            return false;
        }
        
        return true;
    }

    @Override
    public void addServiceCallback(CallbackDescription callbackDescription)
                    throws DatabaseException {
        log_.debug("Callback registration not supported!");
    }

    @Override
    public void removeServiceCallback(String serviceId, CallbackType callbackType)
                    throws DatabaseException {
        log_.debug("Callback unregistration not supported!");
    }
}
