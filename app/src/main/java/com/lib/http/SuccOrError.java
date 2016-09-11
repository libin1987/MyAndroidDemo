package com.lib.http;

public class SuccOrError {
	int code;
	String succ;
	String msg;
	String recycleTel;
	public String getRecycleTel() {
		return recycleTel;
	}
	public void setRecycleTel(String recycleTel) {
		this.recycleTel = recycleTel;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getSucc() {
		return succ;
	}
	public void setSucc(String succ) {
		this.succ = succ;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
