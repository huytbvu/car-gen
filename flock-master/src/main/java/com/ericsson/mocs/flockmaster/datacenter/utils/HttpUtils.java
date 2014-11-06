package com.ericsson.mocs.flockmaster.datacenter.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http utils.
 * 
 * @author Eugen Feller <eugen.feller@ericsson.com>
 */
public class HttpUtils {
    // Logger
    private static final Logger log_ = LoggerFactory.getLogger(HttpUtils.class);
    
    // Constructor
    private HttpUtils()
    {
        throw new UnsupportedOperationException();
    }
    
    private static String getBody(HttpResponse response) 
                    throws ParseException, IOException {
        String body = null;
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            body = EntityUtils.toString(responseEntity);
        }  
        
        return body;
    }
    /**
     * Sends an HTTP request.
     * 
     * @param requestType   The request type
     * @param uri           The URI
     * @param data          The data
     * @return              The response
     * @throws IOException 
     */
    public static ServerResponse sendHttpRequest(RequestType requestType, String uri, String data) 
                    throws Exception {
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setSocketTimeout(5000)
                                                   .setConnectTimeout(5000)
                                                   .setConnectionRequestTimeout(5000)
                                                   .build();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = null;
        String body = null;
        StringEntity params = null;
        int statusCode = -1;
        
        try {
            switch (requestType) {
                case POST:
                    HttpPost postRequest = new HttpPost(uri);
                    params = new StringEntity(data);
                    postRequest.addHeader("content-type", "application/json");
                    postRequest.setEntity(params);
                    postRequest.setConfig(requestConfig);
                    response = httpClient.execute(postRequest);
                    statusCode = response.getStatusLine().getStatusCode();
                    body = getBody(response);
                    break;
                case PUT:
                    HttpPut putRequest = new HttpPut(uri);
                    params = new StringEntity(data);
                    putRequest.addHeader("content-type", "application/json");
                    putRequest.setEntity(params);
                    putRequest.setConfig(requestConfig);
                    response = httpClient.execute(putRequest);
                    statusCode = response.getStatusLine().getStatusCode();
                    body = getBody(response);                  
                    break;
                case GET:
                    HttpGet getRequest = new HttpGet(uri);
                    getRequest.setConfig(requestConfig);
                    response = httpClient.execute(getRequest);
                    statusCode = response.getStatusLine().getStatusCode();
                    body = getBody(response);
                    break;
                case DELETE:
                    HttpDelete deleteRequest = new HttpDelete(uri);
                    deleteRequest.setConfig(requestConfig);
                    response = httpClient.execute(deleteRequest);
                    statusCode = response.getStatusLine().getStatusCode();
                    break;
                default:
                    throw new RuntimeException("Unknown request type selected");
            }
        } catch (Exception e) {
            log_.error("Error during HTTP request: " + e.getMessage());
            throw e;
        } finally {
            httpClient.close();
        }
        
        return new ServerResponse(statusCode, body);    
    }
}
