package com.ericsson.mocs.flockmaster.sla.policy;

import com.ericsson.mocs.flockmaster.sla.SLAManager;
import com.ericsson.mocs.flockmaster.sla.policy.api.SLAViolationPolicy;
import com.ericsson.mocs.flockmaster.sla.policy.api.impl.SwitchProxy;

/**
 * SLA violation policy factory.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SLAViolationPolicyFactory {

    public static SLAViolationPolicy newSLAViolationPolicy(SLAManager slaManager, SLAPolicy policy) {
        switch (policy) {
            case switchproxy:
                return new SwitchProxy(slaManager);
            default:
                return null;
        }
    }
}
