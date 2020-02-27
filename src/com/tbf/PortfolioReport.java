package com.tbf;

import java.util.HashMap;
import java.util.Set;

public class PortfolioReport {
	
	/**
	 * This method will return a map that maps an assets code
	 * to its spot in the Asset[] assets array
	 */
	public static HashMap<String, Integer> assetCodeMap(Asset[] assets) {
		HashMap<String, Integer> codeMap = new HashMap<>();
		int counter = 0;
		for(Asset a : assets) {
			codeMap.put(a.getCode(), counter);
			counter++;
		}
		return codeMap;
	}
	
	public static double getTotalValue(HashMap<String, Double> assetList, Asset[] assets, HashMap<String, Integer> portfolioToAsset) {
		double value = 0;

		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			assets[place].setValue(c.getValue());
			value += assets[place].getTotalWorth();
		}
		return value;
	}
	
	public static double getAggregateRisk(HashMap<String, Double> assetList, Asset[] assets, double totalValue, HashMap<String, Integer> portfolioToAsset) {
		double risk = 0;
		
		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			risk += assets[place].getRisk(totalValue);
		}
		return risk;
	}
	
	public static double getAnnualReturns(HashMap<String, Double> assetList, Asset[] assets, HashMap<String, Integer> portfolioToAsset) {
		double annualReturn = 0;
		
		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			annualReturn += assets[place].getAnnualReturn();
		}
		return annualReturn;
	}
	

	public static void summary(Portfolio[] report, User[] p, Asset[] a) {
		HashMap<String, Integer> portfolioToAsset = assetCodeMap(a);
		for (Portfolio s : report) {
			double totalValue = getTotalValue(s.getAssetList(), a);
			double aggregateRisk = getAggregateRisk(s.getAssetList(), a, totalValue);
			
			//Prints out one line at a time/one portfolio per loop
			System.out.println();
		}
	}
}
