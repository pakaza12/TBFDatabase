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
	
	
	
	
	public static void summary (Portfolio[] report, User[] p, Asset[] a) {
		for(Portfolio s: report) {
			
		System.out.println();
		
	}
	}
}
