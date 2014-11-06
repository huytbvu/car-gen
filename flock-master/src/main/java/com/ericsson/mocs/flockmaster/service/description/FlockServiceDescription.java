package com.ericsson.mocs.flockmaster.service.description;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ericsson.mocs.flockmaster.rest.api.services.ServiceStatus;
import com.ericsson.mocs.flockmaster.service.constraints.GlobalConstraint;
import com.ericsson.mocs.flockmaster.utils.JSONUtils;

/**
 * The service description.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class FlockServiceDescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private ContainerInfo container;
    private String id;
    private double cpus;
    private double mem;
    private double disk;
    private int instances;
    private String cmd;
    private List<List<String>> localConstraint;
    private GlobalConstraint globalConstraint;
    private SLADescription slaDescription;
    private Map<String, String> serviceOptions;
    private ServiceStatus status;

    public ContainerInfo getContainer() {
        return container;
    }

    public void setContainer(ContainerInfo container) {
        this.container = container;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCpus() {
        return cpus;
    }

    public void setCpus(double cpus) {
        this.cpus = cpus;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public List<List<String>> getLocalConstraint() {
        return localConstraint;
    }

    public void setLocalConstraint(List<List<String>> constraints) {
        this.localConstraint = constraints;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public double getMem() {
        return mem;
    }

    public void setMem(double mem) {
        this.mem = mem;
    }

    public double getDisk() {
        return disk;
    }

    public void setDisk(double disk) {
        this.disk = disk;
    }
    
    public GlobalConstraint getGlobalConstraint() {
        return globalConstraint;
    }

    public void setGlobalConstraint(GlobalConstraint globalConstraint) {
        this.globalConstraint = globalConstraint;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public SLADescription getSlaDescription() {
        return slaDescription;
    }

    public void setSlaDescription(SLADescription slaDescription) {
        this.slaDescription = slaDescription;
    }
   
    public Map<String, String> getServiceOptions() {
        return serviceOptions;
    }

    public void setServiceOptions(Map<String, String> serviceOptions) {
        this.serviceOptions = serviceOptions;
    }

    public String toString() {
        return JSONUtils.objectToJSON(this);
    }
}
