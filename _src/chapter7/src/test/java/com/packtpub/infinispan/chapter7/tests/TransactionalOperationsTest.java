package com.packtpub.infinispan.chapter7.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.VersioningScheme;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter7.domain.Guest;

public class TransactionalOperationsTest extends BaseTest {
  
  private static final Log logger = LogFactory.getLog(TransactionalOperationsTest.class);
  private EmbeddedCacheManager manager;
  private TransactionManager tm;
  private Guest guest = new Guest();
  
  @Before
  public void setUp() throws Exception {
    logger.info("Executing setUp() ... ");
    manager = new DefaultCacheManager(getSampleFile());
    assertTrue(manager.getCacheNames().contains("batchingCacheWithEvictionAndPassivation"));
    assertTrue(manager.getCacheNames().contains("transactionalPessimistic"));
    assertEquals(manager.getCacheNames().size(), 9);
    guest = new Guest();
    guest.setID(1234);
    guest.setFirstName("Wagner");
    guest.setLastName("Santos");
    guest.setDocumentNumber(2261166123l);
    Calendar birthDate = new GregorianCalendar();
    birthDate.set(1980, Calendar.FEBRUARY, 4);
    guest.setBirthDate(birthDate.getTime());
    
  }
  
  @After
  public void after() {
    logger.info("Stopping after() ... ");
    manager.stop();
  }
  
  @Test
  public void simpleTransactionTest() throws Exception, SystemException {
    Cache<String, Guest> cache = manager.getCache("transactionCache");
    tm = cache.getAdvancedCache().getTransactionManager();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    
  }
  
  @Test
  public void transactionWithLockingTest() throws Exception, SystemException {
    Cache<String, Guest> cache = manager.getCache("transactionCacheWithLocking");
    tm = cache.getAdvancedCache().getTransactionManager();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    guest.setLastName("Roberto dos Santos");
    cache.getAdvancedCache().lock("wds.212");
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    
  }
  
  @Test
  public void transactionWithLockingAndDeadlockDetectionTest() throws Exception, SystemException {
    Cache<String, Guest> cache = manager.getCache("transactionCacheWithLockingAndDeadlockDetection");
    tm = cache.getAdvancedCache().getTransactionManager();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    guest.setLastName("Roberto dos Santos");
    cache.getAdvancedCache().lock("wds.212");
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    
  }
  
  @Test
  public void transactionWithLockingAndWriteSkewCheckAndVersioningTest() throws Exception, SystemException {
    Cache<String, Guest> cache = manager.getCache("transactionCacheWithLockingWriteSkewCheckAndVersioning");
    tm = cache.getAdvancedCache().getTransactionManager();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    guest.setLastName("Roberto dos Santos");
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    
  }
  
  @Test
  public void CacheExample01Test() throws Exception, SystemException {
    Cache<String, Guest> cache = manager.getCache("transactionCacheExample01");
    tm = cache.getAdvancedCache().getTransactionManager();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    guest.setLastName("Roberto dos Santos");
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
  }
  
  @Test
  public void transactionProgrammaticAPI() {
    GlobalConfiguration gc = new GlobalConfigurationBuilder().globalJmxStatistics().jmxDomain("Programmatic API Test")
        .enable().build();
    Configuration config = new ConfigurationBuilder().transaction().transactionMode(TransactionMode.TRANSACTIONAL)
        .transactionManagerLookup(new GenericTransactionManagerLookup()).syncRollbackPhase(false).syncCommitPhase(true)
        .cacheStopTimeout(30000l).use1PcForAutoCommitTransactions(false).autoCommit(true).useSynchronization(false)
        .build();
    
    CacheContainer container = new DefaultCacheManager(gc, config);
    
  }
  
  @Test
  public void CacheExampleRecoveryTest() throws Exception, SystemException {
    Cache<String, Guest> cache = manager.getCache("recoveryCache");
    tm = cache.getAdvancedCache().getTransactionManager();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    guest.setLastName("Roberto dos Santos");
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
  }
  
  @Test
  public void CacheExampleVersioningTest() throws Exception, SystemException {
    Configuration config = new ConfigurationBuilder().transaction().transactionMode(TransactionMode.TRANSACTIONAL)
        .transactionManagerLookup(new GenericTransactionManagerLookup()).versioning().enable()
        .scheme(VersioningScheme.SIMPLE).build();
    
    CacheContainer container = new DefaultCacheManager(config);
    Cache<String, Guest> cache = manager.getCache("recoveryCache");
    tm = cache.getAdvancedCache().getTransactionManager();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
    assertEquals(Status.STATUS_NO_TRANSACTION, tm.getStatus());
    tm.begin();
    guest.setLastName("Roberto dos Santos");
    cache.put("wds.212", guest);
    assertEquals(Status.STATUS_ACTIVE, tm.getStatus());
    tm.commit();
  }
}
