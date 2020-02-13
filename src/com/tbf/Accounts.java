package com.tbf;

public abstract class Accounts {

	protected String code;
	
	public Accounts(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
