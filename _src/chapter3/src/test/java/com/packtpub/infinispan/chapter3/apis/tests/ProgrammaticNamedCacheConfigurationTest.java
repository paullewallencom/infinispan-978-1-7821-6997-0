package com.packtpub.infinispan.chapter3.apis.tests;

import static org.junit.Assert.*;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter3.configurations.MultiTenancyNamedCaches;

public class ProgrammaticNamedCacheConfigurationTest extends BaseTest {
  private EmbeddedCacheManager manager;
  
  @Before
  public void setUp() throws Exception {
    manager = new DefaultCacheManager(getSampleFile());
    assertTrue(manager.getCacheNames().contains("CacheClient_Blue"));
    assertTrue(manager.getCacheNames().contains("CacheClient_Brown"));
    assertEquals(manager.getCacheNames().size(), 6);
  }
  
  @Test
  public void testMultiTenancy() {
    MultiTenancyNamedCaches mtnc = new MultiTenancyNamedCaches();
    Cache<String, String> cache = mtnc.getCacheBlue();
    cache.put("k01", "v01");
    assertEquals(cache.get("k01"), "v01");
    Cache<String, String> cacheBlue = mtnc.getCacheBrown();
    cacheBlue.put("k01", "v01");
    assertEquals(cacheBlue.get("k01"), "v01");
  }
  
  @Test
  public void testProgrammaticNamedCacheConfiguration() {
    Configuration c = new ConfigurationBuilder().jmxStatistics().enable().build();
    String statisticsCacheName = "statisticsCache";
    manager.defineConfiguration(statisticsCacheName, c);
    Cache<String, String> cache = manager.getCache(statisticsCacheName);
    
    assertNotNull(cache);
  }
  
  @Test
  public void testProgrammaticClusterLoaderConfiguration() {
    Configuration c = new ConfigurationBuilder().persistence().addClusterLoader()
        .addProperty("remoteCallTimeout", "20000").build();
    String clusterLoaderCache = "clusterLoaderCache";
    manager.defineConfiguration(clusterLoaderCache, c);
    Cache<String, String> cache = manager.getCache(clusterLoaderCache);
    assertNotNull(cache);
  }
  
  @Test
  public void testProgrammaticJPACacheStoreConfiguration() {
    Cache<String, String> evictionCache = manager.getCache("EvictionCache");
    for (int i = 0; i <= 2000; i++) {
      evictionCache.put("k" + i, "v" + i);
      if (evictionCache.size() == 2000) {
        evictionCache.put("k2001", "v2001");
        assertEquals(evictionCache.size(), 2000);
        evictionCache.put("k2001", "v2001");
        assertEquals(evictionCache.size(), 2000);
      }
    }
  }
  
}
