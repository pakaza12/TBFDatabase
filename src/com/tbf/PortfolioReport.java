package com.tbf;

import java.util.HashMap;
import java.util.Set;

public class PortfolioReport {
	
	public static void main(String[] args) {
	
		//User[] person = dataInput.parsePersons("data/Persons.dat");
		User[] person = User.loadUsers();
		//Asset[] assets = dataInput.parseAssets("data/Assets.dat");
		Asset[] assets = Asset.loadAssets();
		//Portfolio[] portfolios = dataInput.parsePortfolio("data/Portfolios.dat");
		Portfolio[] portfolios = Portfolio.loadPortfolios();
		Portfolio.summaryReport(portfolios, person, assets);
		Portfolio.detailReport(portfolios, person, assets);
		
	}

}
