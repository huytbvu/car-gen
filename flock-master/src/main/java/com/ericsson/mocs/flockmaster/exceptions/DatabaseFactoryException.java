package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Database factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class DatabaseFactoryException extends Exception {
    public DatabaseFactoryException(String message) {
        super(message);
    }
}
