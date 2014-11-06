package com.ericsson.mocs.flockmaster.sla.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.exceptions.SLAViolationResolverException;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;
import com.ericsson.mocs.flockmaster.service.description.SLADescription;
import com.ericsson.mocs.flockmaster.sla.SLAManager;
import com.ericsson.mocs.flockmaster.sla.message.SLAViolationMessage;
import com.ericsson.mocs.flockmaster.sla.policy.SLAViolationPolicyFactory;
import com.ericsson.mocs.flockmaster.sla.policy.api.SLAViolationPolicy;

/**
 * SLA violation resolver.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SLAViolationResolver implements Runnable {
    // The logger
    private static final Logger log_ = LoggerFactory.getLogger(SLAViolationResolver.class);
    
    // Violation message
    private SLAViolationMessage message_;

    // Policy
    private SLAViolationPolicy policy_;

    // SLA manager
    private SLAManager slaManager_;
    
    /**
     * Constructor.
     * 
     * @param slaManager    The SLA manager
     * @param message       The SLA violation message
     * @throws SLAViolationResolverException 
     */
    public SLAViolationResolver(SLAManager slaManager, SLAViolationMessage message) 
                    throws SLAViolationResolverException {
        log_.debug("Initializing SLA violation resolver for service: " + message.getServiceId());
        slaManager_ = slaManager;
        message_ = message;
        initializePolicy(message);
    }
    
    /**
     * Initializes the SLA violation policy.
     * 
     * @param message       The SLA violation message
     * @throws SLAViolationResolverException 
     */
    private void initializePolicy(SLAViolationMessage message) 
                    throws SLAViolationResolverException {
        FlockServiceDescription service = slaManager_.getServiceDescription(message.getServiceId());
        if (service == null) {
            throw new SLAViolationResolverException("Service not found!");
        }
        
        SLADescription slaDescription = service.getSlaDescription();
        if (slaDescription == null) {
            throw new SLAViolationResolverException("SLA description not available!");
        }
        
        policy_ = SLAViolationPolicyFactory.newSLAViolationPolicy(slaManager_, 
                                                                  slaDescription.getPolicy());        
    }
    
    @Override
    public void run() {
        try {
            policy_.resolveViolation(message_);
        } catch (Exception e) {
            e.printStackTrace();
        }
        slaManager_.onResume();
    }
}
