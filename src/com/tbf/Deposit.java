package com.tbf;

/**
 * This class is a subclass of Asset, it represents a Deposit Account
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class Deposit extends Asset {

	private double apr;
	
	public Deposit(String code, String label, double apr) {
		super(code, label);
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
