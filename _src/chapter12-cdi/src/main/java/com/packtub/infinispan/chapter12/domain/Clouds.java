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
@JsonPropertyOrder({ "all" })
public class Clouds implements Serializable{

	private static final long serialVersionUID = 2906988502911652680L;

	@JsonProperty("all")
	private Integer all;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("all")
	public Integer getAll() {
		return all;
	}

	@JsonProperty("all")
	public void setAll(Integer all) {
		this.all = all;
	}

	public Clouds withAll(Integer all) {
		this.all = all;
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
