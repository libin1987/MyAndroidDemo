package com.lib.mydemo.model;

public class Region {
	private String code;

	private String provinceCode;

	private String name;

	private String level;

	private double area;

	private String station;

	private int postalCode;

	private int areaCode;

	private double longitude;

	private double dimensions;

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return this.level;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getArea() {
		return this.area;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStation() {
		return this.station;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public int getPostalCode() {
		return this.postalCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public int getAreaCode() {
		return this.areaCode;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setDimensions(double dimensions) {
		this.dimensions = dimensions;
	}

	public double getDimensions() {
		return this.dimensions;
	}
}
