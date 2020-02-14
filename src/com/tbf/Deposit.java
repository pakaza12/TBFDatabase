package com.tbf;

/**
 * This class is a subclass of Accounts, it represents a Deposit Account
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class Deposit extends Accounts {

	private String label;
	private double apr;
	
	/**
	 * Constructs a deposit account
	 * 
	 * @param code
	 * @param label
	 * @param apr
	 */
	public Deposit(String code, String label, double apr) {
		super(code);
		this.label = label;
		this.apr = apr;
	}

	public double getApr() {
		return apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
