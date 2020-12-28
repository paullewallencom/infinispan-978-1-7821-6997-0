package com.packtpub.infinispan.chapter9.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map.Entry;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.ExhaustedAction;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter9.domain.Customer;

public class HotRodClientTest {
	private RemoteCache<String, Customer> rc;
	private static final String KEY = "k01";
	private Customer customer;
	private RemoteCacheManager rcm;

	private static final Log logger = LogFactory.getLog(HotRodClientTest.class);
	private static final String REALM = "ApplicationRealm";

	
	@Before
	public void setUp() throws Exception {
    	
		customer = new Customer();
		customer.setName("Wagner");
		customer.setAge(33);
		customer.setCredit(15000d);
		customer.setDoc("212.333.111");
		customer.setMaritalStatus("Married");
    	
    	ConfigurationBuilder builder = new ConfigurationBuilder();
    	builder.addServer().
    			  host("localhost").
    			  port(11222).
    			connectionPool().
    			  lifo(true).
    			  maxActive(10).
    			  maxIdle(10).
    			  maxTotal(20).
    			  exhaustedAction(ExhaustedAction.CREATE_NEW).
    			  timeBetweenEvictionRuns(120000).
    			  minEvictableIdleTime(1800000).
    			  minIdle(1);

		rcm = new RemoteCacheManager(builder.build());
		rc = rcm.getCache();    	
	}
	
	@After
	public void closeRemoteCM(){
		rc.stop();
		rcm.stop();
	}

	@Test
	public void executeHotRodOperations() {
		logger.infof("Using the %s.", rc.getProtocolVersion());
		logger.infof("Add a Customer %s in the Cache:",customer.getName());
		rc.put(KEY, customer);

		logger.info("Retrieving customer from remote cache");
		Customer customerFromCache = rc.get(KEY);
        assertEquals("Customer must be the same", customer, customerFromCache);		

		logger.infof("Removing customer from remote cache");
		rc.remove(KEY);
		customer = rc.get(KEY);
		assertNull( "Customer should be removed!", customer);

	}
	
	@Test
	public void printStatisticsFromRemoteCache(){
		logger.info("*** Server Statistics ***");
		for (Entry<String, String> entry : rc.stats().getStatsMap().entrySet()){
			logger.infof(">> %s",entry);			
		}
	}
	
}
