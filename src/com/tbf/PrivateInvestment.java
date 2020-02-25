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
	
	
	
}
