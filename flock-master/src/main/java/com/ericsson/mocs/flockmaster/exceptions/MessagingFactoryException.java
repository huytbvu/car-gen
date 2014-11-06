package com.ericsson.mocs.flockmaster.exceptions;

/**
 * Cloud connector factory exception.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("serial")
public class MessagingFactoryException extends Exception {
    public MessagingFactoryException(String message) {
        super(message);
    }
}
