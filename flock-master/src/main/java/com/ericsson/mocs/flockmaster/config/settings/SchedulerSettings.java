package com.ericsson.mocs.flockmaster.config.settings;

import com.ericsson.mocs.flockmaster.scheduling.list.ServicePlacementAlgorithms;

/**
 * Scheduler settings.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SchedulerSettings {
    private ServicePlacementAlgorithms servicePlacement_;

    public ServicePlacementAlgorithms getServicePlacement() {
        return servicePlacement_;
    }

    public void setServicePlacement(ServicePlacementAlgorithms servicePlacement) {
        servicePlacement_ = servicePlacement;
    }
}
