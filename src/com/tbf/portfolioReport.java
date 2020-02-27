package com.tbf;

import java.util.HashMap;
import java.util.Set;

public class portfolioReport {
	
	
	public static double value(HashMap<String, Double> a, Asset[] i) {
	double value = 0;
	
	for(HashMap.Entry<String, Double> c : a.entrySet()) {
		for(int j = 0; j < i.length; j++) {
			if(c.getKey().contains(i[j].getCode())) {
				if(i[j]instanceof Deposit) {
					value += c.getValue();
				} else if(i[j]instanceof Stocks) {
					value += (((Stocks)i[j]).getSharePrice() * c.getValue());
				} else if(i[j]instanceof PrivateInvestment) {
					value += (((PrivateInvestment)i[j]).getTotalValue() * c.getValue()/100);
				}
			}
		}
	}
	
		
	return value;
	}
	
	
	public static double risk(HashMap<String, Double> a, Asset[] i, double value) {
		double risk = 0;
		//ω=b+e(−125,500/v)= 0.32 +e(−125,500/1212500.00)= 1.22167
		for(HashMap.Entry<String, Double> c : a.entrySet()) {
			for(int j = 0; j < i.length; j++) {
				if(i[j] instanceof PrivateInvestment) {
					double constant = (-125500)/((PrivateInvestment)c).getTotalValue();
					double omega = ((PrivateInvestment)c).getBaseOmegaMeasure() + Math.exp(constant);
					risk += omega*(c.getValue()/value);
				}else if(i[j] instanceof Stocks) {
					double beta = ((Stocks)c).getBaseRateOfReturn() * ((Stocks)c).getSharePrice() * c.getValue() + 4 * ((Stocks)c).getQuarterlyDividend() *c.getValue();
					risk += beta*(c.getValue()/value);
				}
		}
		}

				
		
		
		return risk;
	}
	
	
	
	
	public static void summary (Portfolio[] report, User[] p, Asset[] a) {
		for(Portfolio s: report) {
			
		System.out.println();
		
	}
	}
}
