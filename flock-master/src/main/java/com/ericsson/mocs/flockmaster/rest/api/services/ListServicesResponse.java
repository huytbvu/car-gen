package com.ericsson.mocs.flockmaster.rest.api.services;

import java.io.Serializable;
import java.util.List;

import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;

/**
 * List services response.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class ListServicesResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<FlockDatacenter> datacenters_;
    private String errorMessage_;
        
    public ListServicesResponse(List<FlockDatacenter> datacenters, String errorMessage) {
        datacenters_ = datacenters;
        errorMessage_ = errorMessage;
    }

    public List<FlockDatacenter> getDatacenters() {
        return datacenters_;
    }

    public String getErrorMessage() {
        return errorMessage_;
    }
}
