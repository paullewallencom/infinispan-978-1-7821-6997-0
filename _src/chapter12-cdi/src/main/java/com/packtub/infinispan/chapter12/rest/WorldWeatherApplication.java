package com.packtub.infinispan.chapter12.rest;

import javax.ws.rs.core.Application;

import com.packtub.infinispan.chapter12.ejb.WeatherService;

import java.util.HashSet;
import java.util.Set;

public class WorldWeatherApplication extends Application {
  private Set<Object> singletons = new HashSet<Object>();
  
  public WorldWeatherApplication() {
    singletons.add(new WeatherService());
  }
  
  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }
}
