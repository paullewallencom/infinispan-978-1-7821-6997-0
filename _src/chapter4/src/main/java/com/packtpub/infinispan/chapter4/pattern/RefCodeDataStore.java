package com.packtpub.infinispan.chapter4.pattern;

import java.io.*;
import java.util.Properties;

/**
 * Created by wrsantos on 20/03/15.
 */
public class RefCodeDataStore implements IDataStore {
    private static RefCodeDataStore dataStore = null;

    private RefCodeDataStore() {
    }

    public static RefCodeDataStore getInstance() {
        if (dataStore == null) {
            dataStore = new RefCodeDataStore();
        }
        return dataStore;
    }

    @Override
    public String getEntry(String key) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("dataStore.properties");
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void setEntry(String key, String value) {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("dataStore.properties");
            prop.setProperty(key, value);
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
