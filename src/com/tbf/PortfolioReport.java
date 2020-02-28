package com.tbf;

import java.util.HashMap;
import java.util.Set;

public class PortfolioReport {
	
	public static void main(String[] args) {
	
		User[] person = dataInput.parsePersons("data/Persons.dat");
		Asset[] assets = dataInput.parseAssets("data/Assets.dat");
		Portfolio[] portfolios = dataInput.parsePortfolio("data/Portfolios.dat");
		Portfolio.summaryReport(portfolios, person, assets);
		Portfolio.detailReport(portfolios, person, assets);
		
	}

}
