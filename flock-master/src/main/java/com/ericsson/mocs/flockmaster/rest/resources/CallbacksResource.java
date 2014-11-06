package com.ericsson.mocs.flockmaster.rest.resources;

import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.rest.api.CallbackAPI;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackDescription;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackStatus;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackType;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.RegisterCallbackResponse;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.UnregisterCallbackResponse;
import com.ericsson.mocs.flockmaster.service.callback.ServiceManagerCallback;

public class CallbacksResource extends ServerResource
    implements CallbackAPI {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(CallbacksResource.class);
    private ServiceManagerCallback backend_;
    
    public CallbacksResource() {
        backend_ = (ServiceManagerCallback) getApplication().
                                            getContext().
                                            getAttributes().
                                            get("servicemanager");
    }
    
    @Override
    public RegisterCallbackResponse registerCallback(CallbackDescription callbackDescription) {
        log_.debug(String.format("Received register callback request: %s", callbackDescription));
        
        try {
            backend_.onRegisterServiceCallback(callbackDescription);
            log_.debug("Callback registered");
        } catch (Exception e) {
            return new RegisterCallbackResponse(CallbackStatus.CALLBACK_REGISTRATION_FAILED, e.getMessage());
        }
        
        return new RegisterCallbackResponse(CallbackStatus.CALLBACK_REGISTERED);
    }

    @Override
    public UnregisterCallbackResponse unregisterCallback() {
        String namespace = (String) getRequest().getAttributes().get("namespace");
        String serviceName = (String) getRequest().getAttributes().get("serviceName");
        String serviceId = namespace + "/" + serviceName;
        CallbackType callbackType = CallbackType.valueOf((String) getRequest().getAttributes().get("callbackType"));
        log_.debug(String.format("Received request to unregister callback %s from service %s", callbackType, serviceId));
        
        try {
            backend_.onUnregisterServiceCallback(serviceId, callbackType);
            log_.debug("Callback unregistered");
        } catch (Exception e) {
            return new UnregisterCallbackResponse(CallbackStatus.CALLBACK_UNREGISTRATION_FAILED, e.getMessage());
        }
       
        return new UnregisterCallbackResponse(CallbackStatus.CALLBACK_UNREGISTERED);
    }
}
