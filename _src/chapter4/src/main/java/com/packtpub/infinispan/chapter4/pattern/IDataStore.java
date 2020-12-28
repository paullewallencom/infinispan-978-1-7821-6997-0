package com.packtpub.infinispan.chapter4.pattern;

/**
 * Created by wrsantos on 20/03/15.
 */
public interface IDataStore {

    String getEntry(String key);
    void setEntry(String key, String value);

}
