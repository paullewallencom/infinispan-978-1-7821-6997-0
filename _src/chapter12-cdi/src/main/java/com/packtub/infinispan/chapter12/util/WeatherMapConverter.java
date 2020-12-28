package com.packtub.infinispan.chapter12.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packtub.infinispan.chapter12.domain.WeatherMap;

public class WeatherMapConverter {
  
  public static WeatherMap convertJSONToJava(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, WeatherMap.class);
    
  }
}
