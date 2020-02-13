package com.tbf;

public class Deposit extends Accounts {

	private String label;
	private int balance;
	private int apr;
	
	public Deposit(String type, String label, int balance, int apr) {
		super(type);
		this.label = label;
		this.balance = balance;
		this.apr = apr;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
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
