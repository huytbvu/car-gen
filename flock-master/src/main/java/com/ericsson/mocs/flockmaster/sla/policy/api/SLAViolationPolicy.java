package com.ericsson.mocs.flockmaster.sla.policy.api;

import com.ericsson.mocs.flockmaster.sla.message.SLAViolationMessage;

/**
 * SLA violation policy interface.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public interface SLAViolationPolicy {
    void resolveViolation(SLAViolationMessage message) throws Exception;
}
