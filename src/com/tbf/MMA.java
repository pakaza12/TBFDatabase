package com.tbf;

public class MMA extends Accounts{

	private int balance;
	private int apr;
	
	public MMA(String type, int balance, int apr) {
		super(type);
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
	
}
