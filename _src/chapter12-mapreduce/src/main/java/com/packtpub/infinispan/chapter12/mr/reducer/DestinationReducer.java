package com.packtpub.infinispan.chapter12.mr.reducer;

import static com.packtpub.infinispan.chapter12.mr.domain.DestinationTypeEnum.SUN;

import java.util.Iterator;

import com.packtpub.infinispan.chapter12.mr.domain.WeatherInfo;
import org.infinispan.distexec.mapreduce.Reducer;

import com.packtpub.infinispan.chapter12.mr.domain.DestinationTypeEnum;

public class DestinationReducer implements Reducer<DestinationTypeEnum, WeatherInfo> {
  
  private static final long serialVersionUID = 7711240429951976280L;
  
  public WeatherInfo reduce(DestinationTypeEnum key, Iterator<WeatherInfo> it) {
    WeatherInfo bestPlace = null;
    if (key.equals(SUN)) {
      while (it.hasNext()) {
        WeatherInfo w = it.next();
        if (bestPlace == null || w.getTemperature() > bestPlace.getTemperature()) {
          bestPlace = w;
        }
      }
    } else { // / Best for skiing
      while (it.hasNext()) {
        WeatherInfo w = it.next();
        if (bestPlace == null || w.getTemperature() < bestPlace.getTemperature()) {
          bestPlace = w;
        }
      }
    }
    
    return bestPlace;
  }
  
}
