package org.agmip.ws.navi.core;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaviLatLng {
	@NotEmpty
	@JsonProperty
	private final String lat;
	
	@NotEmpty
	@JsonProperty
	private final String lng;
	
	public NaviLatLng(@JsonProperty("lat") String lat, @JsonProperty("lng") String lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getLat() {
		return this.lat;
	}
	
	public String getLng() {
		return this.lng;
	}
}
