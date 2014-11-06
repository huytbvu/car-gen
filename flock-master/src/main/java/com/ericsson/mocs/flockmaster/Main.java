package com.ericsson.mocs.flockmaster;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.mocs.flockmaster.config.Configuration;
import com.ericsson.mocs.flockmaster.config.utils.ConfigUtils;
import com.ericsson.mocs.flockmaster.database.DatabaseFactory;
import com.ericsson.mocs.flockmaster.database.api.Database;
import com.ericsson.mocs.flockmaster.datacenter.DatacenterManager;
import com.ericsson.mocs.flockmaster.exceptions.DatabaseFactoryException;
import com.ericsson.mocs.flockmaster.exceptions.DatacenterFactoryException;
import com.ericsson.mocs.flockmaster.exceptions.SchedulingAlgorithmFactoryException;
import com.ericsson.mocs.flockmaster.rest.server.WebServer;
import com.ericsson.mocs.flockmaster.service.ServiceManager;
import com.ericsson.mocs.flockmaster.sla.SLAManager;

/**
 * Main file.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class Main {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(Main.class);
    
    /**
     * Prepares the logger
     */
    private static void prepareLogger() {
        String filename = System.getProperty("log4j.configuration");
        if (filename == null) {
            System.out.println("No log4j configuration file specified. Falling back to default");
            BasicConfigurator.configure();  
            return;
        } 
        
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Unable to load log4j properties file: " + file.getAbsolutePath());
            BasicConfigurator.configure();  
            return;
        }
    
        DOMConfigurator.configure(file.getAbsolutePath());      
    }
    
    /**
     * Main class.
     * 
     * @param args  The arguments
     */
    public static void main(String[] args) {
        prepareLogger();
        
        if (args.length < 1) {
            System.err.println("Usage: flock-master <config file>");
            System.exit(1);
        }
        
        log_.debug("Initializing flock master");
        try {
            Configuration configuration = ConfigUtils.loadConfigurationFromFile(args[0]);
            initialize(configuration);
        } catch (DatabaseFactoryException e) {
            log_.error(String.format("Database exception %s", e.getMessage()));
            System.exit(1);
        } catch (DatacenterFactoryException e) {
            log_.error(String.format("Datacenter exception %s", e.getMessage()));
            System.exit(1);
        } catch (SchedulingAlgorithmFactoryException e) {
            log_.error(String.format("Scheduling algorithm exception %s", e.getMessage()));
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Initializes the flock master.
     * 
     * @param configuration     The configuration
     * @throws Exception
     */
    private static void initialize(Configuration configuration) 
                    throws Exception {
        Database database = DatabaseFactory.createNewDatabase(configuration.getDatabase());
        DatacenterManager datacenterManagter = new DatacenterManager(database, configuration.getCloud());
        ServiceManager serviceManager = new ServiceManager(database, datacenterManagter, configuration.getScheduler());
        
        if (configuration.getSla().isEnabled()) {
            new SLAManager(database, configuration.getMessaging());
        }
        
        WebServer server = new WebServer(configuration.getRest());
        server.registerBackend("servicemanager", serviceManager);
        server.start();
    }
}
