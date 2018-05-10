package com.sas.entity;

public class LocationEntity {
	
	private String checkId;
	private Double latitude;
	private Double longitude;
	
	public LocationEntity() {
		super();
	}

	public LocationEntity(String checkId, Double latitude, Double longitude) {
		super();
		this.checkId = checkId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	
	
}
