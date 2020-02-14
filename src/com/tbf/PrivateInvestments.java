package com.tbf;

/**
 * This is a subclass of Accounts that creates a PrivateInvestments Account
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class PrivateInvestments extends Accounts {
	
	private String label;
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double baseOmegaMeasure;
	private double totalValue;
	
	/**
	 * Constructs a PrivateInvestments class
	 * 
	 * @param code
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param baseOmegaMeasure
	 * @param totalValue
	 */
	public PrivateInvestments(String code, String label, double quarterlyDividend, double baseRateOfReturn, double baseOmegaMeasure, double totalValue) {
		super(code);
		this.label = label;
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.baseOmegaMeasure = baseOmegaMeasure;
		this.totalValue = totalValue;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
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
