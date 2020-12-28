package com.packtpub.infinispan.chapter12.mr.mapper;

import com.packtpub.infinispan.chapter12.mr.domain.WeatherInfo;
import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;

import com.packtpub.infinispan.chapter12.mr.domain.DestinationTypeEnum;

import static com.packtpub.infinispan.chapter12.mr.domain.DestinationTypeEnum.*;

public class DestinationMapper implements Mapper<String, WeatherInfo, DestinationTypeEnum, WeatherInfo> {
  
  private static final long serialVersionUID = -3418976303227050166L;
  
  public void map(String key, WeatherInfo weather, Collector<DestinationTypeEnum, WeatherInfo> c) {
    if (weather.getTemperature() >= SUN.getTemperature()) {
      c.emit(SUN, weather);
    } else if (weather.getTemperature() <= SKIING.getTemperature()) {
      c.emit(SKIING, weather);
    }
  }
  
}
