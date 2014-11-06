package com.ericsson.mocs.flockmaster.rest.resources;

import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.exceptions.ServiceManagerException;
import com.ericsson.mocs.flockmaster.rest.api.ServicesAPI;
import com.ericsson.mocs.flockmaster.rest.api.services.ListServicesResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.ServiceStatus;
import com.ericsson.mocs.flockmaster.rest.api.services.StartServiceResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceRequest;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceResponse;
import com.ericsson.mocs.flockmaster.service.callback.ServiceManagerCallback;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * The services resource.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class ServicesResource extends ServerResource 
    implements ServicesAPI {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(ServicesResource.class);

    // The service manager callback
    private ServiceManagerCallback backend_;
    
    // Constructor
    public ServicesResource() {
        backend_ = (ServiceManagerCallback) getApplication().
                                            getContext().
                                            getAttributes().
                                            get("servicemanager");
    }
    
    /**
     * Sets the server.
     * 
     * @param description   The service description
     * @return              The response
     * @throws Exception
     */
    @Override
    public StartServiceResponse startService(FlockServiceDescription description) {        
        log_.debug("Received request to start service");
        try {
            StartServiceResponse response = backend_.onStartService(description);
            return response;
        } catch (ServiceManagerException e) {
            return new StartServiceResponse(ServiceStatus.SERVICE_FAILED_TO_START, e.getMessage());
        }
    }

    /**
     * Returns the current server.
     * 
     * @return           The stop service response
     */
    @Override
    public StopServiceResponse stopService() {
        String namespace = (String) getRequest().getAttributes().get("namespace");
        String serviceId = (String) getRequest().getAttributes().get("serviceId");
        log_.debug(String.format("Received request to stop service %s/%s", namespace, serviceId));

        try {
            StopServiceResponse response = backend_.onStopService(new StopServiceRequest(namespace, serviceId));
            return response;
        } catch (ServiceManagerException e) {
            e.printStackTrace();
            return new StopServiceResponse(ServiceStatus.SERVICE_FAILED_TO_STOP, e.getMessage());
        }
    }
    
   /**
    * Lists the currently running services.
    * 
    * @return The list of services
    */
    @Override
    public ListServicesResponse listServices() {
        log_.debug("Received request to list the available datacenters");
        return backend_.onListServices();
    }
} 