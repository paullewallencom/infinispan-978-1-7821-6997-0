package com.packtpub.infinispan.chapter12_mapreduce;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import com.packtpub.infinispan.chapter12.mr.domain.WeatherInfo;
import org.infinispan.Cache;
import org.infinispan.commons.logging.Log;
import org.infinispan.commons.logging.LogFactory;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.distexec.mapreduce.MapReduceTask;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter12.mr.domain.DestinationTypeEnum;
import com.packtpub.infinispan.chapter12.mr.mapper.DestinationMapper;
import com.packtpub.infinispan.chapter12.mr.reducer.DestinationReducer;

public class WeatherInfoReduceTest {
  
  private static final Log logger = LogFactory.getLog(WeatherInfoReduceTest.class);
  
  private Cache<String, WeatherInfo> weatherCache;
  
  @Before
  public void setUp() throws Exception {
    Date today = new Date();
    EmbeddedCacheManager manager = new DefaultCacheManager();
    Configuration config = new ConfigurationBuilder().clustering().cacheMode(CacheMode.LOCAL).build();
    
    manager.defineConfiguration("weatherCache", config);
    weatherCache = manager.getCache("weatherCache");
    
    weatherCache.put("1", new WeatherInfo("Germany", "Berlin", today, 12d));
    weatherCache.put("2", new WeatherInfo("Germany", "Stuttgart", today, 11d));
    weatherCache.put("3", new WeatherInfo("England", "London", today, 8d));
    weatherCache.put("4", new WeatherInfo("England", "Manchester", today, 6d));
    weatherCache.put("5", new WeatherInfo("Italy", "Rome", today, 17d));
    weatherCache.put("6", new WeatherInfo("Italy", "Napoli", today, 18d));
    weatherCache.put("7", new WeatherInfo("Ireland", "Belfast", today, 9d));
    weatherCache.put("8", new WeatherInfo("Ireland", "Dublin", today, 7d));
    weatherCache.put("9", new WeatherInfo("Spain", "Madrid", today, 19d));
    weatherCache.put("10", new WeatherInfo("Spain", "Barcelona", today, 21d));
    weatherCache.put("11", new WeatherInfo("France", "Paris", today, 11d));
    weatherCache.put("12", new WeatherInfo("France", "Marseille", today, -8d));
    weatherCache.put("13", new WeatherInfo("Netherlands", "Amsterdam", today, 11d));
    weatherCache.put("14", new WeatherInfo("Portugal", "Lisbon", today, 13d));
    weatherCache.put("15", new WeatherInfo("Switzerland", "Zurich", today, -12d));
    
  }
  
  @Test
  public void execute() {
    MapReduceTask<String, WeatherInfo, DestinationTypeEnum, WeatherInfo> task = new MapReduceTask<String, WeatherInfo, DestinationTypeEnum, WeatherInfo>(
        weatherCache);
    task.mappedWith(new DestinationMapper()).reducedWith(new DestinationReducer());
    Map<DestinationTypeEnum, WeatherInfo> destination = task.execute();
    assertNotNull(destination);
    assertEquals(destination.keySet().size(), 2);
    logger.info("********** PRINTING RESULTS FOR WEATHER CACHE *************");
    for (DestinationTypeEnum destinationType : destination.keySet()) {
      logger.infof("%s - Best Place: %s \n", destinationType.getDescription(), destination.get(destinationType));
    }
    
  }
  
}
