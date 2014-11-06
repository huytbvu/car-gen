package com.ericsson.mocs.flockmaster.database.utils;

import java.net.URI;

import com.ericsson.mocs.flockmaster.config.settings.DatabaseSettings;
import com.justinsb.etcd.EtcdClient;

public class EtcdUtils {
    // Directories
    public static final String DATACENTERS_DIRECTORY = "/flock-master/datacenters/";
    public static final String SERVICES_DIRECTORY = "/flock-master/services/";
    
    public static EtcdClient initializeEtcdClient(DatabaseSettings settings) {
        String url = "http://" + settings.getAddress().getIp() + ":" + settings.getAddress().getPort();
        return new EtcdClient(URI.create(url));
    }
}
