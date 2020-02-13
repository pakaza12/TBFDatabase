package com.tbf;

import java.util.List;

public abstract class Stock extends Accounts {

	protected List<Stocks> stocks;
	
	public Stock(String type) {
		super(type);
	}

	public List<Stocks> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stocks> stocks) {
		this.stocks = stocks;
	}
	
	public void addStock(Stocks a) {
		this.stocks.add(a);
	}
	
}
