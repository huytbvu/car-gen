package com.ericsson.mocs.flockmaster.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.datacenter.DatacenterConnectorFactory;
import com.ericsson.mocs.flockmaster.datacenter.DatacenterManager;
import com.ericsson.mocs.flockmaster.datacenter.api.DatacenterConnector;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.ServiceTaskException;
import com.ericsson.mocs.flockmaster.rest.api.services.StartServiceResponse;
import com.ericsson.mocs.flockmaster.service.callback.ServiceManagerCallback;
import com.ericsson.mocs.flockmaster.service.constraints.GlobalConstraint;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * Service scheduler task.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class ServiceTask implements Runnable {
    // The logger
    private static final Logger log_ = LoggerFactory.getLogger(ServiceTask.class);
    
    // Service scheduler
    private ServiceManagerCallback serviceManager_;
    
    // Service description
    private FlockServiceDescription serviceDescription_;

    // Data center manager
    private DatacenterManager datacenterManager_;

    public ServiceTask(ServiceManagerCallback serviceManager, 
                       DatacenterManager datacenterManager, 
                       FlockServiceDescription serviceDescription) {
        log_.debug(String.format("Initializing a service task for %s", serviceDescription.getId()));
        serviceManager_ = serviceManager;
        datacenterManager_ = datacenterManager;
        serviceDescription_ = serviceDescription;
    }

    /**
     * Schedules the service.
     * 
     * @param globalConstraint      The datacenter constraint
     * @return                          The datacenter description
     * @throws ServiceTaskException 
     */
    private FlockDatacenter scheduleService(GlobalConstraint globalConstraint) 
                    throws Exception {
        FlockDatacenter datacenter;
        if (globalConstraint != null) {
            String datacenterId = globalConstraint.getDatacenterId();
            log_.debug(String.format("Datacenter constraint %s found...Igorning the scheduler...", datacenterId));
            datacenter = datacenterManager_.getDatacenter(datacenterId);
        } else {
            log_.debug("No constraints found! Triggering the scheduling algorithm");
            datacenter = serviceManager_.onScheduleService(serviceDescription_);
        }    
        
        if (datacenter == null) {
            throw new ServiceTaskException("Datacenter is NULL!");
        }
        
        return datacenter;
    }
    
    @Override
    public void run() {
        log_.debug("Running service task");
        FlockDatacenter datacenter;
        try {
            datacenter = scheduleService(serviceDescription_.getGlobalConstraint());
            DatacenterConnector connector = DatacenterConnectorFactory.createNewDatacenterConnector(datacenter.getSettings());
            StartServiceResponse response = connector.startService(serviceDescription_);
            serviceDescription_.setStatus(response.getCode());
            serviceManager_.onRegisterService(serviceDescription_, datacenter);
        } catch (Exception e) {
            log_.error("Exeception: " + e.getMessage());
        }
    }
}
