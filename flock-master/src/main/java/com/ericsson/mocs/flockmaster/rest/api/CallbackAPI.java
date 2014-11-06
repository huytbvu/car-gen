package com.ericsson.mocs.flockmaster.rest.api;

import org.restlet.resource.Delete;
import org.restlet.resource.Post;

import com.ericsson.mocs.flockmaster.rest.api.callbacks.CallbackDescription;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.RegisterCallbackResponse;
import com.ericsson.mocs.flockmaster.rest.api.callbacks.UnregisterCallbackResponse;

public interface CallbackAPI {
    @Post
    RegisterCallbackResponse registerCallback(CallbackDescription service);
    
    @Delete
    UnregisterCallbackResponse unregisterCallback();
}
