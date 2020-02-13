package com.tbf;

public class Stocks {
	
	

	private String label;
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double betaMeasure;
	private double stockSymbol;
	private double sharePrice;
	
	public Stocks(String label, double quarterlyDividend, double baseRateOfReturn, double betaMeasure,
			double stockSymbol, double sharePrice) {
		super();
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
	
	public double getStockSymbol() {
		return stockSymbol;
	}
	
	public void setStockSymbol(double stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	
	public double getSharePrice() {
		return sharePrice;
	}
	
	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}
	
}
