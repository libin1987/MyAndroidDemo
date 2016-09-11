package com.lib.mydemo.model;

public class MyUser {
	private int userId;

	private String account;

	private String mobile;

	private String recommendCode;

	private String userName;
	private String realName;

	private String typeVal;
	private String headImg;

	private int type;

	private int status;

	private int paystatus;

	private int state;

	private int really;
	
	private String pwd;
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return this.account;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}

	public String getRecommendCode() {
		return this.recommendCode;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setTypeVal(String typeVal) {
		this.typeVal = typeVal;
	}

	public String getTypeVal() {
		return this.typeVal;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getHeadImg() {
		return this.headImg;
	}

	public int getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(int paystatus) {
		this.paystatus = paystatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getReally() {
		return really;
	}

	public void setReally(int really) {
		this.really = really;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
}
