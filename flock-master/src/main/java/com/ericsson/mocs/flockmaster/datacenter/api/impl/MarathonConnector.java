package com.ericsson.mocs.flockmaster.datacenter.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.DatacenterSettings;
import com.ericsson.mocs.flockmaster.datacenter.api.DatacenterConnector;
import com.ericsson.mocs.flockmaster.datacenter.utils.HttpUtils;
import com.ericsson.mocs.flockmaster.datacenter.utils.RequestType;
import com.ericsson.mocs.flockmaster.rest.api.services.ServiceStatus;
import com.ericsson.mocs.flockmaster.rest.api.services.StartServiceResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceResponse;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * Marathon connector.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class MarathonConnector implements DatacenterConnector {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(MarathonConnector.class);
    
    // Uri
    private String uri_;
    
    /**
     * Constructor.
     * 
     * @param settings     The settings  
     */
    public MarathonConnector(DatacenterSettings settings) {
        log_.debug("Initializing marathon connector");
        uri_ = "http://" + settings.getAddress().getIp() +
                        ":" + settings.getAddress().getPort() +
                        "/v2/apps";
    }
    
    /**
     * Starts a service.
     * 
     * @param service   The service description
     * @return          The start response
     */
    @Override
    public StartServiceResponse startService(FlockServiceDescription service) {
       String serviceId = service.getId().replace("/", ".");
       try {
           MarathonServiceDescriptionV2 marathonService = 
                           new MarathonServiceDescriptionV2.Builder(serviceId, 
                                                                    service.getCpus(), 
                                                                    service.getMem(), 
                                                                    service.getInstances(),
                                                                    service.getCmd())
                           .setContainer(service.getContainer())
                           .setConstraints(service.getLocalConstraint())
                           .build();
           HttpUtils.sendHttpRequest(RequestType.POST,
                                     uri_, 
                                     marathonService.toString());
       } catch (Exception e) {
           e.printStackTrace();
           return new StartServiceResponse(ServiceStatus.SERVICE_FAILED_TO_START, e.getMessage());
       }
       
       log_.debug(String.format("Service %s started", serviceId));
       return new StartServiceResponse(ServiceStatus.SERVICE_STARTED);
    }

    /**
     * Stops a service.
     * 
     * @param service        The flock service description
     * @return               The stop response
     */
    @Override
    public StopServiceResponse stopService(String serviceId) {
        String newServicd = serviceId.replace("/", ".");
        try {           
            HttpUtils.sendHttpRequest(RequestType.DELETE,
                                      uri_ + "/" + newServicd,
                                      null);
        } catch (Exception e) {
            e.printStackTrace();
            return new StopServiceResponse(ServiceStatus.SERVICE_FAILED_TO_STOP, e.getMessage());
        }
        
        log_.debug(String.format("Service %s stopped", newServicd));
        return new StopServiceResponse(ServiceStatus.SERVICE_STOPPED);
    }
    
    /**
     * Restarts a service.
     * 
     * @param service       The service description
     */
    @Override
    public void restartService(FlockServiceDescription service) {
        log_.debug(String.format("Restarting service %s", service.getId()));
        stopService(service.getId());
        startService(service);
    }
}
