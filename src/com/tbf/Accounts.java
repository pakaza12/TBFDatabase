package com.tbf;

/**
 * This abstract class represents an account
 * with the subclasses: Deposit, PrivateInvestments, and Stocks
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
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
