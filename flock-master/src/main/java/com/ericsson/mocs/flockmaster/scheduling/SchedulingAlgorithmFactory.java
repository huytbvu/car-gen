package com.ericsson.mocs.flockmaster.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.exceptions.SchedulingAlgorithmFactoryException;
import com.ericsson.mocs.flockmaster.scheduling.api.ServicePlacement;
import com.ericsson.mocs.flockmaster.scheduling.api.impl.RoundRobinServicePlacement;
import com.ericsson.mocs.flockmaster.scheduling.list.ServicePlacementAlgorithms;

/**
 * Scheduler factory.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SchedulingAlgorithmFactory {
    private static final Logger log_ = LoggerFactory.getLogger(SchedulingAlgorithmFactory.class);
    
    /**
     * Instantiates a new service placement algorithm.
     * 
     * @param algorithm                                 The selected algorithm     
     * @return                                          The algorithm instance
     * @throws SchedulingAlgorithmFactoryException
     */
    public static ServicePlacement newServicePlacementAlgorithm(ServicePlacementAlgorithms algorithm) 
                    throws SchedulingAlgorithmFactoryException {
        log_.debug("Initializing service placement algorithm " + algorithm);
        switch (algorithm) {
            case roundrobin:
                return new RoundRobinServicePlacement();
            default:       
                throw new SchedulingAlgorithmFactoryException("Unsupported service placement algorithm selected");
        }
    }
}
