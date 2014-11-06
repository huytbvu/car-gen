package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Database factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class SLAViolationResolverException extends Exception {
    public SLAViolationResolverException(String message) {
        super(message);
    }
}
