package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	public Portfolio(String portfolioCode, String ownerCode, String managerCode, HashMap<String, Double> assetList) {
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
	 * This method will return a map that maps an assets code to its spot in the
	 * Asset[] assets array. Used to connect the Portfolio class to the Asset class.
	 */
	public static HashMap<String, Integer> assetCodeMap(ArrayList<Asset> assets) {
		HashMap<String, Integer> codeMap = new HashMap<>();
		int counter = 0;
		for (Asset a : assets) {
			// Gets the code from the asset and puts which spot it is in Asset[]
			codeMap.put(a.getCode(), counter);
			counter++;
		}
		return codeMap;
	}

	/**
	 * This method will return a map that maps a users code to its spot in the
	 * User[] assets array. Used to connect the Portfolio class to the User class.
	 */
	public static HashMap<String, Integer> userCodeMap(ArrayList<User> person) {
		HashMap<String, Integer> codeMap = new HashMap<>();
		int counter = 0;
		for (User a : person) {
			// Gets the code from the person and puts which spot it is in User[]
			codeMap.put(a.getPersonCode(), counter);
			counter++;
		}
		return codeMap;
	}

	/**
	 * This method is used as a summation tool for the total value of each asset in
	 * the portfolio
	 */
	public static double getTotalValue(HashMap<String, Double> assetList, ArrayList<Asset> assets,
			HashMap<String, Integer> portfolioToAsset) {
		double value = 0;

		// Iterates through the assets in the (code to position in array) hashmap
		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			assets.get(place).setValue(c.getValue());
			value += assets.get(place).getTotalWorth();
		}
		return value;
	}

	/**
	 * This method is used as a summation tool for the total aggregate risk of each
	 * asset in the portfolio
	 */
	public static double getAggregateRisk(HashMap<String, Double> assetList, ArrayList<Asset> assets,
			HashMap<String, Integer> portfolioToAsset, double totalValue) {
		double risk = 0;

		// Iterates through the assets in the (code to position in array) hashmap
		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			risk += assets.get(place).getAggregateRisk(totalValue);
		}
		return risk;
	}

	/**
	 * This method is used as a summation tool for the total annual return of each
	 * asset in the portfolio
	 */
	public static double getAnnualReturn(HashMap<String, Double> assetList, ArrayList<Asset> assets,
			HashMap<String, Integer> portfolioToAsset) {
		double annualReturn = 0;

		// Iterates through the assets in the (code to position in array) hashmap
		for (HashMap.Entry<String, Double> c : assetList.entrySet()) {
			int place = portfolioToAsset.get(c.getKey());
			annualReturn += assets.get(place).getAnnualReturn();
		}
		return annualReturn;
	}

	/**
	 * This method is used to get the total fees per portfolio
	 */
	public static double getTotalFee(HashMap<String, Double> assetList, ArrayList<User> person, HashMap<String, Integer> portfolioToUser, String managerId) {
		if (person.get(portfolioToUser.get(managerId)).isJuniorBroker()) {
			return assetList.size() * 75.0;
		} else {
			return 0.0;
		}
	}

	/**
	 * This method is used to get the total commissions per portfolio
	 */
	public static double getTotalCommission(ArrayList<User> person, HashMap<String, Integer> portfolioToUser, String managerId,
			double annualReturn) {
		if (person.get(portfolioToUser.get(managerId)).isJuniorBroker()) {
			return annualReturn * 0.0125;
		} else if (person.get(portfolioToUser.get(managerId)).isExpertBroker()) {
			return annualReturn * 0.0375;
		} else {
			return 0.0;
		}
	}

	public static void summaryReport(ArrayList<Portfolio> report, ArrayList<User> person, ArrayList<Asset> assets) {
		double value = 0, anReturn = 0, fees = 0, commissions = 0;

		// Creates two HashMaps to map the assetCode and userCode to their respective
		// assets/users in the User/Asset arrays
		// Ex: (key, value) would be (assetCode, spot in the array)
		HashMap<String, Integer> portfolioToAsset = assetCodeMap(assets);
		HashMap<String, Integer> portfolioToUser = userCodeMap(person);

		System.out.println(
				"Portfolio Code \t\tPortfolio Owner \t\tPortfolio Manager \t\tTotal Fees \t\tTotal Commissions \t\tAggregate Risk \t\tAnnual Returns \t\tTotal Value");
		System.out.println(
				"===========================================================================================================================================================================================================");

		// Goes through the array portfolio by portfolio to print a line by line report
		// of the values in the portfolio
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

			String userName = person.get( portfolioToUser.get(s.getOwnerCode()) ).getLastName() + ", "
					+ person.get( portfolioToUser.get(s.getOwnerCode()) ).getFirstName();
			String managerName = person.get( portfolioToUser.get(s.getManagerCode()) ).getLastName() + ", "
					+ person.get( portfolioToUser.get(s.getManagerCode()) ).getFirstName();

			// Prints out one portfolio per loop
			System.out.printf("%-23s %-31s %-31s $%-22.2f $%-30.2f %-23.4f $%-22.2f $%-32.2f\n", s.getPortfolioCode(),
					userName, managerName, totalFees, totalCommissions, aggregateRisk, annualReturn, totalValue);
		}
		System.out.println(
				"===========================================================================================================================================================================================================");
		System.out.printf("\t\t\t\t\t\t\t\t\t      Results = $%-22.2f $%-54.2f $%-22.2f $%-32.2f\n", fees, commissions,
				anReturn, value);
	}

	public static void detailReport(ArrayList<Portfolio> report, ArrayList<User> person, ArrayList<Asset> assets) {

		// Creates two HashMaps to map the assetCode and userCode to their respective
		// assets/users in the User/Asset arrays
		// Ex: (key, value) would be (assetCode, spot in the array)
		HashMap<String, Integer> portfolioToAsset = assetCodeMap(assets);
		HashMap<String, Integer> portfolioToUser = userCodeMap(person);

		System.out.printf(
				"\n\nPortfolio Details\n============================================================================================================================================================================\n");

		// Iterates through the Portfolio array and prints the detailed report for each
		for (Portfolio s : report) {
			System.out.printf(
					"Portfolio %s\n-----------------------------------------------------------------------------------\n",
					s.getPortfolioCode());

			double totalValue = getTotalValue(s.getAssetList(), assets, portfolioToAsset);
			double aggregateRisk = getAggregateRisk(s.getAssetList(), assets, portfolioToAsset, totalValue);
			double annualReturn = getAnnualReturn(s.getAssetList(), assets, portfolioToAsset);
			String managerName = person.get( portfolioToUser.get(s.getManagerCode()) ).getLastName() + ", "
					+ person.get( portfolioToUser.get(s.getManagerCode()) ).getFirstName();

			// Test case to check if there is a beneficiary
			String beneficiaryInfo = "";
			if (!(s.getBeneficiaryCode() == null) && !(portfolioToUser.get(s.getBeneficiaryCode()) == null)) {
				beneficiaryInfo += person.get( portfolioToUser.get(s.getBeneficiaryCode()) ).toString();
			} else {
				beneficiaryInfo += "None";
			}

			System.out.printf("Owner:\n%s \nManager:\n%s \nBeneficiary:\n%s \nAssets:\n",
					person.get( portfolioToUser.get(s.getOwnerCode()) ).toString(), managerName, beneficiaryInfo);
			System.out.printf("Code \t\tAsset \t\t\t\t\tReturn Rate \t\tRisk \t\tAnnual Return \t\tValue\n");

			// Iterates through each asset in the portfolio to print out the details for
			// each asset
			for (HashMap.Entry<String, Double> c : s.assetList.entrySet()) {
				// Gets the spot in the Asset array that the assetCode corresponds to (shows up
				// in)
				int place = portfolioToAsset.get(c.getKey());

				String label = assets.get(place).getLabel();
				double anReturn = assets.get(place).getAnnualReturn();
				double anReturnRate = (anReturn / assets.get(place).getTotalWorth()) * 100.0;
				double risk = assets.get(place).getRisk();
				double value = assets.get(place).getTotalWorth();

				System.out.printf("%-15s %-31s %18.2f%% %16.2f \t\t$%-22.2f $%-20.2f\n", c.getKey(), label,
						anReturnRate, risk, anReturn, value);
			}
			System.out.printf(
					"\t\t\t\t\t\t\t----------------------------------------------------------------------------------\n");
			System.out.printf("\t\t\t\t\t\t\t Totals %20.4f \t\t$%-22.2f $%-20.2f\n\n", aggregateRisk, annualReturn,
					totalValue);
		}
	}

	public static ArrayList<Portfolio> loadPortfolios() {
		ArrayList<Portfolio> b = new ArrayList<Portfolio>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "select p.portfolioId, p.portfolioCode, p.ownerCode, p.managerCode, p.beneficiaryCode from Portfolio p";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int portfolioId = rs.getInt("portfolioId");
				String portfolioCode = rs.getString("portfolioCode");
				System.out.println("PortfolioCode = " +portfolioCode);
				String ownerCode = rs.getString("ownerCode");
				String managerCode = rs.getString("managerCode");
				String beneficiaryCode = rs.getString("beneficiaryCode");
				HashMap<String, Double> assetList = loadAsset(portfolioId);

				if (beneficiaryCode != null) {
					b.add(new Portfolio(portfolioCode, ownerCode, managerCode, beneficiaryCode, assetList));
				} else {
					b.add(new Portfolio(portfolioCode, ownerCode, managerCode, assetList));
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return b;
	}

	public static HashMap<String, Double> loadAsset(int portfolioId) {
		HashMap<String, Double> assets = new HashMap<String, Double>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "select a.assetCode, ap.assetValue from AssetPortfolio ap left join Asset a on a.assetId = ap.assetId where ap.portfolioId = ?;";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, portfolioId);
			rs = ps.executeQuery();
			while (rs.next()) {
				String assetCode = rs.getString("assetCode");
				Double assetValue = rs.getDouble("assetValue");
				assets.put(assetCode, assetValue);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return assets;
	}

}
