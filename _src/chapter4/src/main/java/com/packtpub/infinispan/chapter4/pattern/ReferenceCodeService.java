package com.packtpub.infinispan.chapter4.pattern;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;

import com.packtpub.infinispan.chapter4.exceptions.CodeDoesNotExistsException;

public class ReferenceCodeService {
	private final Cache<String, String> cache;
    private IDataStore dataStore;

	public ReferenceCodeService() {
        GlobalConfiguration gc = new GlobalConfigurationBuilder().globalJmxStatistics().jmxDomain("com.packtpub.infinispan").allowDuplicateDomains(true).build();
		Configuration config = new ConfigurationBuilder().clustering()
				.cacheMode(CacheMode.LOCAL).build();
		this.cache = new DefaultCacheManager(gc, config).getCache();
        this.dataStore = RefCodeDataStore.getInstance();
	}

	public String get(String code) throws CodeDoesNotExistsException {
		String meaning;
		if ((meaning = cache.get(code)) != null) {
			return meaning;
		}

		if ((meaning = readRefDataFromDataStore(code)) != null) {
			cache.put(code, meaning);
		} else {
			throw new CodeDoesNotExistsException("Reference Code does not exists!");
		}
		return meaning;
	}

	public void put(String refCode, String meaning) {
		writeRefDataToDataStore(refCode, meaning);
		cache.put(refCode, meaning);
	}

	private String readRefDataFromDataStore(String refCode) {
		String meaning = dataStore.getEntry(refCode);
		return meaning;
	}

	private void writeRefDataToDataStore(String refCode, String meaning) {
		dataStore.setEntry(refCode, meaning);
	}
}
