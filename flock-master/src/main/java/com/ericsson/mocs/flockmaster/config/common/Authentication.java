package com.ericsson.mocs.flockmaster.config.common;

import java.io.Serializable;

/**
 * Authentication.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class Authentication implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username_;
    private String password_;

    public String getUsername() {
        return username_;
    }

    public void setUsername(String username) {
        username_ = username;
    }

    public String getPassword() {
        return password_;
    }
    
    public void setPassword(String password) {
        password_ = password;
    }
}
