package com.ericsson.mocs.flockmaster.config;

import com.ericsson.mocs.flockmaster.config.settings.CloudSettings;
import com.ericsson.mocs.flockmaster.config.settings.DatabaseSettings;
import com.ericsson.mocs.flockmaster.config.settings.MessagingSettings;
import com.ericsson.mocs.flockmaster.config.settings.RESTSettings;
import com.ericsson.mocs.flockmaster.config.settings.SLASettings;
import com.ericsson.mocs.flockmaster.config.settings.SchedulerSettings;

/**
 * The configuration.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class Configuration {
    private RESTSettings rest_;
    private MessagingSettings messaging_;
    private DatabaseSettings database_;
    private SLASettings sla_;
    private SchedulerSettings scheduler_;
    private CloudSettings cloud_;

    public RESTSettings getRest() {
        return rest_;
    }

    public void setRest(RESTSettings restApi) {
        rest_ = restApi;
    }
    
    public CloudSettings getCloud() {
        return cloud_;
    }

    public void setCloud(CloudSettings cloud) {
        cloud_ = cloud;
    }

    public DatabaseSettings getDatabase() {
        return database_;
    }

    public void setDatabase(DatabaseSettings database) {
        database_ = database;
    }

    public SchedulerSettings getScheduler() {
        return scheduler_;
    }

    public void setScheduler(SchedulerSettings scheduler) {
        scheduler_ = scheduler;
    }

    public MessagingSettings getMessaging() {
        return messaging_;
    }

    public void setMessaging(MessagingSettings messaging) {
        messaging_ = messaging;
    }

    public SLASettings getSla() {
        return sla_;
    }

    public void setSla(SLASettings sla) {
        sla_ = sla;
    }
}
