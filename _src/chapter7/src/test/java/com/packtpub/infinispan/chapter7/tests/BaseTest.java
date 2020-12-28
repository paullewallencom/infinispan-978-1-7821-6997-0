package com.packtpub.infinispan.chapter7.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by santowa on 29/05/2015.
 */
public class BaseTest {
  
  public String getSampleFile() {
    String result = "";
    Properties prop = new Properties();
    String propFileName = "sample-file.properties";
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    String filename = "sample.xml";
    if (inputStream != null) {
      try {
        prop.load(inputStream);
        filename = prop.getProperty("sample.file");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return filename;
  }
}
