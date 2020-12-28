package com.packtpub.infinispan.chapter7.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter7.domain.Guest;
import com.packtpub.infinispan.chapter7.utils.GuestListImporter;

public class BatchModeCacheTest extends BaseTest {
  
  private static final Log logger = LogFactory.getLog(BatchModeCacheTest.class);
  private CacheContainer container;
  private TransactionManager tm;
  
  @Before
  public void setUp() throws Exception {
    logger.info("Executing setUp() ... ");
    container = new DefaultCacheManager(getSampleFile());
  }
  
  @Test
  public void batchModeTest() throws Exception {
    Cache<Integer, Guest> cache = container.getCache("batchingCacheWithEvictionAndPassivation");
    List<Guest> guests = new GuestListImporter().uploadGuestFile("guest_list.csv");
    try {
      cache.startBatch();
      for (Guest guest : guests) {
        cache.put(guest.getID(), guest);
      }
      assertEquals(guests.size(), 5);
      cache.endBatch(true);
    } catch (Exception ex) {
      cache.endBatch(false);
    }
  }
}
