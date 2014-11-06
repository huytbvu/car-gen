package com.ericsson.mocs.flockmaster.rest.api.services;

import java.io.Serializable;

/**
 * Service start response.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class StartServiceResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private ServiceStatus code_;
    private String errorMessage_;
    
    public StartServiceResponse(ServiceStatus code) {
        code_ = code;
    }
    
    public StartServiceResponse(ServiceStatus code, String errorMessage) {
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
