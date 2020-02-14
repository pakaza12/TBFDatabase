package com.tbf;

/**
 * This is a subclass of Accounts that creates a Stocks Account
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class Stocks extends Accounts {

	private String label;
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double betaMeasure;
	private String stockSymbol;
	private double sharePrice;
	
	/**
	 * This constructs a Stocks Account
	 * 
	 * @param code
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param betaMeasure
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public Stocks(String code, String label, double quarterlyDividend, double baseRateOfReturn, double betaMeasure,
			String stockSymbol, double sharePrice) {
		super(code);
		this.label = label;
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaMeasure = betaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
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
	
	public double getBetaMeasure() {
		return betaMeasure;
	}
	
	public void setBetaMeasure(double betaMeasure) {
		this.betaMeasure = betaMeasure;
	}
	
	public String getStockSymbol() {
		return stockSymbol;
	}
	
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	
	public double getSharePrice() {
		return sharePrice;
	}
	
	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}
	
}
