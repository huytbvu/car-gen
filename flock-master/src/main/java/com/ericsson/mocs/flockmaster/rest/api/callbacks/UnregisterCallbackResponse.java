package com.ericsson.mocs.flockmaster.rest.api.callbacks;

public class UnregisterCallbackResponse extends BaseCallbackResponse {
    private static final long serialVersionUID = 1L;

    public UnregisterCallbackResponse(CallbackStatus status) {
        super(status);
    }

    public UnregisterCallbackResponse(CallbackStatus status, String message) {
        super(status, message);
    }
}
