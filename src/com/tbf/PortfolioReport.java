package com.tbf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PortfolioReport {
	
	public static void main(String[] args) {
		//PortfolioData.startJDBC();
		//Connection conn = PortfolioData.getConnection();
		PortfolioData.addPerson("100ABCDEF", "Parker", "Zach", "1040 Y St", "Lincoln", "NE", "68748", "US", "D", "SEC000001");
		/*
		//User[] person = dataInput.parsePersons("data/Persons.dat");
		ArrayList<User> person = User.loadUsers();
		//Asset[] assets = dataInput.parseAssets("data/Assets.dat");
		ArrayList<Asset> assets = Asset.loadAssets();
		//Portfolio[] portfolios = dataInput.parsePortfolio("data/Portfolios.dat");
		ArrayList<Portfolio> portfolios = Portfolio.loadPortfolios();
		Portfolio.summaryReport(portfolios, person, assets);
		Portfolio.detailReport(portfolios, person, assets);
		*/
	}

}
