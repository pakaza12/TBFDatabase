package com.tbf;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a subclass of Asset that creates a PrivateInvestments Account
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
@XStreamAlias("privateInvestment")
public class PrivateInvestment extends Asset {
	
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double baseOmegaMeasure;
	private double totalValue;
	
	public PrivateInvestment(String code, String label, double quarterlyDividend, double baseRateOfReturn, double baseOmegaMeasure, double totalValue) {
		super(code, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.baseOmegaMeasure = baseOmegaMeasure;
		this.totalValue = totalValue;
	}
	
	public double getQuarterlyDividend() {
		return quarterlyDividend;
	}
	
	public void setQuarterlyDividend(double quarterlyDividend) {
		this.quarterlyDividend = quarterlyDividend;
	}
	
	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}
	
	public void setBaseRateOfReturn(double baseRateOfReturn) {
		this.baseRateOfReturn = baseRateOfReturn;
	}
	
	public double getBaseOmegaMeasure() {
		return baseOmegaMeasure;
	}
	
	public void setBaseOmegaMeasure(double baseOmegaMeasure) {
		this.baseOmegaMeasure = baseOmegaMeasure;
	}
	
	public double getTotalValue() {
		return totalValue;
	}
	
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value / 100;
	}

	@Override
	public double getTotalWorth() {
		return (this.value * this.totalValue);
	}

	@Override
	public double getAggregateRisk(double totalV) {
		return ((this.baseOmegaMeasure + Math.exp(-125500.0/this.totalValue)) * ((double) getTotalWorth()/totalV));
	}

	@Override
	public double getAnnualReturn() {
		return (this.baseRateOfReturn * getTotalWorth() + 4 * this.quarterlyDividend * this.value); //Not sure if you want to multiply by the actual total value or by the worth
	}

	@Override
	public double getRisk() {
		return (this.baseOmegaMeasure + Math.exp(-125500.0/this.totalValue));
	}
	
}
