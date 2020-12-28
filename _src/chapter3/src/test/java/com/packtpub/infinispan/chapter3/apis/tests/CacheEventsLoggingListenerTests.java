package com.packtpub.infinispan.chapter3.apis.tests;

import static org.junit.Assert.assertNull;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter3.listener.CacheLevelLoggingListener;
import com.packtpub.infinispan.chapter3.listener.CacheManagerLevelLoggingListener;

public class CacheEventsLoggingListenerTests extends BaseTest{

	private DefaultCacheManager cacheManager;
	private Cache<String, String> cache;

	@Before
	public void setUpBeforeClass() throws Exception {
		CacheLevelLoggingListener cacheListener = new CacheLevelLoggingListener();
		CacheManagerLevelLoggingListener cacheManagerListener = new CacheManagerLevelLoggingListener();
		cacheManager = new DefaultCacheManager(getSampleFile());
		// Add a listener to the cache manager component
		cacheManager.addListener(cacheManagerListener);
		cache = cacheManager.getCache("ExpirationCache");
		// Add a listener to the cache component
		cache.addListener(cacheListener);
		cacheManager.start();
	}

	/* Simple test methods to triggers some events */
	@Test
	public void testEntryAdd() {
		for (int x = 0; x < 100; x++)
			cache.put("key" + x, "Value " + x);
	}

	@Test
	public void testEntryManipulated() throws InterruptedException {
		// Wait 5 seconds
		Thread.sleep(5000);
		cache.remove("key0");
		cache.put("key10", "Value 174", 5, TimeUnit.SECONDS);
		cache.put("Key01", "Value 01");
	}

	@Test
	public void testEntriesEvicted() {
		cache.evict("key15");
		assertNull(cache.get("key15"));
		assertNull(cache.get("key1"));
	}

	@After
	public  void testCacheManagerNotification() {
		cacheManager.stop();
	}

}
