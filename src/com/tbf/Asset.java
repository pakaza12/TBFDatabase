package com.tbf;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This abstract class represents an account
 * with the subclasses: Deposit, PrivateInvestments, and Stocks
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
@XStreamAlias("asset")
public abstract class Asset {

	protected String code;
	protected String label;
	
	public Asset(String code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
