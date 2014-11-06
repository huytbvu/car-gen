package com.ericsson.mocs.flockmaster.rest.api.services;

import java.io.Serializable;

/**
 * Service stop repsonse.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class StopServiceResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private ServiceStatus code_;
    private String errorMessage_;
       
    public StopServiceResponse(ServiceStatus code) {
        code_ = code;;
    }
    
    public StopServiceResponse(ServiceStatus code, String errorMessage) {
        code_ = code;
        errorMessage_ = errorMessage;
    }

    public ServiceStatus getCode() {
        return code_;
    }

    public String getErrorMessage() {
        return errorMessage_;
    }
}
