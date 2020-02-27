package com.tbf;

import java.util.HashMap;
import java.util.Set;

public class portfolioReport {

	public static double getTotalValue(HashMap<String, Double> a, Asset[] i) {
		double value = 0;

		for (HashMap.Entry<String, Double> c : a.entrySet()) {
			for (int j = 0; j < i.length; j++) {
				if (c.getKey().contains(i[j].getCode())) {
					if (i[j] instanceof Deposit) {
						value += c.getValue();
					} else if (i[j] instanceof Stocks) {
						value += (((Stocks) i[j]).getSharePrice() * c.getValue());
					} else if (i[j] instanceof PrivateInvestment) {
						value += (((PrivateInvestment) i[j]).getTotalValue() * c.getValue() / 100);
					}
				}
			}
		}

		return value;
	}

	public static double getAggregateRisk(HashMap<String, Double> a, Asset[] i, double value) {
		double risk = 0;
<<<<<<< HEAD
		
		for(HashMap.Entry<String, Double> c : a.entrySet()) {
			for(int j = 0; j < i.length; j++) {
				if(i[j] instanceof PrivateInvestment) {
					double constant = (-125500)/((PrivateInvestment)c).getTotalValue();
					double omega = ((PrivateInvestment)c).getBaseOmegaMeasure() + Math.exp(constant);
					risk += omega*(c.getValue()/value);
				} else if(i[j] instanceof Stocks) {
					risk += ((Stocks) i[j]).getBetaMeasure()*(c.getValue()/value);
=======

		for (HashMap.Entry<String, Double> c : a.entrySet()) {
			for (int j = 0; j < i.length; j++) {
				if (c.getKey().contains(i[j].getCode())) {
					if (i[j] instanceof PrivateInvestment) {
						double constant = (-125500) / ((PrivateInvestment) c).getTotalValue();
						double omega = ((PrivateInvestment) c).getBaseOmegaMeasure() + Math.exp(constant);
						risk += omega * (c.getValue() / value);
					} else if (i[j] instanceof Stocks) {
						double beta = ((Stocks) c).getBaseRateOfReturn() * ((Stocks) c).getSharePrice() * c.getValue()
								+ 4 * ((Stocks) c).getQuarterlyDividend() * c.getValue();
						risk += beta * (c.getValue() / value);
					}
>>>>>>> 579f9e7a48d60012cfc9dc53aa2252b96f14dd64
				}
			}
		}

		return risk;
	}
	
	public static double getAnnualReturns(HashMap<String, Double> a, Asset[] i) {
		double annualReturn = 0;
		
		for(HashMap.Entry<String, Double> c : a.entrySet()) {
			for(int j = 0; j < i.length; j++) {
				if (c.getKey().contains(i[j].getCode())) {
					
				}
			}
		}
		
		return annualReturn;
	}

	public static void summary(Portfolio[] report, User[] p, Asset[] a) {
		for (Portfolio s : report) {
			double totalValue = getTotalValue(s.getAssetList(), a);
			double aggregateRisk = getAggregateRisk(s.getAssetList(), a, totalValue);
			
			System.out.println();
		}
	}
}
