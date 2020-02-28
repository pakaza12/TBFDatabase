package com.tbf;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class is a subclass of Asset, it represents a Deposit Account
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
@XStreamAlias("deposit")
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
	
	@Override
	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public double getTotalWorth() {
		return this.value;
	}

	@Override
	public double getRisk(double totalV) {
		return 0.0;
	}

	@Override
	public double getAnnualReturn() {
		double apy = Math.exp(this.apr) - 1;
		return (apy * this.value);
	}

}
