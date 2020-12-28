package com.packtpub.infinispan.chapter4.configuration;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;


public class ClusteringConfiguration {
	public EmbeddedCacheManager getLocalCacheManager() {
		GlobalConfiguration glob = new GlobalConfigurationBuilder()
				.nonClusteredDefault().build(); // Builds the
												// GlobalConfiguration object

		Configuration loc = new ConfigurationBuilder().clustering()
				.cacheMode(CacheMode.LOCAL).eviction().maxEntries(400)
				.strategy(EvictionStrategy.LIRS).build();

		return new DefaultCacheManager(glob, loc, true);
	}
	
	public EmbeddedCacheManager getCacheManagerWithVirtualNodes() {
	    Configuration configuration = new ConfigurationBuilder()
	                  .clustering().cacheMode(CacheMode.DIST_SYNC)
	                  .hash().numOwners(2).numSegments(10)
	                  .build();
	    return new DefaultCacheManager(configuration);
	}
	
	
    public EmbeddedCacheManager getCacheManagerWithL1(){
		GlobalConfiguration glob = new GlobalConfigurationBuilder()
		                           .transport().
		                             defaultTransport()
		                            .build();

		Configuration configuration = new ConfigurationBuilder() 
        .clustering()
        .cacheMode(CacheMode.DIST_ASYNC)
        .l1().enable().lifespan(1000000)
        .hash().numOwners(2)
        .build();
    	
	    return new DefaultCacheManager(glob, configuration);
    	
    }

}
