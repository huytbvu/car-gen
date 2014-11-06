package com.ericsson.mocs.flockmaster.database.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.DatabaseSettings;
import com.ericsson.mocs.flockmaster.database.api.Database;
import com.ericsson.mocs.flockmaster.database.utils.EtcdUtils;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.DatabaseException;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackDescription;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackType;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;
import com.ericsson.mocs.flockmaster.utils.JSONUtils;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdNode;
import com.justinsb.etcd.EtcdResult;

/**
 * Etcd database connector.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class EtcdDatabase implements Database {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(EtcdDatabase.class);
        
    // Client
    private EtcdClient client_;
    
    /**
     * Constructor.
     * 
     * @param settings      The database settings
     */
    public EtcdDatabase(DatabaseSettings settings) {
        client_ = EtcdUtils.initializeEtcdClient(settings);
        initializeDirectories();
    }
    
    /**
     * Initializes directories.
     */
    private void initializeDirectories() {
        try {
            if (!hasKey(EtcdUtils.SERVICES_DIRECTORY))
                client_.createDirectory(EtcdUtils.SERVICES_DIRECTORY);
            if (!hasKey(EtcdUtils.DATACENTERS_DIRECTORY))
                client_.createDirectory(EtcdUtils.DATACENTERS_DIRECTORY);
        } catch (EtcdClientException e) {
            log_.warn("Unable to create directory: " + e.getMessage());
        }
    }
    
    /**
     * Checks if datacenter exist.
     * 
     * @param datacenterId      The datacenter id
     * @return                  True if exist, false otherwise
     */
    private boolean hasKey(String key) {
        EtcdResult result = null;
        try {
            result = client_.get(key);
            if (result != null) {
                return true;
            }
        } catch (EtcdClientException e) {
            log_.warn("Unable to retrieve key: " + e.getMessage());
        }
        
        return false;
    }
   
    /**
     * Updates a datacenter.
     * 
     * @param datacenter    The datacenter
     */
    private void updateDatacenter(FlockDatacenter datacenter) {
        String key = getDatacenterKey(datacenter.getSettings().getId());
        log_.debug("Updating datacenter: " + key);
        try {
            client_.set(key, JSONUtils.objectToJSON(datacenter));  
        } catch (EtcdClientException e) {
            log_.warn("Unable to add datacenter: " + e.getMessage());
        }   
    }
    
    @Override
    public void addDatacenter(FlockDatacenter datacenter) {
        String datacenterId = datacenter.getSettings().getId();
        String key = getDatacenterKey(datacenter.getSettings().getId());
        try {
            if (hasKey(key)) {
                log_.debug(String.format("Datacenter %s already exist! Skipping!", datacenterId));
                return;
            }
            client_.set(key, JSONUtils.objectToJSON(datacenter));  
        } catch (EtcdClientException e) {
            log_.warn("Unable to add datacenter: " + e.getMessage());
        }   
    }

    @Override
    public FlockDatacenter getDatacenter(String datacenterId) {
        log_.debug("Retrieving datacenter: " + datacenterId);
        try {
            EtcdResult result = client_.get(getDatacenterKey(datacenterId));
            if (result == null) {
                log_.warn(String.format("No datacenter %s found", datacenterId));
                return null;
            }
            return JSONUtils.jsonToFlockDatacenterDescription(result.node.value);
        } catch (EtcdClientException e) {
            log_.error("Error retrieving datacenter: " + e.getMessage());
        }
        return null;
    }

    private void addServiceOptions(FlockServiceDescription service) 
                    throws EtcdClientException {
        String serviceDirectory = getServiceDirectory(service.getId());
        Map<String, String> discoveryOptions = service.getServiceOptions();
        for (Map.Entry<String, String> entry : discoveryOptions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            client_.set(serviceDirectory + "options/" + key, value);
        }        
    }
    
    @Override
    public void addService(FlockServiceDescription service, String datacenterId)
                    throws DatabaseException {
        log_.debug(String.format("Adding service %s to database", service.getId()));
        try {
            // Create mapping
            String serviceId = service.getId();
            String serviceDirectory = getServiceDirectory(serviceId);
            client_.createDirectory(serviceDirectory);
            client_.set(serviceDirectory + "datacenter", datacenterId);
            addServiceOptions(service);
            
            // Add service to DC
            FlockDatacenter datacenter = getDatacenter(datacenterId);
            datacenter.getServices().put(serviceId, service);
            updateDatacenter(datacenter);
        } catch (EtcdClientException e) {
            log_.warn("Failed to add service: " + e.getMessage());
        }
    }

    public String getDatacenterKey(String datacenterId) {
        return EtcdUtils.DATACENTERS_DIRECTORY + datacenterId;
    }
    
    public String getServiceDirectory(String serviceId) {
        return EtcdUtils.SERVICES_DIRECTORY + serviceId + "/";
    }
    
    @Override
    public FlockServiceDescription getService(String serviceId) {    
        String datacenterId = getDatacenterId(getServiceDirectory(serviceId));
        if (datacenterId == null) {
            log_.warn(String.format("No datacenter found for service %s", serviceId));
            return null;
        }
        
        FlockDatacenter datacenter = getDatacenter(datacenterId);
        return datacenter.getServices().get(serviceId);
    }

    @Override
    public List<FlockDatacenter> getDatacenters() throws Exception {
        List<FlockDatacenter> datacenters = new ArrayList<FlockDatacenter>();
        try {
            EtcdResult result = client_.get(EtcdUtils.DATACENTERS_DIRECTORY);
            List<EtcdNode> nodes = result.node.nodes;
            for (EtcdNode node: nodes) {
                datacenters.add(JSONUtils.jsonToFlockDatacenterDescription(node.value));
            }
        } catch (EtcdClientException e) {
            log_.error("Error retrieving datacenters: " + e.getMessage());
            throw new Exception(String.format("Etcd exception: %s", e.getMessage()));
        }
        return datacenters;
    }

    public String getDatacenterId(String serviceDirectory) {
        log_.debug("Searching for datacenter in: " + serviceDirectory);
        String datacenterId = null;
        try {
            List<EtcdNode> nodes = client_.listDirectory(serviceDirectory);
            if (nodes == null) {
                log_.warn(String.format("No directory %s found", serviceDirectory));
                return null;
            }
            for (EtcdNode node: nodes) {
                if (node.key.equals(serviceDirectory + "datacenter")) {
                    datacenterId = node.value;
                    break;
                }
            }  
        } catch (EtcdClientException e) {
            log_.warn("Exception in datacenter id retrieval: " + e.getMessage());
        }
        
        return datacenterId;
    }
    
    private void deleteDirectory(String directory) {     
        try {
            List<EtcdNode> nodes = client_.listDirectory(directory);
            if (nodes != null) {       
                for (EtcdNode node: nodes) {
                    client_.delete(node.key);
                }       
            }
            
            client_.deleteDirectory(directory);
        } catch (EtcdClientException e) {
            log_.warn(String.format("Unable to delete directory %s, exception: %s", directory, e.getMessage()));
        }
    }
    
    private void deleteServiceDirectory(String directory) {
        log_.debug("Deleting service directory: " + directory);
        try {
            deleteDirectory(directory + "options");
            deleteDirectory(directory + "callbacks"); 
            
            client_.delete(directory + "datacenter");
            deleteDirectory(directory);
            
            String[] split = directory.split("/");
            String namespace = EtcdUtils.SERVICES_DIRECTORY + split[split.length - 2];
            client_.deleteDirectory(namespace);
        } catch (EtcdClientException e) {
            log_.error("Failed to delete directory: " + e.getMessage());
        }
    }
    
    @Override
    public void removeService(String serviceId) {
        String serviceDirectory = getServiceDirectory(serviceId);
        String datacenterId = getDatacenterId(serviceDirectory);      
        if (datacenterId == null) {
            log_.error("Datacenter is NULL!");
            return;
        }
        
        deleteServiceDirectory(serviceDirectory);
        FlockDatacenter datacenter = getDatacenter(datacenterId);
        datacenter.getServices().remove(serviceId);
        updateDatacenter(datacenter);
    }

    @Override
    public FlockDatacenter getDatacenterForService(String serviceId) {
        try {
            EtcdResult result = client_.get(getServiceDirectory(serviceId) + "datacenter");
            return getDatacenter(result.node.value);
        } catch (EtcdClientException e) {
            log_.warn("Failed to retrieve datacenter: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean hasService(String serviceId) {
        EtcdResult result;
        try {
            result = client_.get(getServiceDirectory(serviceId));
            if (result != null) {
                return true;
            }
        } catch (EtcdClientException e) {
            log_.warn("Failed to retrieve service: " + e.getMessage());
        }
        
        log_.warn(String.format("No service %s found", serviceId));
        return false;
    }

    @Override
    public void addServiceCallback(CallbackDescription callbackDescription) 
                    throws DatabaseException {
        String callbacksDirectory = EtcdUtils.SERVICES_DIRECTORY + callbackDescription.getServiceId() + "/callbacks/";
        
        try {
            if (!hasKey(callbacksDirectory)) {
                client_.createDirectory(callbacksDirectory);
            }
            
            String callbackKey = callbacksDirectory + getCallbackMethod(CallbackType.valueOf(callbackDescription.getCallbackType()));
            client_.set(callbackKey, callbackDescription.getCallbackUri());
        } catch (Exception e) {
            log_.warn("Failed to add service callback: " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    private String getCallbackMethod(CallbackType callbackType) 
                    throws DatabaseException {
        switch (callbackType) {
            case slaViolation:
                return "sla_violation";
            case stopService:
                return "stop_service";
            default:
                throw new DatabaseException("Unknown callback type selected");
        }
    }
    
    @Override
    public void removeServiceCallback(String serviceId, CallbackType callbackType) 
                    throws DatabaseException {
        try {
            String callbackMethod = getCallbackMethod(callbackType);
            client_.delete(EtcdUtils.SERVICES_DIRECTORY + serviceId + "/callbacks/" + callbackMethod);
        } catch (EtcdClientException e) {
            log_.warn("Failed to remove service callback: " + e.getMessage());
            throw new DatabaseException(e.getMessage());
        }    
    }
}
