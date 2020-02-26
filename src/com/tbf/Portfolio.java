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
	
	
	
}
