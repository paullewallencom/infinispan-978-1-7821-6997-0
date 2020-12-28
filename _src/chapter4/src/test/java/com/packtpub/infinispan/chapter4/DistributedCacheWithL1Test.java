package com.packtpub.infinispan.chapter4;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Test;
import static org.junit.Assert.*;

import com.packtpub.infinispan.chapter4.configuration.ClusteringConfiguration;

public class DistributedCacheWithL1Test {
  
  @Test
  public void cacheDistWithL1test() {
    ClusteringConfiguration conf = new ClusteringConfiguration();
    EmbeddedCacheManager manager = conf.getCacheManagerWithL1();
    Cache<String, String> cacheWithL1 = manager.getCache("cacheDistWithL1");
    cacheWithL1.put("K01", "V01");
    assertEquals("V01", cacheWithL1.get("K01"));
  }
}
