package com.packtpub.infinispan.chapter3.apis.tests;

import static org.junit.Assert.assertEquals;

import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Test;

public class ProgrammaticConfigurationTest {
  
  @Test
  public void testManualGlobalConfiguration() {
    GlobalConfiguration globalConfig = new GlobalConfigurationBuilder().globalJmxStatistics().disable().build();
    Configuration config = new ConfigurationBuilder().eviction().maxEntries(20000l).strategy(EvictionStrategy.LIRS)
        .expiration().wakeUpInterval(5000L).maxIdle(120000L).jmxStatistics().disable().build();
    
    CacheContainer container = new DefaultCacheManager(globalConfig, config);
    assertEquals(container.getCache().getCacheConfiguration().eviction().maxEntries(), 20000);
    assertEquals(container.getCache().getCacheConfiguration().eviction().strategy(), EvictionStrategy.LIRS);
    assertEquals(container.getCache().getCacheConfiguration().expiration().wakeUpInterval(), 5000l);
    assertEquals(container.getCache().getCacheConfiguration().expiration().maxIdle(), 120000l);
    
    container.stop();
    
  }
  
}
