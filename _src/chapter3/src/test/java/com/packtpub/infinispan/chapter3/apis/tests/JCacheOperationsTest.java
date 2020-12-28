package com.packtpub.infinispan.chapter3.apis.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

import org.junit.Before;
import org.junit.Test;

public class JCacheOperationsTest {
	private CacheManager cacheManager;

	@Before
	public void setUp() throws Exception {
		// resolve a cache manager
		CachingProvider cachingProvider = Caching.getCachingProvider();
		cacheManager = cachingProvider.getCacheManager();
	}

	@Test
	public void testJCacheOperations() {
		// create the cache
		cacheManager.createCache("_TempCache", getJCacheConfiguration());
		// get the cache
		Cache<String, String> tempCache = cacheManager.getCache("_TempCache",
				String.class, String.class);

		// cache operations
		String key = "20130801";
		String tempData = "Children are great comfort in your old age, and they help you to reach it faster too";
		tempCache.put(key, tempData);
		String otherTempData = tempCache.get(key);
		assertEquals(otherTempData, tempData);
		tempCache.remove(key);
		assertNull(tempCache.get(key));
	}

	private Configuration<String, String> getJCacheConfiguration() {
		// configure the cache
		MutableConfiguration<String, String> config = new MutableConfiguration<String, String>();
		config.setStoreByValue(false)
				.setTypes(String.class, String.class)
				.setExpiryPolicyFactory(
						AccessedExpiryPolicy.factoryOf(Duration.THIRTY_MINUTES))
				.setStatisticsEnabled(true);

		return config;
	}

}
