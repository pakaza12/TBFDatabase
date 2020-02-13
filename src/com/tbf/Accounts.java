package com.tbf;

public abstract class Accounts {

	protected String type;
	
	public Accounts(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
