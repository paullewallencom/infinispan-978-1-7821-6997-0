package com.packtub.infinispan.chapter12.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.infinispan.Cache;
import org.infinispan.eviction.EvictionStrategy;

import com.packtub.infinispan.chapter12.qualifiers.OpenWeatherCache;

@Named
@ApplicationScoped
public class OpenWeatherCacheInfo {
  
  private static Map<String, String> weatherMap = new HashMap<String, String>();
  
  @Inject
  @OpenWeatherCache
  private Cache<String, String> cache;
  
  public String getCacheName() {
    return cache.getName();
  }
  
  public int getNumberOfEntries() {
    return cache.size();
  }
  
  public EvictionStrategy getEvictionStrategy() {
    return cache.getCacheConfiguration().eviction().strategy();
  }
  
  public long getEvictionMaxEntries() {
    return cache.getCacheConfiguration().eviction().maxEntries();
  }
  
  public long getExpirationLifespan() {
    return cache.getCacheConfiguration().expiration().lifespan();
  }
  
  public String getCacheMode() {
    return cache.getCacheConfiguration().clustering().cacheModeString();
  }
  
  public boolean isJMXEnabled() {
    return cache.getCacheConfiguration().jmxStatistics().enabled();
  }
  
  @CachePut(cacheName = "openweather-cache")
  public void createEntry(@CacheKey String key, @CacheValue String jsonWeather) {}
  
  @CacheResult(cacheName = "openweather-cache")
  public String getWeatherFor(@CacheKey String key) {
    return weatherMap.get(key);
  }
  
  @CacheRemoveAll(cacheName = "openweather-cache")
  public void clearCache() {}
}
