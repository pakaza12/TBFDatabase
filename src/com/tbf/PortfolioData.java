package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {}
	
	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
		
	}
	
	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, String brokerType, String secBrokerId) {
		startJDBC();
		Connection conn = getConnection();
		
		//These functions check if the these things exist, if not they add them
		JDBCUtils.addCity(city, conn);
		JDBCUtils.addState(state, conn);
		JDBCUtils.addAddress(street, zip, country, city, state, conn);
		
		String query = "select p.firstName from Person p where p.personCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean personExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, personCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				personExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!personExist) {
			String query2 = "insert into Person(personCode, addressId, firstName, lastName) values (?, (Select a.addressId from Address a left join City c on"
								+ " a.cityId = c.cityId left join State s on s.stateId = a.stateId where a.street = ? where a.zip = ? where a.country = ?), ?, ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps1.setString(1, personCode);
				ps1.setString(2, street);
				ps1.setString(3, zip);
				ps1.setString(4, country);
				ps1.setString(5, firstName);
				ps1.setString(6, lastName);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {}

	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {}
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr - Annual Percentage Rate on the scale [0, 1]
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {}
	
	/**
	 * Adds a private investment asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn on the scale [0, 1]
	 * @param baseOmega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double baseOmega, Double totalValue) {}
	
	/**
	 * Adds a stock asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn on the scale [0, 1]
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {}
	
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {}
	
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {}
	
	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with 
	 * the portfolio corresponding to the provided <code>portfolioCode</code>.  
	 * The third parameter, <code>value</code> is interpreted as a <i>balance</i>, 
	 * <i>number of shares</i> or <i>stake percentage</i> (on the scale [0, 1])
	 * depending on the type of asset the <code>assetCode</code> is associated 
	 * with.
	 * 
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value) {}
	
	public static void startJDBC() { 
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
	}
	
	public static Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
	}
	
}

