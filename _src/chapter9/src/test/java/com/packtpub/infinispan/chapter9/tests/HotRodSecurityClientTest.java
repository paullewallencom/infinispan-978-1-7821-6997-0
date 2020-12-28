package com.packtpub.infinispan.chapter9.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.ExhaustedAction;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.security.AuthorizationPermission;
import org.infinispan.security.impl.IdentityRoleMapper;

import com.packtpub.infinispan.chapter9.domain.Customer;
import com.packtpub.infinispan.chapter9.security.SecurityCallbackHandler;

public class HotRodSecurityClientTest {
	private RemoteCache<String, Customer> rc;
	private static final String KEY = "k01";
	private Customer customer;
	private RemoteCacheManager rcm;

	private static final Log logger = LogFactory.getLog(HotRodClientTest.class);
	private static final String REALM = "ApplicationRealm";

	//@Before
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
                minIdle(1).
                security().
                authentication().
                enable().
                serverName("localhost").
                saslMechanism("DIGEST-MD5").
                callbackHandler(new SecurityCallbackHandler("wsantos", "test123", REALM));

		rcm = new RemoteCacheManager(builder.build());
		rc = rcm.getCache("securedCache");
	}

	//@Test(expected=TransportException.class)
	public void expectedInvalidPasswordTest(){
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
    			  minIdle(1).
    		    security().
    	          authentication().
    	            enable().
    	            serverName("localhost").
    	            saslMechanism("DIGEST-MD5").
    	            callbackHandler(new SecurityCallbackHandler("wrsantos", "test1234", REALM));;

		rcm = new RemoteCacheManager(builder.build());
		rc = rcm.getCache("securedCache");
	}

	//@Test
	public void creatingSecureCacheProgrammatically(){
		 GlobalConfigurationBuilder global = new GlobalConfigurationBuilder();
		  global
		     .security()
		        .authorization()
		           .principalRoleMapper(new IdentityRoleMapper())
		           .role("admin")
		              .permission(AuthorizationPermission.ALL)
		           .role("supervisor")
		              .permission(AuthorizationPermission.EXEC)
		              .permission(AuthorizationPermission.READ)
		              .permission(AuthorizationPermission.WRITE)
		           .role("reader")
		              .permission(AuthorizationPermission.READ);

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
	    			  minIdle(1).
	    		    security().
	    	          authentication().
	    	            enable().
	    	            serverName("localhost").
	    	            saslMechanism("DIGEST-MD5").
	    	            callbackHandler(new SecurityCallbackHandler("wrsantos", "test123", "ApplicationRealm"));;

			rcm = new RemoteCacheManager(builder.build());
			rc = rcm.getCache("securedCache");
	}

	//@Test
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

	//@After
	public void closeRemoteCM(){
		rc.stop();
		rcm.stop();
	}
}
