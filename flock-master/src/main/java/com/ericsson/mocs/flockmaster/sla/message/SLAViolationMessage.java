package com.ericsson.mocs.flockmaster.sla.message;

/**
 * The SLA violation message.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SLAViolationMessage {
    private String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
