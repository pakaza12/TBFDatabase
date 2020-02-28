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
	
	/**
	 * This method will return a map that maps a users code
	 * to its spot in the User[] assets array
	 */
	public static HashMap<String, Integer> userCodeMap(User[] person) {
		HashMap<String, Integer> codeMap = new HashMap<>();
		int counter = 0;
		for(User a : person) {
			codeMap.put(a.getPersonCode(), counter);
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
	
	public static double getAnnualReturn(HashMap<String, Double> assetList, Asset[] assets, HashMap<String, Integer> portfolioToAsset) {
		double annualReturn = 0;
		
		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			annualReturn += assets[place].getAnnualReturn();
		}
		return annualReturn;
	}
	
	public static double getTotalFee(HashMap<String, Double> assetList, User[] person, HashMap<String, Integer> portfolioToUser, String managerId) {
		if(person[portfolioToUser.get(managerId)].isJuniorBroker()) {
			return assetList.size() * 75.0;
		} else {
			return 0.0;
		}
	}
	
	public static double getTotalCommission(User[] person, HashMap<String, Integer> portfolioToUser, String managerId, double annualReturn) {
		if(person[portfolioToUser.get(managerId)].isJuniorBroker()) {
			return annualReturn * 0.0125;
		} else if (person[portfolioToUser.get(managerId)].isExpertBroker()){
			return annualReturn * 0.0375;
		} else {
			return 0.0;
		}
	}
	

	public static void summary(Portfolio[] report, User[] person, Asset[] assets) {
		HashMap<String, Integer> portfolioToAsset = assetCodeMap(assets);
		HashMap<String, Integer> portfolioToUser = userCodeMap(person);
		System.out.println("Portfolio Code \t\tPortfolio Owner \t\tPortfolio Manager \t\tTotal Fees \t\tTotal Commissions \t\tAggregate Risk \t\tAnnual Returns \t\tTotal Value");
		System.out.println("===========================================================================================================================================================================================================");
		for (Portfolio s : report) {
			double totalValue = getTotalValue(s.getAssetList(), assets, portfolioToAsset);
			double aggregateRisk = getAggregateRisk(s.getAssetList(), assets, totalValue, portfolioToAsset);
			double annualReturn = getAnnualReturn(s.getAssetList(), assets, portfolioToAsset);
			double totalFees = getTotalFee(s.getAssetList(), person, portfolioToUser, s.getManagerCode());
			double totalCommissions = getTotalCommission(person, portfolioToUser, s.getManagerCode(), annualReturn);
			
			//Prints out one line at a time/one portfolio per loop
			System.out.printf("%-23s %-31s %-31s %-23.2f %-31.2f %-23.3f %-23.2f %-32.2f\n", s.getPortfolioCode(), s.getOwnerCode(), s.getManagerCode(), totalFees, totalCommissions, aggregateRisk, annualReturn, totalValue);
		}
	}

}
