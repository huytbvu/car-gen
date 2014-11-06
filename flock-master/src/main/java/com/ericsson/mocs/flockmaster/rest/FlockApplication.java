package com.ericsson.mocs.flockmaster.rest;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.rest.resources.CallbacksResource;
import com.ericsson.mocs.flockmaster.rest.resources.ServicesResource;

/**
 * Flock application.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public final class FlockApplication extends Application 
{  
    /** Define the logger. */
    private static final Logger log_ = LoggerFactory.getLogger(FlockApplication.class);

    /**
     * Constructor.
     * 
     * @param parentContext  The parent context
     * @throws Exception Exception
     */
    public FlockApplication(Context parentContext) 
        throws Exception 
    {
        super(parentContext);
        log_.debug("Starting the Flock application");
    }
    
    /**
     * Creates the inbound router.
     * 
     * @return  The router
     */
    public Restlet createInboundRoot() 
    {  
         Router router = new Router(getContext());  
         router.attach("/v1/api/services", ServicesResource.class);
         router.attach("/v1/api/services/{namespace}/{serviceId}", ServicesResource.class);
         router.attach("/v1/api/callbacks", CallbacksResource.class);
         router.attach("/v1/api/callbacks/{namespace}/{serviceName}/{callbackType}", CallbacksResource.class);
         return router;  
    }
}
