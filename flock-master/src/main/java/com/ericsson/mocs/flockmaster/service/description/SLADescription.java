package com.ericsson.mocs.flockmaster.service.description;

import java.io.Serializable;

import com.ericsson.mocs.flockmaster.sla.policy.SLAPolicy;

/**
 * SLA description.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SLADescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private SLAPolicy policy;

    public SLAPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(SLAPolicy policy) {
        this.policy = policy;
    }
}
