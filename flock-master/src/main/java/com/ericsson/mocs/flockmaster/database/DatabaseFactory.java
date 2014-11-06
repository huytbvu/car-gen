package com.ericsson.mocs.flockmaster.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.settings.DatabaseSettings;
import com.ericsson.mocs.flockmaster.database.api.Database;
import com.ericsson.mocs.flockmaster.database.api.impl.EtcdDatabase;
import com.ericsson.mocs.flockmaster.database.api.impl.InMemoryDatabase;
import com.ericsson.mocs.flockmaster.exceptions.DatabaseFactoryException;

/**
 * Database factory.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class DatabaseFactory {
    private static final Logger log_ = LoggerFactory.getLogger(DatabaseFactory.class);
    
    public static Database createNewDatabase(DatabaseSettings settings) 
                    throws DatabaseFactoryException {
        log_.debug(String.format("Instantiating %s database", settings.getConnector()));
        switch (settings.getConnector()) {
            case memory:
                return new InMemoryDatabase();
            case etcd:
                return new EtcdDatabase(settings);
            default:
                throw new DatabaseFactoryException("Unknown database connector selected");
        }
    }
}
