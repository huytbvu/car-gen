package com.ericsson.mocs.flockmaster.datacenter.api.impl;

import java.io.Serializable;
import java.util.List;

import com.ericsson.mocs.flockmaster.service.description.ContainerInfo;
import com.ericsson.mocs.flockmaster.utils.JSONUtils;

/**
 * The service description.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
@SuppressWarnings("unused")
public class MarathonServiceDescriptionV2 implements Serializable {
    private static final long serialVersionUID = 1L;
    private ContainerInfo container;
    private String id;
    private double cpus;
    private double mem;
    private double disk;
    private int instances;
    private String executor;
    private double taskRateLimit;
    private double tasksRunning;
    private double tasksStaged;
    private List<List<String>> constraints;
    private List<String> uris;
    private List<String> healthChecks;
    private List<Integer> ports;
    private String cmd;
    private String version;
    
    private MarathonServiceDescriptionV2(Builder builder) {
        container = builder.container;
        id = builder.id;
        cpus = builder.cpus;
        mem = builder.mem;
        disk = builder.disk;
        instances = builder.instances;
        executor = builder.executor;
        taskRateLimit = builder.taskRateLimit;
        tasksRunning = builder.tasksRunning;
        tasksStaged = builder.tasksStaged;
        constraints = builder.constraints;
        uris = builder.uris;
        healthChecks = builder.healthChecks;
        ports = builder.ports;
        cmd = builder.cmd;
        version = builder.version;
    }
    
    public static class Builder {
        private ContainerInfo container;
        private String id;
        private double cpus;
        private double mem;
        private double disk;
        private int instances;
        private String executor;
        private double taskRateLimit;
        private double tasksRunning;
        private double tasksStaged;
        private List<List<String>> constraints;
        private List<String> uris;
        private List<String> healthChecks;
        private List<Integer> ports;
        private String cmd;
        private String version;
        
        public Builder(String id,
                       double cpus,
                       double mem,
                       int instances,
                       String cmd) {
            this.id = id;
            this.cpus = cpus;
            this.mem = mem;
            this.instances = instances;
            this.cmd = cmd;
        }
        
        public Builder setContainer(ContainerInfo container) {
            this.container = container;
            return this;
        }
        
        public Builder setConstraints(List<List<String>> constraints) {
            this.constraints = constraints;
            return this;
        }

        public Builder setUris(List<String> uris) {
            this.uris = uris;
            return this;
        }

        public Builder setPorts(List<Integer> ports) {
            this.ports = ports;
            return this;
        }

        public Builder setTaskRateLimit(double taskRateLimit) {
            this.taskRateLimit = taskRateLimit;
            return this;
        }

        public Builder setTasksRunning(double tasksRunning) {
            this.tasksRunning = tasksRunning;
            return this;
        }
        
        public Builder setTasksStaged(double tasksStaged) {
            this.tasksStaged = tasksStaged;
            return this;
        }

        public Builder setExecutor(String executor) {
            this.executor = executor;
            return this;
        }

        public Builder setHealthChecks(List<String> healthChecks) {
            this.healthChecks = healthChecks;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setDisk(double disk) {
            this.disk = disk;
            return this;
        }
        
        public MarathonServiceDescriptionV2 build()
        {
           return new MarathonServiceDescriptionV2(this);
        }
    }
    
    public String toString() {
        return JSONUtils.objectToJSON(this);
    }
}
