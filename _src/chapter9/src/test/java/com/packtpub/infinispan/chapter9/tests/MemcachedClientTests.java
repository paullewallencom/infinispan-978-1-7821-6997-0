package com.packtpub.infinispan.chapter9.tests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter9.domain.Customer;

public class MemcachedClientTests {
  private Customer cust;
  private static final Log logger = LogFactory.getLog(MemcachedClientTests.class);
  private MemcachedClient memcachedClient;
  private static final String KEY = "key01";
  
  @Before
  public void setUp() throws Exception {
    cust = new Customer();
    cust.setName("Wagner");
    cust.setAge(33);
    cust.setCredit(15000d);
    cust.setDoc("212.333.111");
    cust.setMaritalStatus("Married");
    
    memcachedClient = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11211")).build();
    if (memcachedClient == null) {
      throw new NullPointerException("MemcachedClient is not started!");
    }
  }
  
  @Test
  public void addCustomer() throws TimeoutException, InterruptedException, MemcachedException {
    logger.infof("Execution with XMemcached. Using the %s Protocol.", memcachedClient.getProtocol());
    logger.infof("Add a Customer in the Cache!");
    memcachedClient.set(KEY, 0, cust);
    
    logger.infof("Retrieving the customer");
    Customer customerXML = memcachedClient.get(KEY);
    assertEquals("Customer should be the same", cust, customerXML);
    logger.infof("Retrieve customer >> ", cust);
    
    logger.infof("Deleting the customer");
    memcachedClient.delete(KEY);
    customerXML = memcachedClient.get(KEY);
    logger.infof("Customer after delete >>> %s", customerXML);
  }
  
}
