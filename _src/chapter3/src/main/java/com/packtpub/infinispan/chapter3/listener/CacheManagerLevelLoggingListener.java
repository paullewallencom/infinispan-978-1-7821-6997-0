package com.packtpub.infinispan.chapter3.listener;

import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachemanagerlistener.event.Event;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStarted;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStopped;
import org.infinispan.notifications.cachemanagerlistener.annotation.Merged;
import org.infinispan.notifications.cachemanagerlistener.annotation.ViewChanged;
import org.infinispan.notifications.cachemanagerlistener.event.MergeEvent;
import org.infinispan.notifications.cachemanagerlistener.event.ViewChangedEvent;

@Listener
public class CacheManagerLevelLoggingListener {
  private Log log = LogFactory.getLog(CacheLevelLoggingListener.class);
  
  @CacheStarted
  @CacheStopped
  public void notificationforStartOrStop(Event event) {
    switch (event.getType()) {
      case CACHE_STARTED:
        log.infof("[Event %s] Cache started.", event.getType());
        break;
      case CACHE_STOPPED:
        log.infof("[Event %s] Cache stopped.", event.getType());
        break;
      default:
        log.infof("[Event %s] Unknown Event.", event.getType());
        break;
    }
  }
  
  @ViewChanged
  public void viewChanged(ViewChangedEvent event) {
    log.infof(
        "[Event %s] View change event. isMergeView? %s. Local Address: &s. JGroups View ID: %s. Old Members: %s, New Members: %s",
        event.getType(), event.isMergeView(), event.getLocalAddress(), event.getOldMembers(), event.getNewMembers());
  }
  
  @Merged
  public void merged(MergeEvent event) {
    log.infof(
        "[Event %s] Merge event. isMergeView? %s. Local Address: &s. Subgroups Merged: %s. JGroups View ID: %s. Old Members: %s, New Members: %s",
        event.getType(), event.isMergeView(), event.getLocalAddress(), event.getSubgroupsMerged(),
        event.getOldMembers(), event.getNewMembers());
  }
}
