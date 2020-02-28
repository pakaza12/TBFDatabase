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
	/**
	 * Value represents a different value in each subclass
	 * which allows us to calculate the asset value:
	 * In Deposit, it represents the balance
	 * In PrivateInvestment it represents the percentage ownership
	 * In Stocks it represents the number of stocks owned
	 */
	protected double value;
	
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

	public abstract double getValue();

	public abstract void setValue(Double value);
	
	public abstract double getTotalWorth();
	
	public abstract double getRisk(double totalV);
	
	public abstract double getAnnualReturn();
}
