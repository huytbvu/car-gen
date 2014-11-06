package com.ericsson.mocs.flockmaster.service.callback;

import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.DatabaseException;
import com.ericsson.mocs.flockmaster.exceptions.ServiceManagerException;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackDescription;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackType;
import com.ericsson.mocs.flockmaster.rest.api.services.ListServicesResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StartServiceResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceRequest;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceResponse;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * Change server callback.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public interface ServiceManagerCallback {
    /**
     * Called in the event of start service.
     * 
     * @param flockServiceDescription       The service description
     * @return                              The start service response
     */
    StartServiceResponse onStartService(FlockServiceDescription flockServiceDescription) 
                    throws ServiceManagerException;
    
    /**
     * Called in the event of stop service.
     * 
     * @param stopServiceRequest      The stop service request
     * @return                        The stop service response
     */
    StopServiceResponse onStopService(StopServiceRequest stopServiceRequest) 
                    throws ServiceManagerException;
    
    /**
     * Called in the event of list services.
     * 
     * @return  The list of services
     */
    ListServicesResponse onListServices();

    /**
     * Called in the event of service scheduling.
     * 
     * @param serviceDescription        The service description
     * @return                          The datacenter description
     * @throws Exception 
     */     
    FlockDatacenter onScheduleService(FlockServiceDescription serviceDescription) 
                    throws Exception;

    /**
     * Called in the event of service registration.
     * 
     * @param serviceDescription    The service description
     * @param datacenter            The datacenter description
     * @throws DatabaseException 
     */
    void onRegisterService(FlockServiceDescription serviceDescription,
                           FlockDatacenter datacenter) 
                                           throws DatabaseException;

    /**
     * Called in the event of service callback registration.
     * 
     * @param callbackDescription   The callback description
     * @throws DatabaseException 
     */
    void onRegisterServiceCallback(CallbackDescription callbackDescription) 
                    throws DatabaseException;

    /**
     * Called in the event of service unregister event.
     * 
     * @param serviceId         The service identifier
     * @param callbackType      The callback method
     * @throws DatabaseException 
     */
    void onUnregisterServiceCallback(String serviceId, CallbackType callbackType)
                    throws DatabaseException;
}
