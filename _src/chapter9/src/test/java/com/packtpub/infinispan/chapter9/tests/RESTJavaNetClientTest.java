package com.packtpub.infinispan.chapter9.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter9.domain.Customer;
import com.packtpub.infinispan.chapter9.utils.CustomerConverter;

public class RESTJavaNetClientTest {
  
  private Customer cust;
  private static final String URI_CREDIT_PENDING = "http://127.0.0.1:8080/rest/default/";
  private static final String KEY = "key02";
  private static final Log logger = LogFactory.getLog(RESTJavaNetClientTest.class);
  
  @Before
  public void setUp() throws Exception {
    cust = new Customer();
    cust.setName("Wagner");
    cust.setAge(33);
    cust.setCredit(15000d);
    cust.setDoc("212.333.111");
    cust.setMaritalStatus("Married");
    
  }
  
  @Test
  public void executeRESTOperationsWithHttpClient() throws Exception {
    logger.info("Execution with java.net");
    // Open Connection
    URL url = new URL(URI_CREDIT_PENDING + KEY);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/xml");
    
    String encoding = Base64.encodeBase64String("wagner:passw0rd@01".getBytes());
    conn.setRequestProperty("Authorization", "Basic " + encoding);
    String xmlData = CustomerConverter.convertJavaToXML(cust);
    assertTrue(xmlData.indexOf("Wagner") > -1);
    // Send POST request
    OutputStream os = conn.getOutputStream();
    os.write(xmlData.getBytes());
    os.flush();
    
    validateResponseCode(conn);
    conn.disconnect();
    // Reopen connection to send GET request
    conn = (HttpURLConnection) url.openConnection();
    // Define a HttpGet request
    conn.setRequestMethod("GET");
    // Set the MIMe type in HTTP header
    conn.setRequestProperty("accept", "application/xml");
    conn.setRequestProperty("Authorization", "Basic " + encoding);
    validateResponseCode(conn);
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line;
    StringBuffer output = new StringBuffer();
    
    while ((line = in.readLine()) != null) {
      output.append(line);
    }
    assertTrue(output.indexOf("Wagner") > -1);
    in.close();
    conn.disconnect();
    cust = CustomerConverter.convertXMLtoJava(output.toString());
    logger.info("\nJAXB Customer");
    logger.info(cust);
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
