package com.tbf;

import java.util.List;

public class Investments extends Accounts {

protected List<PrivateInvestments> investments;
	
	public Investments(String code, String type) {
		super(code, type);
	}

	public List<PrivateInvestments> getStocks() {
		return investments;
	}

	public void setInvestments(List<PrivateInvestments> investments) {
		this.investments = investments;
	}
	
	public void addInvestments(PrivateInvestments a) {
		this.investments.add(a);
	}
	
}
