package com.packtpub.infinispan.chapter9.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter9.domain.Customer;
import com.packtpub.infinispan.chapter9.utils.CustomerConverter;

public class RESTApacheClientTest {
  private Customer cust;
  private static final String URI_CREDIT_PENDING = "http://127.0.0.1:8080/rest/default/";
  private static final String KEY = "key01";
  private static final Log logger = LogFactory.getLog(RESTApacheClientTest.class);
  private CloseableHttpClient httpclient;
  private HttpClientContext localContext;
  private HttpHost target;
  
  @Before
  public void setUp() throws Exception {
    cust = new Customer();
    cust.setName("Wagner");
    cust.setAge(33);
    cust.setCredit(15000d);
    cust.setDoc("212.333.111");
    cust.setMaritalStatus("Married");
    
    target = new HttpHost("localhost", 8080, "http");
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()),
        new UsernamePasswordCredentials("wagner", "passw0rd@01"));
    httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
    // Create AuthCache instance
    AuthCache authCache = new BasicAuthCache();
    // Generate BASIC scheme object and add it to the local
    // auth cache
    BasicScheme basicAuth = new BasicScheme();
    authCache.put(target, basicAuth);
    
    // Add AuthCache to the execution context
    localContext = HttpClientContext.create();
    localContext.setAuthCache(authCache);
    
    HttpGet httpget = new HttpGet("/rest/namedCache");
    
    logger.info("Executing request " + httpget.getRequestLine() + " to target " + target);
    for (int i = 0; i < 3; i++) {
      CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);
      try {
        logger.info("----------------------------------------");
        logger.info(response.getStatusLine());
        EntityUtils.consume(response.getEntity());
      } finally {
        response.close();
      }
    }
  }
  
  @Test
  public void executeRESTOperationsWithJavaNet() throws Exception {
    HttpPut put = new HttpPut(URI_CREDIT_PENDING + KEY);
    
    String xmlData = CustomerConverter.convertJavaToXML(cust);
    StringEntity xmlEntity = new StringEntity(xmlData);
    put.setEntity(xmlEntity);
    CloseableHttpResponse response = httpclient.execute(target, put, localContext);
    
    // Verify the status code of the response
    StatusLine status = response.getStatusLine();
    if (response.getStatusLine().getStatusCode() >= 300) {
      throw new HttpResponseException(status.getStatusCode(), status.getReasonPhrase());
    }
    
    assertFalse(response.getStatusLine().getStatusCode() >= 300);
    
    logger.infof("PUT Request >> HTTP Status %d : %s \n", status.getStatusCode(), status.getReasonPhrase());
    
    // Define a HttpGet request
    HttpGet get = new HttpGet(URI_CREDIT_PENDING + KEY);
    // Set the MIMe type in HTTP header
    get.addHeader("accept", "application/xml");
    // Creating a custom ResponseHandler to handle responses
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
      public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
        // Checking the status code again for errors
        StatusLine status = response.getStatusLine();
        if (response.getStatusLine().getStatusCode() >= 300) {
          throw new HttpResponseException(status.getStatusCode(), status.getReasonPhrase());
        }
        logger.infof("GET Request >> HTTP Status %d : %s \n", status.getStatusCode(), status.getReasonPhrase());
        logger.info("\nResponse Header");
        logger.info("========================");
        for (Header header : response.getAllHeaders()) {
          logger.infof("%s : %s \n", header.getName(), header.getValue());
        }
        HttpEntity entity = response.getEntity();
        return entity != null ? EntityUtils.toString(entity) : null;
      }
    };
    // Send the request; It will return the response
    xmlData = httpclient.execute(target, get, responseHandler, localContext);
    logger.info("\nResponse Body");
    logger.info("========================");
    logger.info(xmlData);
    
    cust = CustomerConverter.convertXMLtoJava(xmlData);
    
    logger.info("\nJAXB Customer");
    logger.info(cust);
    
    logger.info("\nResponse Body of the HTTP GET Request");
    logger.info("========================");
    logger.info(xmlData);
    assertTrue(xmlData.indexOf("Wagner") > -1);
    
    logger.info("\nSend a Delete Request");
    
    // Define a HttpDelete request
    HttpDelete delete = new HttpDelete(URI_CREDIT_PENDING + KEY);
    // Set the MIMe type in HTTP header
    delete.addHeader("accept", "application/xml");
    
    // Send the delete request; It will return the response
    xmlData = httpclient.execute(target, delete, responseHandler, localContext);
    assertEquals(xmlData.indexOf("Wagner"), -1);
  }
  
  @After
  public void cleanup() {
    try {
      httpclient.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
