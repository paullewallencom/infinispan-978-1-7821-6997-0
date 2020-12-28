package com.packtub.infinispan.chapter12.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;

import com.packtub.infinispan.chapter12.domain.WeatherMap;

public class RESTClient {
  
  private static final Log logger = LogFactory.getLog(RESTClient.class);
  
  public String executeRESTOperation(String URI) throws MalformedURLException, IOException {
    logger.info("Execution with java.net");
    // Open Connection
    URL url = new URL(URI);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    // Define a HttpGet request
    conn.setRequestMethod("GET");
    // Set the MIMe type in HTTP header
    conn.setRequestProperty("accept", "application/json");
    validateResponseCode(conn);
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line;
    StringBuffer output = new StringBuffer();
    
    while ((line = in.readLine()) != null) {
      output.append(line);
    }
    in.close();
    conn.disconnect();
    // WeatherMap weather = WeatherMapConverter.convertJSONToJava(output.toString());
    logger.info("\nWeather Map");
    // logger.info(weather);
    logger.info(output);
    return output.toString();
  }
  
  public void validateResponseCode(HttpURLConnection conn) throws IOException {
    // Verify the status code of the response
    logger.infof(conn.getRequestMethod() + " Request >> HTTP Status %d : %s \n", conn.getResponseCode(),
        conn.getResponseMessage());
    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
      throw new RuntimeException("Error: HTTP Message>> " + conn.getResponseMessage());
    }
  }
  
}
