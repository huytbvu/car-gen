package com.ericsson.mocs.flockmaster.service.constraints;

import java.io.Serializable;

/**
 * Datacenter constraint.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class GlobalConstraint implements Serializable {
    private static final long serialVersionUID = 1L;
    private String datacenterId;
    
    public String getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(String datacenterId) {
        this.datacenterId = datacenterId;
    }
}
