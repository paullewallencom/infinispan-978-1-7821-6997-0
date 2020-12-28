package com.packtpub.infinispan.chapter12.mr.domain;

public enum DestinationTypeEnum {
  SUN(18d, "Sun Destination"), SKIING(-5d, "Skiing Destination");
  
  private Double temperature;
  private String description;
  
  DestinationTypeEnum(Double temperature, String description) {
    this.temperature = temperature;
    this.description = description;
  }
  
  public Double getTemperature() {
    return temperature;
  }
  
  public String getDescription() {
    return description;
  }
  
}
