package com.packtpub.infinispan.chapter3.configurations;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class MultiTenancyNamedCaches {
	EmbeddedCacheManager manager = new DefaultCacheManager(new GlobalConfigurationBuilder().transport().defaultTransport().build());

	public Cache<String, String> getInvalidationCacheForClient(String name) {
		Configuration config = new ConfigurationBuilder().clustering()
				.cacheMode(CacheMode.INVALIDATION_SYNC).sync()
				.replTimeout(20000).build();
		manager.defineConfiguration(name, config);
		return manager.getCache(name);
	}

	public Cache<String, String> getReplicationCacheForClient(String name) {
		Configuration config = new ConfigurationBuilder().clustering()
				.cacheMode(CacheMode.REPL_ASYNC).async().asyncMarshalling(true)
				.build();
		manager.defineConfiguration(name, config);
		return manager.getCache(name);
	}

	public Cache<String, String> getCacheBlue() {
		Cache<String, String> blueCache = getInvalidationCacheForClient("CacheClient_Blue");
		return blueCache;
	}

	public Cache<String, String> getCacheBrown() {
		Cache<String, String> brownCache = getReplicationCacheForClient("CacheClient_Brown");
		return brownCache;
	}

}
