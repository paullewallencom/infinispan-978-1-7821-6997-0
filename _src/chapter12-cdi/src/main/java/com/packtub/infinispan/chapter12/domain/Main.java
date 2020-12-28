package com.packtub.infinispan.chapter12.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "temp", "humidity", "pressure", "temp_min", "temp_max" })
public class Main implements Serializable {

	private static final long serialVersionUID = -4054729438332182233L;

	@JsonProperty("temp")
	private Double temp;
	@JsonProperty("humidity")
	private Integer humidity;
	@JsonProperty("pressure")
	private Integer pressure;
	@JsonProperty("temp_min")
	private Double temp_min;
	@JsonProperty("temp_max")
	private Double temp_max;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("temp")
	public Double getTemp() {
		return temp;
	}

	@JsonProperty("temp")
	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public Main withTemp(Double temp) {
		this.temp = temp;
		return this;
	}

	@JsonProperty("humidity")
	public Integer getHumidity() {
		return humidity;
	}

	@JsonProperty("humidity")
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public Main withHumidity(Integer humidity) {
		this.humidity = humidity;
		return this;
	}

	@JsonProperty("pressure")
	public Integer getPressure() {
		return pressure;
	}

	@JsonProperty("pressure")
	public void setPressure(Integer pressure) {
		this.pressure = pressure;
	}

	public Main withPressure(Integer pressure) {
		this.pressure = pressure;
		return this;
	}

	@JsonProperty("temp_min")
	public Double getTemp_min() {
		return temp_min;
	}

	@JsonProperty("temp_min")
	public void setTemp_min(Double temp_min) {
		this.temp_min = temp_min;
	}

	public Main withTemp_min(Double temp_min) {
		this.temp_min = temp_min;
		return this;
	}

	@JsonProperty("temp_max")
	public Double getTemp_max() {
		return temp_max;
	}

	@JsonProperty("temp_max")
	public void setTemp_max(Double temp_max) {
		this.temp_max = temp_max;
	}

	public Main withTemp_max(Double temp_max) {
		this.temp_max = temp_max;
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
