package com.packtpub.infinispan.chapter4;

import static org.junit.Assert.assertEquals;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Before;
import org.junit.Test;

public class TopologiesTest extends BaseTest {
  
  private DefaultCacheManager cacheManager;
  private Cache<String, String> localCache;
  
  @Before
  public void setUpBeforeClass() throws Exception {
    cacheManager = new DefaultCacheManager(getSampleFile());
    localCache = cacheManager.getCache("DefaultLocalCache");
    cacheManager.start();
  }
  
  @Test
  public void testLocalCache() {
    assertEquals(localCache.size(), 0);
    localCache.put("K01", "V01");
    assertEquals(localCache.get("K01"), "V01");
  }
}
