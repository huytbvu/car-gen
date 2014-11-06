package com.ericsson.mocs.flockmaster.datacenter;

import java.util.List;

import com.ericsson.mocs.flockmaster.config.settings.CloudSettings;
import com.ericsson.mocs.flockmaster.config.settings.DatacenterSettings;
import com.ericsson.mocs.flockmaster.database.api.Database;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.DatacenterFactoryException;

/**
 * The datacenter manager.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class DatacenterManager {    
    private Database database_;

    public DatacenterManager(Database database, CloudSettings cloudSettings) 
                    throws DatacenterFactoryException {
        database_ = database;
        addDatacentersToDatabase(cloudSettings);
    }
    
    /**
     * Adds datacenters to the database.
     * 
     * @param cloudSettings                 The cloud settings
     * @throws DatacenterFactoryException
     */
    private void addDatacentersToDatabase(CloudSettings cloudSettings) 
                    throws DatacenterFactoryException {
        for (DatacenterSettings description : cloudSettings.getDatacenters()) {
            FlockDatacenter flockDatacenter = new FlockDatacenter(description);
            database_.addDatacenter(flockDatacenter);
        }
    }

    public FlockDatacenter getDatacenter(String datacenterId) {
        return database_.getDatacenter(datacenterId);
    }

    public List<FlockDatacenter> getDatacenters() throws Exception {
        return database_.getDatacenters();
    }
}
