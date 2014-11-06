package com.ericsson.mocs.flockmaster.datacenter.description;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ericsson.mocs.flockmaster.config.settings.DatacenterSettings;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * Datacenter description.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class FlockDatacenter implements Serializable {    
    // Serial version
    private static final long serialVersionUID = 1L;
        
    // The datacenter connector
    private DatacenterSettings settings;
    
    // The assigned services
    private Map<String, FlockServiceDescription> services;
    
    // Timestamp
    private long lastUsedTime;
    
    public FlockDatacenter() { 
        this.services = new ConcurrentHashMap<String, FlockServiceDescription>();
        this.lastUsedTime = new Date().getTime();
    }

    public FlockDatacenter(DatacenterSettings settings) {
        this.settings = settings;
        this.services = new ConcurrentHashMap<String, FlockServiceDescription>();
        this.lastUsedTime = new Date().getTime();
    }

    public void setServices(Map<String, FlockServiceDescription> services) {
        this.services = services;
    }
        
    public Map<String, FlockServiceDescription> getServices() {
        return services;
    }
    
    public long getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public DatacenterSettings getSettings() {
        return settings;
    }

    public void setSettings(DatacenterSettings settings) {
        this.settings = settings;
    }
}
