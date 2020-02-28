package com.tbf;

import java.util.HashMap;
import java.util.Set;

/**
 * This is a class that will connect the User class and the Asset class
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class Portfolio {

	private String portfolioCode;
	private String OwnerCode;
	private String managerCode;
	private String beneficiaryCode;
	private HashMap<String, Double> assetList;
	
	public Portfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode,
			HashMap<String, Double> assetList) {
		super();
		this.portfolioCode = portfolioCode;
		this.OwnerCode = ownerCode;
		this.managerCode = managerCode;
		this.beneficiaryCode = beneficiaryCode;
		this.assetList = assetList;
	}
	
	public Portfolio(String portfolioCode, String ownerCode, String managerCode,
			HashMap<String, Double> assetList) {
		super();
		this.portfolioCode = portfolioCode;
		this.OwnerCode = ownerCode;
		this.managerCode = managerCode;
		this.assetList = assetList;
	}
	
	public String getPortfolioCode() {
		return portfolioCode;
	}
	
	public void setPortfolioCode(String portfolioCode) {
		this.portfolioCode = portfolioCode;
	}
	
	public String getOwnerCode() {
		return OwnerCode;
	}
	
	public void setOwnerCode(String ownerCode) {
		OwnerCode = ownerCode;
	}
	
	public String getManagerCode() {
		return managerCode;
	}
	
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	
	public String getBeneficiaryCode() {
		return beneficiaryCode;
	}
	
	public void setBeneficiaryCode(String beneficiaryCode) {
		this.beneficiaryCode = beneficiaryCode;
	}
	
	public HashMap<String, Double> getAssetList() {
		return assetList;
	}
	
	public void setAssetList(HashMap<String, Double> assetList) {
		this.assetList = assetList;
	}
	
	/**
	 * This method will return a map that maps an assets code
	 * to its spot in the Asset[] assets array. Used to connect
	 * the Portfolio class to the Asset class.
	 */
	public static HashMap<String, Integer> assetCodeMap(Asset[] assets) {
		HashMap<String, Integer> codeMap = new HashMap<>();
		int counter = 0;
		for(Asset a : assets) {
			//Gets the code from the asset and puts which spot it is in Asset[]
			codeMap.put(a.getCode(), counter);
			counter++;
		}
		return codeMap;
	}
	
	/**
	 * This method will return a map that maps a users code
	 * to its spot in the User[] assets array. Used to connect
	 * the Portfolio class to the User class.
	 */
	public static HashMap<String, Integer> userCodeMap(User[] person) {
		HashMap<String, Integer> codeMap = new HashMap<>();
		int counter = 0;
		for(User a : person) {
			//Gets the code from the person and puts which spot it is in User[]
			codeMap.put(a.getPersonCode(), counter);
			counter++;
		}
		return codeMap;
	}
	
	/**
	 * This method is used as a summation tool
	 * for the total value of each asset in the portfolio
	 */
	public static double getTotalValue(HashMap<String, Double> assetList, Asset[] assets, HashMap<String, Integer> portfolioToAsset) {
		double value = 0;

		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			assets[place].setValue(c.getValue());
			value += assets[place].getTotalWorth();
		}
		return value;
	}
	
	public static double getAggregateRisk(HashMap<String, Double> assetList, Asset[] assets, HashMap<String, Integer> portfolioToAsset, double totalValue) {
		double risk = 0;
		
		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			risk += assets[place].getAggregateRisk(totalValue);
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
	

	public static void summaryReport(Portfolio[] report, User[] person, Asset[] assets) {
		double value = 0, anReturn = 0, fees = 0, commissions = 0;
		
		HashMap<String, Integer> portfolioToAsset = assetCodeMap(assets);
		HashMap<String, Integer> portfolioToUser = userCodeMap(person);
		System.out.println("Portfolio Code \t\tPortfolio Owner \t\tPortfolio Manager \t\tTotal Fees \t\tTotal Commissions \t\tAggregate Risk \t\tAnnual Returns \t\tTotal Value");
		System.out.println("===========================================================================================================================================================================================================");
		for (Portfolio s : report) {
			double totalValue = getTotalValue(s.getAssetList(), assets, portfolioToAsset);
			value += totalValue;
			double aggregateRisk = getAggregateRisk(s.getAssetList(), assets, portfolioToAsset, totalValue);
			double annualReturn = getAnnualReturn(s.getAssetList(), assets, portfolioToAsset);
			anReturn += annualReturn;
			double totalFees = getTotalFee(s.getAssetList(), person, portfolioToUser, s.getManagerCode());
			fees += totalFees;
			double totalCommissions = getTotalCommission(person, portfolioToUser, s.getManagerCode(), annualReturn);
			commissions += totalCommissions;
			
			//Prints out one portfolio per loop
			String userName = person[portfolioToUser.get(s.getOwnerCode())].getLastName() + ", " + person[portfolioToUser.get(s.getOwnerCode())].getFirstName();
			String managerName = person[portfolioToUser.get(s.getManagerCode())].getLastName() + ", " + person[portfolioToUser.get(s.getManagerCode())].getFirstName();
			
			System.out.printf("%-23s %-31s %-31s $%-22.2f $%-30.2f %-23.4f $%-22.2f $%-32.2f\n", s.getPortfolioCode(), userName, managerName, totalFees, totalCommissions, aggregateRisk, annualReturn, totalValue);
		}
		System.out.println("===========================================================================================================================================================================================================");
		System.out.printf("\t\t\t\t\t\t\t\t\t      Results = $%-22.2f $%-54.2f $%-22.2f $%-32.2f\n", fees, commissions, anReturn, value);
	}
	
	public static void detailReport(Portfolio[] report, User[] person, Asset[] assets) {
		HashMap<String, Integer> portfolioToAsset = assetCodeMap(assets);
		HashMap<String, Integer> portfolioToUser = userCodeMap(person);
		
		System.out.printf("\n\nPortfolio Details\n============================================================================================================================================================================\n");
		for(Portfolio s : report) {
			double totalValue = getTotalValue(s.getAssetList(), assets, portfolioToAsset);
			double aggregateRisk = getAggregateRisk(s.getAssetList(), assets, portfolioToAsset, totalValue);
			double annualReturn = getAnnualReturn(s.getAssetList(), assets, portfolioToAsset);
			double totalFees = getTotalFee(s.getAssetList(), person, portfolioToUser, s.getManagerCode());
			double totalCommissions = getTotalCommission(person, portfolioToUser, s.getManagerCode(), annualReturn);
			String managerName = person[portfolioToUser.get(s.getManagerCode())].getLastName() + ", " + person[portfolioToUser.get(s.getManagerCode())].getFirstName();
			
			System.out.printf("Portfolio %s\n-----------------------------------------------------------------------------------\n", s.getPortfolioCode());
			if(!(s.getBeneficiaryCode() == null) && !(s.getManagerCode() == null)) {
				
				System.out.printf("Owner:\n%s \nManager:\n%s \nBeneficiary:\n%s \nAssets:\n", person[portfolioToUser.get(s.getOwnerCode())].toString(), managerName, person[portfolioToUser.get(s.getBeneficiaryCode())].toString());
				System.out.printf("Code \t\tAsset \t\t\t\t\tReturn Rate \t\tRisk \t\tAnnual Return \t\tValue\n");
				
				for(HashMap.Entry<String, Double> c : s.assetList.entrySet()) {
					int place = portfolioToAsset.get(c.getKey());
					
					String label = assets[place].getLabel();
					double anReturn = assets[place].getAnnualReturn();
					double anReturnRate = (anReturn/assets[place].getTotalWorth()) * 100.0;
					double risk = assets[place].getRisk();
					double value = assets[place].getTotalWorth();
					
					System.out.printf("%-15s %-31s %18.2f%% %16.2f \t\t$%-22.2f $%-20.2f\n", c.getKey(), label, anReturnRate, risk, anReturn, value);
				}
			}
		}
	}
	
}
