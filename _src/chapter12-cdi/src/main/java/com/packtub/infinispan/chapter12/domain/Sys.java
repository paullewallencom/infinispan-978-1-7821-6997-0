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
@JsonPropertyOrder({ "message", "country", "sunrise", "sunset" })
public class Sys implements Serializable {

	private static final long serialVersionUID = 928225384083022372L;

	@JsonProperty("message")
	private Double message;
	@JsonProperty("country")
	private String country;
	@JsonProperty("sunrise")
	private Integer sunrise;
	@JsonProperty("sunset")
	private Integer sunset;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("message")
	public Double getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(Double message) {
		this.message = message;
	}

	public Sys withMessage(Double message) {
		this.message = message;
		return this;
	}

	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	public Sys withCountry(String country) {
		this.country = country;
		return this;
	}

	@JsonProperty("sunrise")
	public Integer getSunrise() {
		return sunrise;
	}

	@JsonProperty("sunrise")
	public void setSunrise(Integer sunrise) {
		this.sunrise = sunrise;
	}

	public Sys withSunrise(Integer sunrise) {
		this.sunrise = sunrise;
		return this;
	}

	@JsonProperty("sunset")
	public Integer getSunset() {
		return sunset;
	}

	@JsonProperty("sunset")
	public void setSunset(Integer sunset) {
		this.sunset = sunset;
	}

	public Sys withSunset(Integer sunset) {
		this.sunset = sunset;
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
