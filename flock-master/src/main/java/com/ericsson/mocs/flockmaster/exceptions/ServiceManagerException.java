package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Database factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class ServiceManagerException extends Exception {
    public ServiceManagerException(String message) {
        super(message);
    }
}
