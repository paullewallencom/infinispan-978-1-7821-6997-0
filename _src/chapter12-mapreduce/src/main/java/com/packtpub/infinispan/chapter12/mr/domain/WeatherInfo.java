package com.packtpub.infinispan.chapter12.mr.domain;

import java.io.Serializable;
import java.util.Date;

public class WeatherInfo implements Serializable {
  
  private static final long serialVersionUID = -3479816816724167384L;
  
  private String country;
  private String city;
  private Date day;
  private Double temperature;
  private Double tempMax;
  private Double tempMin;
  
  public WeatherInfo(String country, String city, Date day, Double temperature) {
    this(country, city, day, temperature, temperature + 5, temperature - 5);
  }
  
  public WeatherInfo(String country, String city, Date day, Double temperature, Double tempMax, Double tempMin) {
    super();
    this.country = country;
    this.city = city;
    this.day = day;
    this.temperature = temperature;
    this.tempMax = tempMax;
    this.tempMin = tempMin;
  }
  
  public String getCountry() {
    return country;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public String getCity() {
    return city;
  }
  
  public void setCity(String city) {
    this.city = city;
  }
  
  public Date getDay() {
    return day;
  }
  
  public void setDay(Date day) {
    this.day = day;
  }
  
  public Double getTemperature() {
    return temperature;
  }
  
  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }
  
  public Double getTempMax() {
    return tempMax;
  }
  
  public void setTempMax(Double tempMax) {
    this.tempMax = tempMax;
  }
  
  public Double getTempMin() {
    return tempMin;
  }
  
  public void setTempMin(Double tempMin) {
    this.tempMin = tempMin;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((country == null) ? 0 : country.hashCode());
    result = prime * result + ((day == null) ? 0 : day.hashCode());
    result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
    result = prime * result + ((tempMin == null) ? 0 : tempMin.hashCode());
    result = prime * result + ((tempMax == null) ? 0 : tempMax.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    WeatherInfo other = (WeatherInfo) obj;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (country == null) {
      if (other.country != null)
        return false;
    } else if (!country.equals(other.country))
      return false;
    if (day == null) {
      if (other.day != null)
        return false;
    } else if (!day.equals(other.day))
      return false;
    if (temperature == null) {
      if (other.temperature != null)
        return false;
    } else if (!temperature.equals(other.temperature))
      return false;
    if (tempMin == null) {
      if (other.tempMin != null)
        return false;
    } else if (!tempMin.equals(other.tempMin))
      return false;
    if (tempMax == null) {
      if (other.tempMax != null)
        return false;
    } else if (!tempMax.equals(other.tempMax))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "{WeatherInfo:{ country:" + country + ", city:" + city + ", day:" + day + ", temperature:" + temperature
        + ", tempMax:" + tempMax + ", tempMin:" + tempMin + "}";
  }
  
}
