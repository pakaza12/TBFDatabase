package com.tbf;

public class Deposit extends Accounts {

	private String label;
	private double apr;
	
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
