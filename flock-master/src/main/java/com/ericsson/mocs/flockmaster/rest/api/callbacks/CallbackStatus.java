package com.ericsson.mocs.flockmaster.rest.api.callbacks;

/**
 * Callback status.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public enum CallbackStatus {
    CALLBACK_UNREGISTERED,
    CALLBACK_UNREGISTRATION_FAILED, 
    CALLBACK_REGISTERED,
    CALLBACK_REGISTRATION_FAILED
}
