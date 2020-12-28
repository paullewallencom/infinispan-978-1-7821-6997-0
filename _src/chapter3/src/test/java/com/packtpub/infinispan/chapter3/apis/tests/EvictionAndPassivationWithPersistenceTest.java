package com.packtpub.infinispan.chapter3.apis.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EvictionAndPassivationWithPersistenceTest extends BaseTest {
  
  private EmbeddedCacheManager manager;
  private File fileCache;
  
  @Before
  public void setUp() throws Exception {
    fileCache = new File("/tmp/cache/backup/EvictionAndPassivationCache.dat");
    if (fileCache.exists()) {
      assertTrue(fileCache.delete());
    }
    manager = new DefaultCacheManager(getSampleFile());
  }
  
  @Test
  public void testPpassivationAndEvictionConfig() throws URISyntaxException {
    Cache<String, String> cache = manager.getCache("EvictionAndPassivationCache");
    for (int i = 1; i < 10; i++) {
      cache.put("key_" + i, "Key " + i + " Value");
    }
    assertTrue(fileCache.exists());
  }
  
  @After
  public void cleanUp() throws Exception {
    assertFalse(fileCache.delete());
  }
  
}
