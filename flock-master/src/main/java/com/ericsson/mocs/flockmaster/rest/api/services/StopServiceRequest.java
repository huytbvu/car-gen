package com.ericsson.mocs.flockmaster.rest.api.services;

import java.io.Serializable;

/**
 * Stop service request.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class StopServiceRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String namespace_;
    private String serviceId_;

    public StopServiceRequest(String namespace, String serviceId) { 
        namespace_ = namespace;
        serviceId_ = serviceId;
    }
    
    public String getNamespace() {
        return namespace_;
    }
    
    public String getServiceId() {
        return serviceId_;
    }
    
    public String toString() {
        return namespace_ + "/" + serviceId_;
    }
}
