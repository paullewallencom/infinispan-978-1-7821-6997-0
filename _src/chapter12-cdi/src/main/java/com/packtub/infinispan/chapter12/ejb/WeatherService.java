package com.packtub.infinispan.chapter12.ejb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.infinispan.manager.CacheContainer;

import com.packtub.infinispan.chapter12.manager.OpenWeatherCacheInfo;
import com.packtub.infinispan.chapter12.qualifiers.JBossCache;
import com.packtub.infinispan.chapter12.qualifiers.OpenWeatherCache;
import com.packtub.infinispan.chapter12.qualifiers.WunderGroundCache;
import com.packtub.infinispan.chapter12.util.RESTClient;

@Stateless
@WebService
public class WeatherService {
  
  @Inject
  @OpenWeatherCache
  private Cache<String, String> openWeatherCache;
  
  @Inject
  @WunderGroundCache
  private Cache<String, String> wunderGroundCache;
  
  @Inject
  @JBossCache
  private CacheContainer jbossCacheManager;
  
  @Inject
  private RemoteCacheManager remoteCacheManager;
  
  @Inject
  private RemoteCache<String, String> defaultRemoteCache;
  
  @Inject
  private OpenWeatherCacheInfo openWeatherInfo;
  
  private static final Log logger = LogFactory.getLog(WeatherService.class);
  
  @WebMethod
  public String openWeatherTo(String country, String city) throws MalformedURLException, IOException {
    RemoteCache<String, String> remoteCache = remoteCacheManager.getCache();
    Cache<String, String> jbossCache = jbossCacheManager.getCache();
    logger.infof("JBoss Infinispan Cache Version>> %s ", jbossCache.getVersion());
    String weather = "";
    if (!openWeatherCache.containsKey(getKey(country, city))) {
      logger.info("Calling the Weather Online Service");
      RESTClient client = new RESTClient();
      weather = client.executeRESTOperation(String.format(getURL(), country, city));
      openWeatherCache.put(getKey(country, city), weather, 12l, TimeUnit.HOURS);
      logger.info("Replicating to Remote Cache!");
      remoteCache.put(getKey(country, city), weather);
    } else {
      logger.info("Returning Weather Information from the Cache");
      weather = openWeatherCache.get(getKey(country, city));
    }
    return weather;
  }
  
  @WebMethod
  public void jcacheOperations(String country, String city, String weather) {
    printOpenWeatherCacheInfo();
    logger.infof("*** Get weather using JCache annotations ***");
    logger.infof("Looking the weather for %s = %s", getKey(country, city),
        openWeatherInfo.getWeatherFor(getKey(country, city)));
    openWeatherInfo.createEntry(getKey(country, city), "The weather is good!!!!");
    logger.infof("Looking the weather again for %s = %s", getKey(country, city),
        openWeatherInfo.getWeatherFor(getKey(country, city)));
    openWeatherInfo.clearCache();
    logger.infof("Looking the weather after clean the cache s %s", getKey(country, city),
        openWeatherInfo.getWeatherFor(getKey(country, city)));
  }
  
  private String getKey(String country, String city) {
    return country.toUpperCase() + ":" + city;
  }
  
  private String getURL() {
    if (openWeatherCache.getName().equals("openweather-cache")) {
      return "http://api.openweathermap.org/data/2.5/weather?q=%s,%s";
    } else {
      return "http://api.wunderground.com/api/449c708390bc617d/conditions/q/%s/%s.json";
    }
  }
  
  private void printOpenWeatherCacheInfo() {
    logger.info("Printing Open Weather ...........");
    logger.infof("Name: %s", openWeatherInfo.getCacheName());
    logger.infof("Cache Mode %s ", openWeatherInfo.getCacheMode());
    logger.infof("Number of entries: %d", openWeatherInfo.getNumberOfEntries());
    logger.infof("Eviction Strategy: %s", openWeatherInfo.getEvictionStrategy());
    logger.infof("Eviction Max Entries: %d ", openWeatherInfo.getEvictionMaxEntries());
    logger.infof("Expiration Lifespan: %d", openWeatherInfo.getExpirationLifespan());
    logger.infof("Is JMX Enabled? %s ", openWeatherInfo.isJMXEnabled());
  }
}
