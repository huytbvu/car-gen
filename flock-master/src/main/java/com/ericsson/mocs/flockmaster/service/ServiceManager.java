package com.ericsson.mocs.flockmaster.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.SchedulerSettings;
import com.ericsson.mocs.flockmaster.database.api.Database;
import com.ericsson.mocs.flockmaster.datacenter.DatacenterConnectorFactory;
import com.ericsson.mocs.flockmaster.datacenter.DatacenterManager;
import com.ericsson.mocs.flockmaster.datacenter.api.DatacenterConnector;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.DatabaseException;
import com.ericsson.mocs.flockmaster.exceptions.SchedulingAlgorithmFactoryException;
import com.ericsson.mocs.flockmaster.exceptions.ServiceManagerException;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackDescription;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackType;
import com.ericsson.mocs.flockmaster.rest.api.services.ListServicesResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.ServiceStatus;
import com.ericsson.mocs.flockmaster.rest.api.services.StartServiceResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceRequest;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceResponse;
import com.ericsson.mocs.flockmaster.scheduling.SchedulingAlgorithmFactory;
import com.ericsson.mocs.flockmaster.scheduling.api.ServicePlacement;
import com.ericsson.mocs.flockmaster.service.callback.ServiceManagerCallback;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;
import com.ericsson.mocs.flockmaster.service.task.ServiceTask;

/**
 * Service manager.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class ServiceManager implements ServiceManagerCallback {
    // The logger
    private static final Logger log_ = LoggerFactory.getLogger(ServiceManager.class);

    // Executor service.
    private ExecutorService executorService_;
    
    // The datacenter manager
    private DatacenterManager datacenterManager_;

    // Scheduling algorithm
    private ServicePlacement placementAlgorithm_;

    // Database
    private Database database_;
    
    /**
     * Service manager.
     * 
     * @param database                  The database
     * @param datacenterManager         The datacenter manager
     * @param schedulerSettings         The scheduler settings
     * @throws SchedulingAlgorithmFactoryException 
     */
    public ServiceManager(Database database, 
                          DatacenterManager datacenterManager, 
                          SchedulerSettings schedulerSettings) 
                    throws SchedulingAlgorithmFactoryException {
        log_.debug("Initializing the service manager");
        database_ = database;
        datacenterManager_ = datacenterManager;
        executorService_ = Executors.newCachedThreadPool();
        placementAlgorithm_ = SchedulingAlgorithmFactory.newServicePlacementAlgorithm(schedulerSettings.getServicePlacement());
    }
    
    /**
     * Called in the event of start service.
     * 
     * @param serviceDescription  The service description
     * @return                    The start service response
     */
    @Override
    public StartServiceResponse onStartService(FlockServiceDescription serviceDescription)
                    throws ServiceManagerException {     
        if (serviceDescription == null) {
            return new StartServiceResponse(ServiceStatus.SERVICE_DESCRIPTION_NOT_FOUND,
                                            "Service description is NULL");
        }
        
        String serviceId = serviceDescription.getId();
        if (database_.hasService(serviceId)) {
            return new StartServiceResponse(ServiceStatus.SERVICE_ALREADY_RUNNING);
        }
        
        executorService_.execute(new ServiceTask(this, 
                                                 datacenterManager_, 
                                                 serviceDescription));
        return new StartServiceResponse(ServiceStatus.SERVICE_SUBMITTED);
    }

    /**
     * Called in the event of stop service.
     * 
     * @param serviceId   The service ID
     * @return            The stop service
     */
    @Override
    public StopServiceResponse onStopService(StopServiceRequest stopService)
                    throws ServiceManagerException {
        String serviceId = stopService.toString();
        if (!database_.hasService(serviceId)) {
            return new StopServiceResponse(ServiceStatus.SERVICE_NOT_FOUND, 
                                           "No entry in the database");
        }
        
        FlockDatacenter datacenter = database_.getDatacenterForService(serviceId);
        DatacenterConnector connector = DatacenterConnectorFactory.createNewDatacenterConnector(datacenter.getSettings());
        StopServiceResponse response = connector.stopService(serviceId);
        if (response.getCode() == ServiceStatus.SERVICE_STOPPED) {
            database_.removeService(serviceId);
        }
        return response;
    }

    /**
     * Lists the currently available services.
     * 
     * @return  The list of services
     */
    public ListServicesResponse onListServices() {
        List<FlockDatacenter> datacenters = null;
        String message = null;
        try {
            datacenters = database_.getDatacenters();
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        ListServicesResponse services = new ListServicesResponse(datacenters, message);
        return services;
    }

    /**
     * Returns the service description.
     * 
     * @param serviceId     The service identifier
     * @return              The service description
     */
    public FlockServiceDescription onGetService(String serviceId) {
        return database_.getService(serviceId);
    }
    
    /**
     * Called in the event of scheduling.
     * 
     * @param service        The service description
     * @return               The datacenter description
     * @throws Exception 
     */
    @Override
    public FlockDatacenter onScheduleService(FlockServiceDescription service)
                    throws Exception {
        return placementAlgorithm_.placeService(datacenterManager_, service);
    }

    /**
     * Called in the event of service registration.
     * 
     * @param service               The service description
     * @throws DatabaseException 
     */
    @Override
    public void onRegisterService(FlockServiceDescription service, FlockDatacenter datacenter) 
                    throws DatabaseException {
        database_.addService(service, datacenter.getSettings().getId());
        log_.debug(String.format("Service %s registered", service.getId()));
    }

    @Override
    public void onRegisterServiceCallback(CallbackDescription callbackDescription) 
                    throws DatabaseException {
        database_.addServiceCallback(callbackDescription);
    }

    @Override
    public void onUnregisterServiceCallback(String serviceId, CallbackType callbackType)
                    throws DatabaseException {
        database_.removeServiceCallback(serviceId, callbackType);       
    }
}
