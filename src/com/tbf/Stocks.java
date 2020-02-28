package com.tbf;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a subclass of Asset that creates a Stocks Account
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
@XStreamAlias("stock")
public class Stocks extends Asset {

	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double betaMeasure;
	private String stockSymbol;
	private double sharePrice;
	
	public Stocks(String code, String label, double quarterlyDividend, double baseRateOfReturn, double betaMeasure,
			String stockSymbol, double sharePrice) {
		super(code, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaMeasure = betaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
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

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public double getTotalWorth() {
		return (this.value * this.sharePrice);
	}

	@Override
	public double getRisk(double totalValue) {
		return (getAnnualReturn()/(this.value * this.sharePrice));
	}
	
	@Override
	public double getAnnualReturn() {
		return (this.baseRateOfReturn * this.sharePrice * this.value + 4 * this.quarterlyDividend * this.value);
	}
	
}
