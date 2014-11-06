package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Database factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}
