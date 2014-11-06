package com.ericsson.mocs.flockmaster.config.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

import com.ericsson.mocs.flockmaster.config.Configuration;

/**
 * Utilities.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class ConfigUtils {   
    private ConfigUtils()
    {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Loads the configuration from a file.
     * 
     * @param fileName
     *            The file name
     * @return The configuration map
     * @throws FileNotFoundException
     *             If file is not found
     */
    public static Configuration loadConfigurationFromFile(String fileName) 
            throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(new File(fileName));
        Configuration config = yaml.loadAs(inputStream, Configuration.class);
        return config;
    }
}
