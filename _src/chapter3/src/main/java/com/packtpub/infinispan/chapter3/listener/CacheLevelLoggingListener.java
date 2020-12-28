package com.packtpub.infinispan.chapter3.listener;

import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntriesEvicted;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryActivated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryInvalidated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryLoaded;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryPassivated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryVisited;
import org.infinispan.notifications.cachelistener.annotation.DataRehashed;
import org.infinispan.notifications.cachelistener.annotation.TopologyChanged;
import org.infinispan.notifications.cachelistener.annotation.TransactionCompleted;
import org.infinispan.notifications.cachelistener.annotation.TransactionRegistered;
import org.infinispan.notifications.cachelistener.event.CacheEntriesEvictedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryEvent;
import org.infinispan.notifications.cachelistener.event.DataRehashedEvent;
import org.infinispan.notifications.cachelistener.event.Event;
import org.infinispan.notifications.cachelistener.event.TopologyChangedEvent;
import org.infinispan.notifications.cachelistener.event.TransactionCompletedEvent;
import org.infinispan.notifications.cachelistener.event.TransactionRegisteredEvent;
import org.infinispan.transaction.xa.GlobalTransaction;

@Listener
public class CacheLevelLoggingListener {
  private Log log = LogFactory.getLog(CacheLevelLoggingListener.class);
  private ConcurrentMap<GlobalTransaction, Queue<Event>> transactions = new ConcurrentHashMap<GlobalTransaction, Queue<Event>>();
  
  /*
   * We are logging information when a cache entry is added to the cache.
   */
  @CacheEntryCreated
  public void entryAdd(CacheEntryCreatedEvent<String, String> event) {
    if (!event.isPre()) // Message is only logged after operation succeeded
      log.infof("[Event %s] Cache entry %s added in cache %s", event.getType(), event.getKey(), event.getCache());
  }
  
  /*
   * In this method we are using annotations to be notified when a cache entry is visitor, modified,
   * loaded, invalidated or removed.
   */
  @CacheEntryVisited
  @CacheEntryModified
  @CacheEntryLoaded
  @CacheEntryInvalidated
  @CacheEntryRemoved
  public void entryManipulated(CacheEntryEvent<?, ?> event) {
    log.infof("[Event %s] Cache entry %s manipulated in cache %s", event.getType(), event.getKey(), event.getCache());
  }
  
  /*
   * We are logging information when a cache entry is evicted from the cache.
   */
  @CacheEntriesEvicted
  public void entriesEvicted(CacheEntriesEvictedEvent<?, ?> event) {
    for (Entry<?, ?> entry : event.getEntries().entrySet()) {
      log.infof("[Event CACHE_EVICTION] Cache entry %s evicted", entry.getKey());
    }
  }
  
  /*
   * We are logging information when a cache entry is evicted from the cache.
   */
  @CacheEntryPassivated
  @CacheEntryActivated
  public void entryPassivationActivation(Event<?, ?> event) {
    switch (event.getType()) {
      case CACHE_ENTRY_PASSIVATED:
        log.info("Cache Passivated.  Details = " + event);
        break;
      case CACHE_ENTRY_ACTIVATED:
        log.info("Cache Activated.  Details = " + event);
        break;
    }
  }
  
  /*
   * Method to logging information when a transaction is registered.
   */
  @TransactionRegistered
  public void transactionRegistered(TransactionRegisteredEvent<?, ?> event) {
    log.infof("[Event %s] Transaction '{%s}' registered", event.getType(), event.getGlobalTransaction().getId());
    transactions.put(event.getGlobalTransaction(), new ConcurrentLinkedQueue<Event>());
  }
  
  /*
   * Method to logging information when a transaction is completed.
   */
  @TransactionCompleted
  public void transactionCompleted(TransactionCompletedEvent<?, ?> event) {
    Queue<Event> events = transactions.get(event.getGlobalTransaction());
    transactions.remove(event.getGlobalTransaction());
    log.infof("[Event %s] Transaction '{%s}' completed", event.getType(), event.getGlobalTransaction().getId());
    if (event.isTransactionSuccessful()) {
      for (Event e : events) {
        log.info("Event " + e);
      }
    }
  }
  
  /*
   * Method to logging information when the data is rehashed.
   */
  @DataRehashed
  public void dataRehashed(DataRehashedEvent<?, ?> event) {
    log.infof(
        "[Event %s] Data rehashed. Consistent hash before the rebalance start: %s. Consistent hash at the end of the rebalance: %s",
        event.getType(), event.getConsistentHashAtStart(), event.getConsistentHashAtEnd());
  }
  
  /*
   * Method to logging information when the ConsistentHash implementation in use by the
   * DistributionManager.
   */
  @TopologyChanged
  public void topologyChanged(TopologyChangedEvent<?, ?> event) {
    log.infof("[Event %s] Cache entry Topology Changed. New Topology ID: %s", event.getType(), event.getNewTopologyId());
  }
}
