package com.tbf;

public class Deposit extends Accounts {

	private String label;
	private int apr;
	
	public Deposit(String code, String type, String label, int apr) {
		super(code, type);
		this.label = label;
		this.apr = apr;
	}

	public int getApr() {
		return apr;
	}

	public void setApr(int apr) {
		this.apr = apr;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
