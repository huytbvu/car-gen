package com.ericsson.mocs.flockmaster.config.settings;

/**
 * SLA settings.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class SLASettings {
    private boolean enabled_;
    
    public boolean isEnabled() {
        return enabled_;
    }

    public void setEnabled(boolean enabled) {
        enabled_ = enabled;
    }
}
