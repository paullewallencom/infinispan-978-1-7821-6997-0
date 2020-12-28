package com.packtpub.infinispan.chapter3.apis.tests;

import static org.junit.Assert.*;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Before;
import org.junit.Test;

public class EvictionAndExpirationTest extends BaseTest {
  private EmbeddedCacheManager manager;
  
  @Before
  public void setUp() throws Exception {
    manager = new DefaultCacheManager(getSampleFile());
    assertTrue(manager.getCacheNames().contains("CacheClient_Blue"));
    assertTrue(manager.getCacheNames().contains("CacheClient_Brown"));
    assertEquals(manager.getCacheNames().size(), 6);
  }
  
  @Test
  public void testEvictionAndExpirationConfiguration() throws Exception {
    Cache<String, String> cache = manager.getCache("ExpirationCache");
    System.out.println("***********************************************");
    System.out.println("Expiration Test by maximum idle time (maxIdle)");
    cache.put("key_01", "Key 01 Value");
    System.out.println("Key01 = " + cache.get("key_01"));
    Thread.sleep(3000);
    System.out.println("Key01 " + cache.get("key_01"));
    
    System.out.println("**********************************************");
    System.out.println("Expiration Test by lifespan (lifespan)");
    cache.put("key_01", "Key 01 Another Value");
    for (int i = 1; i <= 10; i++) {
      Thread.sleep(1000);
      System.out.println(i + " second(s) >> Object on key_01 = " + cache.get("key_01"));
      
      if (i < 10) {
        assertNotNull(cache.get("key_01"));
      } else {
        assertNull(cache.get("key_01"));
      }
      
    }
    System.out.println("**********************************************");
  }
  
}
