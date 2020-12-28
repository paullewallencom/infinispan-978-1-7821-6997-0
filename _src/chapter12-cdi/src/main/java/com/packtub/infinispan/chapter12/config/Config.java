package com.packtub.infinispan.chapter12.config;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ExhaustedAction;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.packtub.infinispan.chapter12.qualifiers.JBossCache;
import com.packtub.infinispan.chapter12.qualifiers.OpenWeatherCache;
import com.packtub.infinispan.chapter12.qualifiers.WunderGroundCache;

public class Config {

	@Produces
	@ApplicationScoped
	public EmbeddedCacheManager defaultEmbeddedCacheManager() {
		GlobalConfiguration gc = new GlobalConfigurationBuilder()
				.globalJmxStatistics()
				  .cacheManagerName("Infinispan-DefinitiveGuide")
				  .allowDuplicateDomains(true)
				.transport().defaultTransport()
				.build();
		return new DefaultCacheManager(gc, new ConfigurationBuilder()
				.expiration().lifespan(3l, TimeUnit.HOURS).build());
	}

	/**
	 * <p>
	 * This producer overrides the default cache configuration used by the
	 * default cache manager.
	 * </p>
	 * 
	 */
	@Produces
	@Default
	public Configuration defaultEmbeddedConfiguration() {
		return new ConfigurationBuilder().expiration()
				.lifespan(3l, TimeUnit.HOURS).build();

	}

	@ConfigureCache("openweather-cache")
	@OpenWeatherCache
	@Produces
	public Configuration openWeatherConfiguration() {
		return new ConfigurationBuilder().expiration()
				.lifespan(12l, TimeUnit.HOURS).build();
	}

	@ConfigureCache("wunderground-cache")
	@WunderGroundCache
	@Produces
	public Configuration wunderGroundConfiguration() {
		return new ConfigurationBuilder().expiration()
				.lifespan(12l, TimeUnit.HOURS).build();

	}
	
	@Produces
	@ApplicationScoped
	public RemoteCacheManager defaultRemoteCacheManager() {
		 org.infinispan.client.hotrod.configuration.ConfigurationBuilder builder = new org.infinispan.client.hotrod.configuration.ConfigurationBuilder();
	    	builder.addServer().
	    			  host("localhost").
	    			  port(11222).
	    			connectionPool().
	    			  lifo(true).
	    			  maxActive(10).
	    			  maxIdle(10).
	    			  maxTotal(20).
	    			  exhaustedAction(ExhaustedAction.CREATE_NEW).
	    			  timeBetweenEvictionRuns(120000).
	    			  minEvictableIdleTime(1800000).
	    			  minIdle(1);
			return new RemoteCacheManager(builder.build());
	    	
    }	
	
	@Produces
    @JBossCache       
    @Resource(lookup="java:jboss/infinispan/container/packt_container")
    private CacheContainer jbossCacheManager;
}
