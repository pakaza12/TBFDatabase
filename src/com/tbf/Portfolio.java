package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	//Contains the assetCode and the Asset corresponding to it
	private HashMap<String, Asset> assetList;
	private User owner;
	private User manager;
	private User beneficiary;

	public Portfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		super();
		this.portfolioCode = portfolioCode;
		this.OwnerCode = ownerCode;
		this.managerCode = managerCode;
		this.beneficiaryCode = beneficiaryCode;
	}

	public Portfolio(String portfolioCode, String ownerCode, String managerCode) {
		super();
		this.portfolioCode = portfolioCode;
		this.OwnerCode = ownerCode;
		this.managerCode = managerCode;
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

	public HashMap<String, Asset> getAssetList() {
		return assetList;
	}

	public void setAssetList(HashMap<String, Asset> assetList) {
		this.assetList = assetList;
	}
	
	public String ownerToString() {
		return this.owner.getLastName() + " " + this.owner.getFirstName();
	}

	/**
	 * This method is used as a summation tool for the total value of each asset in
	 * the portfolio
	 */
	public static double getTotalValue(HashMap<String, Asset> assetLists) {
		double value = 0;

		// Iterates through the assets in the (code to position in array) hashmap
		for (HashMap.Entry<String, Asset> c : assetLists.entrySet()) {
			value += c.getValue().getTotalWorth();
		}
		return value;
	}

	/**
	 * This method is used as a summation tool for the total aggregate risk of each
	 * asset in the portfolio
	 */
	public static double getAggregateRisk(HashMap<String, Asset> assetList, double totalValue) {
		double risk = 0;

		// Iterates through the assets in the hashmap
		for (HashMap.Entry<String, Asset> c : assetList.entrySet()) {
			risk += c.getValue().getAggregateRisk(totalValue);
		}
		return risk;
	}

	/**
	 * This method is used as a summation tool for the total annual return of each
	 * asset in the portfolio
	 */
	public static double getAnnualReturn(HashMap<String, Asset> assetList) {
		double annualReturn = 0;

		// Iterates through the assets in the (code to position in array) hashmap
		for (HashMap.Entry<String, Asset> c : assetList.entrySet()) {
			annualReturn += c.getValue().getAnnualReturn();
		}
		return annualReturn;
	}

	/**
	 * This method is used to get the total fees per portfolio
	 */
	public static double getTotalFee(Portfolio s) {
		if (s.getManager().isJuniorBroker()) {
			return s.getAssetList().size() * 75.0;
		} else {
			return 0.0;
		}
	}

	/**
	 * This method is used to get the total commissions per portfolio
	 */
	public static double getTotalCommission(Portfolio s, double annualReturn) {
		if (s.getManager().isJuniorBroker()) {
			return annualReturn * 0.0125;
		} else if (s.getManager().isExpertBroker()) {
			return annualReturn * 0.0375;
		} else {
			return 0.0;
		}
	}
	
	public int compByTypeManager(Portfolio p) {
		String lastNameA = this.manager.getLastName();
		String lastNameB = p.getManager().getLastName();
		String firstNameA = this.manager.getFirstName();
		String firstNameB = p.getManager().getFirstName();
		String brokerA = this.manager.getBrokerStatus();
		String brokerB = p.getManager().getBrokerStatus();
		
		//we want lastNameA to be less than lastNameB and everything else
		if(brokerA.compareTo(brokerB) < 0) {
			if(firstNameA.compareTo(firstNameB) < 0) {
				if(lastNameA.compareTo(lastNameB) < 0) {
					return -1;
				} else if(lastNameA.compareTo(lastNameB) == 0) {
					return 0;
				} else {
					return 1;
				}
			} else if (firstNameA.compareTo(firstNameB) == 0) {
				if(lastNameA.compareTo(lastNameB) < 0) {
					return -1;
				} else if(lastNameA.compareTo(lastNameB) == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		} else if (brokerA.compareTo(brokerB) == 0) {
			if(firstNameA.compareTo(firstNameB) < 0) {
				if(lastNameA.compareTo(lastNameB) < 0) {
					return -1;
				} else if(lastNameA.compareTo(lastNameB) == 0) {
					return 0;
				} else {
					return 1;
				}
			} else if (firstNameA.compareTo(firstNameB) == 0) {
				if(lastNameA.compareTo(lastNameB) < 0) {
					return -1;
				} else if(lastNameA.compareTo(lastNameB) == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		}
		return 1;
	}

	public static void summaryReport(CustomList report) {
		double value = 0, anReturn = 0, fees = 0, commissions = 0;
		System.out.println(
				"Portfolio Code \t\tPortfolio Owner \t\tPortfolio Manager \t\tTotal Fees \t\tTotal Commissions \t\tAggregate Risk \t\tAnnual Returns \t\tTotal Value");
		System.out.println(
				"===========================================================================================================================================================================================================");

		// Goes through the array portfolio by portfolio to print a line by line report
		// of the values in the portfolio
		for (Portfolio s : report) {
			double totalValue = getTotalValue(s.getAssetList());
			value += totalValue;
			double aggregateRisk = getAggregateRisk(s.getAssetList(), totalValue);
			double annualReturn = getAnnualReturn(s.getAssetList());
			anReturn += annualReturn;
			double totalFees = getTotalFee(s);
			fees += totalFees;
			double totalCommissions = getTotalCommission(s, annualReturn);
			commissions += totalCommissions;

			String userName = s.getOwner().getLastName() + ", " + s.getOwner().getFirstName();
			String managerName = s.getManager().getLastName() + ", " + s.getManager().getFirstName();

			// Prints out one portfolio per loop
			System.out.printf("%-23s %-31s %-31s $%-22.2f $%-30.2f %-23.4f $%-22.2f $%-32.2f\n", s.getPortfolioCode(),
					userName, managerName, totalFees, totalCommissions, aggregateRisk, annualReturn, totalValue);
		}
		System.out.println(
				"===========================================================================================================================================================================================================");
		System.out.printf("\t\t\t\t\t\t\t\t\t      Results = $%-22.2f $%-54.2f $%-22.2f $%-32.2f\n", fees, commissions,
				anReturn, value);
	}

	public static void detailReport(CustomList report) {
		System.out.printf(
				"\n\nPortfolio Details\n============================================================================================================================================================================\n");

		// Iterates through the Portfolio array and prints the detailed report for each
		for (Portfolio s : report) {
			System.out.printf(
					"Portfolio %s\n-----------------------------------------------------------------------------------\n",
					s.getPortfolioCode());

			double totalValue = getTotalValue(s.getAssetList());
			double aggregateRisk = getAggregateRisk(s.getAssetList(), totalValue);
			double annualReturn = getAnnualReturn(s.getAssetList());
			String managerName = s.getManager().getLastName() + ", " + s.getManager().getFirstName();

			// Test case to check if there is a beneficiary
			String beneficiaryInfo = "";
			if (!(s.getBeneficiaryCode() == null) && !(s.getBeneficiary() == null)) {
				beneficiaryInfo += s.getBeneficiary().toString();
			} else {
				beneficiaryInfo += "None";
			}

			System.out.printf("Owner:\n%s \nManager:\n%s \nBeneficiary:\n%s \nAssets:\n",
					s.getOwner().toString(), managerName, beneficiaryInfo);
			System.out.printf("Code \t\tAsset \t\t\t\t\tReturn Rate \t\tRisk \t\tAnnual Return \t\tValue\n");

			// Iterates through each asset in the portfolio to print out the details for
			// each asset
			for (HashMap.Entry<String, Asset> c : s.getAssetList().entrySet()) {
				String label = c.getValue().getLabel();
				double anReturn = (double) c.getValue().getAnnualReturn();
				double anReturnRate = ((double) anReturn / (double) c.getValue().getTotalWorth()) * 100.0;
				double risk = c.getValue().getRisk();
				double value = c.getValue().getTotalWorth();

				System.out.printf("%-15s %-31s %18.2f%% %16.2f \t\t$%-22.2f $%-20.2f\n", c.getKey(), label,
						anReturnRate, risk, anReturn, value);
			}
			System.out.printf(
					"\t\t\t\t\t\t\t----------------------------------------------------------------------------------\n");
			System.out.printf("\t\t\t\t\t\t\t Totals %20.4f \t\t$%-22.2f $%-20.2f\n\n", aggregateRisk, annualReturn,
					totalValue);
		}
	}

	public static void loadPortfolios() {
		CustomList b = new CustomList();
		CustomList c = new CustomList();
		CustomList d = new CustomList();

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
				String ownerCode = rs.getString("ownerCode");
				String managerCode = rs.getString("managerCode");
				String beneficiaryCode = rs.getString("beneficiaryCode");
				HashMap<String, Asset> assetList = loadAsset(portfolioId);
				User owner = loadUser(ownerCode);
				User manager = loadUser(managerCode);
				User beneficiary = null;
				if(beneficiaryCode != null) {
					beneficiary = loadUser(beneficiaryCode);
				}

				if (beneficiaryCode != null) {
					Portfolio p = new Portfolio(portfolioCode, ownerCode, managerCode, beneficiaryCode);
					p.setAssetList(assetList);
					p.setOwner(owner);
					p.setManager(manager);
					p.setBeneficiary(beneficiary);
					b.insertByName(p);
					c.insertByValue(p);
					d.insertByManager(p);
				} else {
					Portfolio p = new Portfolio(portfolioCode, ownerCode, managerCode);
					p.setAssetList(assetList);
					p.setOwner(owner);
					p.setManager(manager);
					b.insertByName(p);
					c.insertByValue(p);
					d.insertByManager(p);
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
		Portfolio.summaryReport(b);
		Portfolio.summaryReport(c);
		Portfolio.summaryReport(d);

		return;
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public User getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(User beneficiary) {
		this.beneficiary = beneficiary;
	}

	public static HashMap<String, Asset> loadAsset(int portfolioId) {
		HashMap<String, Asset> assets = new HashMap<String, Asset>();

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

		String query = "select a.assetCode, a.label, a.apr, a.quarterlyDividend, a.baseRateReturn, a.betaMeasure, a.stockSymbol, a.sharePrice,"
						+ " a.baseOmegaMeasure, a.totalValue, ap.assetValue from Portfolio p left join AssetPortfolio ap"
						+ " on p.portfolioId = ap.portfolioId left join Asset a on a.assetId = ap.assetId where p.portfolioId = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, portfolioId);
			rs = ps.executeQuery();
			while (rs.next()) {
				String assetCode = rs.getString("assetCode");
				String label = rs.getString("label");
				Double apr = rs.getDouble("apr");
				Double quarterlyDividend = rs.getDouble("quarterlyDividend");
				Double baseRateReturn = rs.getDouble("baseRateReturn");
				Double betaMeasure = rs.getDouble("betaMeasure");
				String stockSymbol = rs.getString("stockSymbol");
				Double sharePrice = rs.getDouble("sharePrice");
				Double baseOmegaMeasure = rs.getDouble("baseOmegaMeasure");
				Double totalValue = rs.getDouble("totalValue");
				Double assetValue = rs.getDouble("assetValue");
				assetCode += Double.toString(assetValue);
				if (totalValue > 0) {
					PrivateInvestment pm = new PrivateInvestment(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue);
					pm.setValue(assetValue);
					assets.put(assetCode, pm);
				}
				if (stockSymbol != null) {
					Stocks pm = new Stocks(assetCode, label, quarterlyDividend, baseRateReturn, betaMeasure, stockSymbol, sharePrice);
					pm.setValue(assetValue);
					assets.put(assetCode, pm);
				}
				if (apr > 0) {
					Deposit pm = new Deposit(assetCode, label, apr);
					pm.setValue(assetValue);
					assets.put(assetCode, pm);
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

		return assets;
	}
	
	public static User loadUser(String ownerCode) {
		User b = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); //.getDeclaredConstructor().newInstance();
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
		

		String query = "select pm.personId, pm.personCode, pm.firstName, pm.lastName, pm.brokerStatus as brokerStatus, pm.secIdentity as secIdentity, ad.street, c.city, s.state, ad.zip, ad.country from Person pm " 
	                + "left join Address ad on pm.addressId = ad.addressId "
                    + "left join City c on ad.cityId = c.cityId "
                    + "left join State s on ad.stateId = s.stateId where pm.personCode = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, ownerCode);
			rs = ps.executeQuery();
      while(rs.next()) { 
        int personId = rs.getInt("personId");
        String personCode = rs.getString("personCode");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String brokerStatus = rs.getString("brokerStatus");
        String secIdentity = rs.getString("secIdentity");
        String street = rs.getString("street");
        String city = rs.getString("city");
        String state = rs.getString("state");
        String zip = rs.getString("zip");
        String country = rs.getString("country");
        Set<String> email = loadEmail(personId);
        Address a = new Address(street, city, state, zip, country);

        if(brokerStatus != null && secIdentity != null && (brokerStatus.contains("J") || brokerStatus.contains("E"))) {
          b = new User(personCode, a, firstName, lastName, brokerStatus, secIdentity, email);
        } else {
          b = new User(personCode, a, firstName, lastName, "", "", email);
        }
      }
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return b;
	}

	public static Set<String> loadEmail(int personId) {
		Set<String> emails = new HashSet<String>();

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

		String query = "select e.email from Email e where e.personId = ?;";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			while (rs.next()) {
				String email = rs.getString("email");
				emails.add(email);
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

		return emails;
	}

}
