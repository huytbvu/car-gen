package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Cloud connector factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class DatacenterFactoryException extends Exception {
    public DatacenterFactoryException(String message) {
        super(message);
    }
}
