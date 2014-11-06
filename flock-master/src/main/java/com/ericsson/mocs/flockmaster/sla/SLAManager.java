package com.ericsson.mocs.flockmaster.sla;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.MessagingSettings;
import com.ericsson.mocs.flockmaster.database.api.Database;
import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.exceptions.MessagingFactoryException;
import com.ericsson.mocs.flockmaster.exceptions.SLAViolationResolverException;
import com.ericsson.mocs.flockmaster.messaging.MessagingFactory;
import com.ericsson.mocs.flockmaster.messaging.receiver.ReceiverConnector;
import com.ericsson.mocs.flockmaster.messaging.receiver.callback.ReceiverCallback;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;
import com.ericsson.mocs.flockmaster.sla.message.SLAViolationMessage;
import com.ericsson.mocs.flockmaster.sla.resolver.SLAViolationResolver;
import com.ericsson.mocs.flockmaster.utils.JSONUtils;

/**
 * The SLA manager.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SLAManager implements ReceiverCallback {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(SLAManager.class);
    
    // Execution service
    private ExecutorService executorService_;
    
    // Messaging service
    private MessagingSettings messagingSettings_;
    
    // Database
    private Database database_;
    
    // Stopps resolver
    private boolean isStopped_ = false;
    
    /**
     * Constructor.
     * 
     * @param database                      The database
     * @param messagingSettings             The messaging settings
     * @throws MessagingFactoryException
     */
    public SLAManager(Database database, MessagingSettings messagingSettings) 
                    throws MessagingFactoryException {
        log_.debug("Initializing the SLA manager");
        database_ = database;
        messagingSettings_ = messagingSettings;
        executorService_ = Executors.newCachedThreadPool();
        createReceiver();
    }
    
    /**
     * Create a new receiver.
     * 
     * @throws MessagingFactoryException
     */
    public void createReceiver() throws MessagingFactoryException {
        ReceiverConnector receiver = MessagingFactory.createNewReceiver(messagingSettings_,
                                                                        messagingSettings_.getQueues().get("sla"), 
                                                                        this);
        new Thread(receiver).start();
    }

    /**
     * Called in the event of message reception.
     * 
     * @param message       The message
     */
    @Override
    public void onMessageReceived(String message) {
        log_.debug(String.format("SLA violation message received: %s", message));
        if (isStopped_) {
            log_.debug("Ignorning message due to already pending SLA violation processing!");
            return;
        }
        
        SLAViolationMessage violation = JSONUtils.jsonToSLAViolationMessage(message);
        try {
            SLAViolationResolver handler = new SLAViolationResolver(this, violation);
            isStopped_ = true;
            executorService_.execute(handler);
        } catch (SLAViolationResolverException e) {
            log_.debug(String.format("Error during SLA resolution: %s", e.getMessage()));
            onResume();
        }
    }

    /**
     * Called in the event of a resume.
     */
    public void onResume() {
        isStopped_ = false;
    }
    
    /**
     * Returns the datacenter description.
     * 
     * @param serviceId     The service id
     * @return              The flock datacenter description
     */
    public FlockDatacenter getDatacenterFromService(String serviceId) {
        return database_.getDatacenterForService(serviceId);
    }
    
    /**
     * Returns the datacenter description.
     * 
     * @param datacenterId  The datacenter id
     * @return              The datacenter description
     */
    public FlockDatacenter getDatacenterDescription(String datacenterId) {
        return database_.getDatacenter(datacenterId);
    }
    /**
     * Returns the flock service description.
     * 
     * @param serviceId     The service identifier
     * @return              The flock service description
     */
    public FlockServiceDescription getServiceDescription(String serviceId) {
        return database_.getService(serviceId);
    }
}
