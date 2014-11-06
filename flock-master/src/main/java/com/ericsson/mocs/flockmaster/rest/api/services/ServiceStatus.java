package com.ericsson.mocs.flockmaster.rest.api.services;

/**
 * Service status.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public enum ServiceStatus {
    SERVICE_STARTED, 
    SERVICE_STOPPED, 
    SERVICE_ALREADY_RUNNING, 
    SERVICE_FAILED_TO_START, 
    SERVICE_FAILED_TO_STOP,
    SERVICE_DESCRIPTION_NOT_FOUND, 
    SERVICE_NOT_FOUND,
    SERVICE_SUBMITTED;
}