package com.ericsson.mocs.flockmaster.config.common;

/**
 * Network address.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class NetworkAddress {
    private String ip;
    private int port;
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getIp() {
        return this.ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return this.port;
    }
}
