package com.tbf;

import java.util.List;

public class Investments extends Accounts {

protected List<PrivateInvestments> investments;
	
	public Investments(String type) {
		super(type);
	}

	public List<PrivateInvestments> getStocks() {
		return investments;
	}

	public void setInvestments(List<PrivateInvestments> investments) {
		this.investments = investments;
	}
	
	public void addStock(PrivateInvestments a) {
		this.investments.add(a);
	}
	
}
