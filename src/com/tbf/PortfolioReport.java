package com.tbf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PortfolioReport {
	
	public static void main(String[] args) {

		ArrayList<Portfolio> portfolios = Portfolio.loadPortfolios();
		Portfolio.summaryReport(portfolios);
		Portfolio.detailReport(portfolios);
		
	}

}
