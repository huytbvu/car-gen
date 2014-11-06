package com.ericsson.mocs.flockmaster.rest.api.callbacks;

public class RegisterCallbackResponse extends BaseCallbackResponse {
    private static final long serialVersionUID = 1L;

    public RegisterCallbackResponse(CallbackStatus status) {
        super(status);
    }

    public RegisterCallbackResponse(CallbackStatus status, String message) {
        super(status, message);
    } 
}
