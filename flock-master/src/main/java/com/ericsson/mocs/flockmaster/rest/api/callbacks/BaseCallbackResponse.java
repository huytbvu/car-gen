package com.ericsson.mocs.flockmaster.rest.api.callbacks;

import java.io.Serializable;

/**
 * Base callback response.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public abstract class BaseCallbackResponse 
    implements Serializable {
    private static final long serialVersionUID = 1L;
    private CallbackStatus callbackStatus_;
    private String errorMessage_;
    
    public BaseCallbackResponse(CallbackStatus callbackStatus) {
        callbackStatus_ = callbackStatus;
    }
    
    public BaseCallbackResponse(CallbackStatus callbackStatus, String errorMessage) {
        callbackStatus_ = callbackStatus;
        errorMessage_ = errorMessage;
    }

    public CallbackStatus getCallbackStatus() {
        return callbackStatus_;
    }

    public String getErrorMessage() {
        return errorMessage_;
    }
}
