package com.ericsson.mocs.flockmaster.utils;

import com.ericsson.mocs.flockmaster.datacenter.description.FlockDatacenter;
import com.ericsson.mocs.flockmaster.service.description.FlockDatacenterServices;
import com.ericsson.mocs.flockmaster.service.description.FlockServiceDescription;
import com.ericsson.mocs.flockmaster.sla.message.SLAViolationMessage;
import com.ericsson.mocs.flockmaster.sla.policy.api.impl.NATSSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON utils.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class JSONUtils {
    // Constructor
    private JSONUtils()
    {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Marathon service description to JSON>
     * 
     * @param serviceDescription    The service description
     * @return                      The JSON representation
     */
    public static String objectToJSON(Object serviceDescription) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(serviceDescription);
    }
    
    /**
     * Converts JSON to flock datacenter.
     * 
     * @param json      The JSON input
     * @return          The datacenter
     */
    public static FlockDatacenterServices jsonToMarathonDatacenterServices(String json) {
        Gson gson = new GsonBuilder().create();
        FlockDatacenterServices datacenter = gson.fromJson(json, FlockDatacenterServices.class);
        return datacenter;
    }

    /**
     * JSON to SLA Violation message.
     * 
     * @param json  The JSON input
     * @return      The message
     */
    public static SLAViolationMessage jsonToSLAViolationMessage(String json) {
        Gson gson = new GsonBuilder().create();
        SLAViolationMessage violation = gson.fromJson(json, SLAViolationMessage.class);
        return violation;
    }

    /**
     * JSON to NATS settings.
     * 
     * @param json      The JSON input
     * @return          The NATS settings object
     */
    public static NATSSettings jsonToNatsSettings(String json) {
        Gson gson = new GsonBuilder().create();
        NATSSettings violation = gson.fromJson(json, NATSSettings.class);
        return violation;
    }

    /**
     * JSON to flock datacenter.
     * 
     * @param json      The JSON description
     * @return          The flock datacenter
     */
    public static FlockDatacenter jsonToFlockDatacenterDescription(String json) {
        Gson gson = new GsonBuilder().create();
        FlockDatacenter description = gson.fromJson(json, FlockDatacenter.class);
        return description;
    }

    public static FlockServiceDescription jsonToFlockServiceDescription(String json) {
        Gson gson = new GsonBuilder().create();
        FlockServiceDescription service = gson.fromJson(json, FlockServiceDescription.class);
        return service;
    }
}
