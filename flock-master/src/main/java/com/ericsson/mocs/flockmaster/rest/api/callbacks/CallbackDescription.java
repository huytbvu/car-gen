package com.ericsson.mocs.flockmaster.rest.api.callbacks;

import java.io.Serializable;

/**
 * Callback description.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class CallbackDescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serviceId_;
    private String callbackUri_;
    private String callbackType_;
    
    public CallbackDescription() { }
    
    public CallbackDescription(String serviceId, String callbackUri, String callbackType) {
        serviceId_ = serviceId;
        callbackUri_ = callbackUri;
        callbackType_ = callbackType;
    }
    
    public void setServiceId(String serviceId) {
        serviceId_ = serviceId;
    }
    
    public String getServiceId() {
        return serviceId_;
    }
    
    public void setCallbackUri(String callbackUri) {
        callbackUri_ = callbackUri;
    }
    
    public String getCallbackUri() {
        return callbackUri_;
    }
    
    public void setCallbackType(String callbackType) {
        callbackType_ = callbackType;
    }
    
    public String getCallbackType() {
        return callbackType_;
    }
    
    public String toString() {
        return String.format("Service ID: %s, callback URI: %s, callback type: %s", serviceId_, callbackUri_, callbackType_); 
    }
}
