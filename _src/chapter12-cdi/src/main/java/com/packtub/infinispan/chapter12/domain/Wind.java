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
@JsonPropertyOrder({ "speed", "gust", "deg" })
public class Wind implements Serializable {

	private static final long serialVersionUID = -5016732797651935566L;

	@JsonProperty("speed")
	private Double speed;
	@JsonProperty("gust")
	private Double gust;
	@JsonProperty("deg")
	private Integer deg;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("speed")
	public Double getSpeed() {
		return speed;
	}

	@JsonProperty("speed")
	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Wind withSpeed(Double speed) {
		this.speed = speed;
		return this;
	}

	@JsonProperty("gust")
	public Double getGust() {
		return gust;
	}

	@JsonProperty("gust")
	public void setGust(Double gust) {
		this.gust = gust;
	}

	public Wind withGust(Double gust) {
		this.gust = gust;
		return this;
	}

	@JsonProperty("deg")
	public Integer getDeg() {
		return deg;
	}

	@JsonProperty("deg")
	public void setDeg(Integer deg) {
		this.deg = deg;
	}

	public Wind withDeg(Integer deg) {
		this.deg = deg;
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
