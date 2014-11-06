package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Database factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class SchedulingAlgorithmFactoryException extends Exception {
    public SchedulingAlgorithmFactoryException(String message) {
        super(message);
    }
}
