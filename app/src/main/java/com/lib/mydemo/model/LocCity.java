package com.lib.mydemo.model;

public class LocCity {
	double lat;
	double lon;
	String city;
	int code;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String cityName) {
		this.city = cityName;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int cityCode) {
		this.code = cityCode;
	}
	public String getAddrStr() {
		return addrStr;
	}
	public void setAddrStr(String addrStr) {
		this.addrStr = addrStr;
	}
	String addrStr;
}
