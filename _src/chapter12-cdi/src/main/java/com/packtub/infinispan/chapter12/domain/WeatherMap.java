package com.packtub.infinispan.chapter12.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"coord",
"sys",
"weather",
"base",
"main",
"wind",
"clouds",
"dt",
"id",
"name",
"cod"
})
public class WeatherMap implements Serializable{


	private static final long serialVersionUID = 6361425923529657627L;

	@JsonProperty("coord")
private Coord coord;
@JsonProperty("sys")
private Sys sys;
@JsonProperty("weather")
private List<Weather> weather = new ArrayList<Weather>();
@JsonProperty("base")
private String base;
@JsonProperty("main")
private Main main;
@JsonProperty("wind")
private Wind wind;
@JsonProperty("clouds")
private Clouds clouds;
@JsonProperty("dt")
private Integer dt;
@JsonProperty("id")
private Integer id;
@JsonProperty("name")
private String name;
@JsonProperty("cod")
private Integer cod;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("coord")
public Coord getCoord() {
return coord;
}

@JsonProperty("coord")
public void setCoord(Coord coord) {
this.coord = coord;
}

public WeatherMap withCoord(Coord coord) {
this.coord = coord;
return this;
}

@JsonProperty("sys")
public Sys getSys() {
return sys;
}

@JsonProperty("sys")
public void setSys(Sys sys) {
this.sys = sys;
}

public WeatherMap withSys(Sys sys) {
this.sys = sys;
return this;
}

@JsonProperty("weather")
public List<Weather> getWeather() {
return weather;
}

@JsonProperty("weather")
public void setWeather(List<Weather> weather) {
this.weather = weather;
}

public WeatherMap withWeather(List<Weather> weather) {
this.weather = weather;
return this;
}

@JsonProperty("base")
public String getBase() {
return base;
}

@JsonProperty("base")
public void setBase(String base) {
this.base = base;
}

public WeatherMap withBase(String base) {
this.base = base;
return this;
}

@JsonProperty("main")
public Main getMain() {
return main;
}

@JsonProperty("main")
public void setMain(Main main) {
this.main = main;
}

public WeatherMap withMain(Main main) {
this.main = main;
return this;
}

@JsonProperty("wind")
public Wind getWind() {
return wind;
}

@JsonProperty("wind")
public void setWind(Wind wind) {
this.wind = wind;
}

public WeatherMap withWind(Wind wind) {
this.wind = wind;
return this;
}

@JsonProperty("clouds")
public Clouds getClouds() {
return clouds;
}

@JsonProperty("clouds")
public void setClouds(Clouds clouds) {
this.clouds = clouds;
}

public WeatherMap withClouds(Clouds clouds) {
this.clouds = clouds;
return this;
}

@JsonProperty("dt")
public Integer getDt() {
return dt;
}

@JsonProperty("dt")
public void setDt(Integer dt) {
this.dt = dt;
}

public WeatherMap withDt(Integer dt) {
this.dt = dt;
return this;
}

@JsonProperty("id")
public Integer getId() {
return id;
}

@JsonProperty("id")
public void setId(Integer id) {
this.id = id;
}

public WeatherMap withId(Integer id) {
this.id = id;
return this;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

public WeatherMap withName(String name) {
this.name = name;
return this;
}

@JsonProperty("cod")
public Integer getCod() {
return cod;
}

@JsonProperty("cod")
public void setCod(Integer cod) {
this.cod = cod;
}

public WeatherMap withCod(Integer cod) {
this.cod = cod;
return this;
}

@Override
public int hashCode() {
return HashCodeBuilder.reflectionHashCode(this);
}

@Override
public boolean equals(Object other) {
return EqualsBuilder.reflectionEquals(this, other);
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}
}
