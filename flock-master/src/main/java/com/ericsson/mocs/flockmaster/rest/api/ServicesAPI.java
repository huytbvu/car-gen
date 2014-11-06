package com.ericsson.mocs.flockmaster.rest.api;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.ericsson.mocs.flockmaster.rest.api.services.ListServicesResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StartServiceResponse;
import com.ericsson.mocs.flockmaster.rest.api.services.StopServiceResponse;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;

/**
 * The services API definition.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public interface ServicesAPI {            
    @Post
    StartServiceResponse startService(FlockServiceDescription service);
    
    @Delete
    StopServiceResponse stopService();
    
    @Get
    ListServicesResponse listServices();
}
