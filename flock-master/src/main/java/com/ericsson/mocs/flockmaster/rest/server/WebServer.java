package com.ericsson.mocs.flockmaster.rest.server;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.ext.httpclient.HttpClientHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.RESTSettings;
import com.ericsson.mocs.flockmaster.rest.FlockApplication;

/**
 * Web server.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class WebServer {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(WebServer.class);
    
    // API settings
    private RESTSettings restSettings_;
    
    // Component
    private Component component_ = new Component();
    
    // Context
    private Context context_ = component_.getContext().createChildContext();
    
    /**
     * Java web server.
     * 
     * @param restSettings   The API settings
     * @throws Exception 
     */
    public WebServer(RESTSettings restSettings) 
                    throws Exception {
        restSettings_ = restSettings;
        initializeServer();
        initializeApplication();
    }
    
    /**
     * Initializes the application.
     * 
     * @throws Exception
     */
    private void initializeApplication() 
        throws Exception {
        log_.debug("Initializing the application");
        Application application = new FlockApplication(context_);
        component_.getDefaultHost().attach(application);  
    }
    
    /** 
     * Starts the server.
     */
    public void start() { 
        try {
            component_.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes web server.
     * 
     * @param component        The component
     * @param context          The context
     * @param networkAddress   The network address
     * @throws Exception 
     */
    private void initializeServer() 
        throws Exception {
        log_.debug("Initializing the web server");
        
        Engine.getInstance().getRegisteredClients().clear();
        Engine.getInstance().getRegisteredClients().add(new HttpClientHelper(null)); 
        
        component_.getClients().add(Protocol.HTTP);
        
        Server jettyServer = new Server(context_,
                                        Protocol.HTTP, 
                                        restSettings_.getAddress().getIp(),
                                        restSettings_.getAddress().getPort(), 
                                        component_);
        component_.getServers().add(jettyServer);
    }

    /**
     * Registers backends.
     * 
     * @param string    The key
     * @param object    The value
     */
    public void registerBackend(String string, Object object) {
        context_.getAttributes().put(string, object);      
    }
}
