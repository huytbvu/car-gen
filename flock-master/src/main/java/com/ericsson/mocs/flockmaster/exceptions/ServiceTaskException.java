package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Database factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class ServiceTaskException extends Exception {
    public ServiceTaskException(String message) {
        super(message);
    }
}
