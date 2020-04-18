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
	public static void removeAllPersons() {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select p.personCode from Person p;";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String personCode = rs.getString("personCode");
				removePerson(personCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
		startJDBC();
		Connection conn = getConnection();
		
		//Check if the person exists
		String query1 = "select personCode from Person where personCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		boolean personExist = true;
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, personCode);
			rs1 = ps1.executeQuery();
			if(!rs1.next()) {
				personExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Check if the address has more than one corresponding person
		String query2 = "select addressId, count(addressId) as numPeople from Person where addressId = (select addressId from Person where personCode = ? );";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		int addressId = 0;
		boolean onePersonAddress = false;
		try {
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, personCode);
			rs2 = ps2.executeQuery();
			int numPeople = 0;
			while(rs2.next()) {
				addressId = rs2.getInt("addressId");
				numPeople += rs2.getInt("numPeople");
			}
			if(numPeople <= 1) {
				onePersonAddress = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Check if the state has multiple different addresses associated with it
		String query3 = "select stateId, count(stateId) as numState from Address where stateId = (select stateId from Address where addressId = (select addressId from Person where personCode = ?));";
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		int stateId = 0;
		boolean oneStateAddress = false;
		try {
			ps3 = conn.prepareStatement(query3);
			ps3.setString(1, personCode);
			rs3 = ps3.executeQuery();
			int numState = 0;
			while(rs3.next()) {
				stateId = rs3.getInt("stateId");
				numState = rs3.getInt("numState");
			}
			if(numState <= 1) {
				oneStateAddress = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Check if the city has multiple different addresses associated with it
		String query4 = "select cityId, count(cityId) as numCity from Address where cityId = (select cityId from Address where addressId = (select addressId from Person where personCode = ?));";
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		boolean oneCityAddress = false;
		int cityId = 0;
		try {
			ps4 = conn.prepareStatement(query4);
			ps4.setString(1, personCode);
			rs4 = ps4.executeQuery();
			int numCity = 0;
			while(rs4.next()) {
				cityId = rs4.getInt("cityId");
				numCity = rs4.getInt("numCity");
			}
			if(numCity <= 1) {
				oneCityAddress = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Deletes the related Portfolio(s) and AssetPortfolio tables
		String query5 = "select a.assetCode, p.portfolioCode from Portfolio p left join AssetPortfolio ap on ap.portfolioId = p.portfolioId left join Asset a on a.assetId = ap.assetId"
						+ " where p.personId = (select personId from Person where personCode = ?);";
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		try {
			ps5 = conn.prepareStatement(query5);
			ps5.setString(1, personCode);
			rs5 = ps5.executeQuery();
			while (rs5.next()) {
				String portfolioCode = rs5.getString("portfolioCode");
				String assetCode = rs5.getString("assetCode");
				
				//Deletes the asset related to the person if they were the only one with the asset
				if(!JDBCUtils.doesAssetBelongToMore(assetCode, conn)) {
					removeAsset(assetCode);
				}
				removePortfolio(portfolioCode);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//If the preson exists, it deletes the email(s) related to them, then the person
		if(personExist) {
			
			String query6 = "delete from Email where personId = (select personId from Person where personCode = ?);";
			PreparedStatement ps6 = null;
			try {
				ps6 = conn.prepareStatement(query6);
				ps6.setString(1, personCode);
				ps6.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String query7 = "delete from Person where personCode = ?;";
			PreparedStatement ps7 = null;
			try {
				ps7 = conn.prepareStatement(query7);
				ps7.setString(1, personCode);
				ps7.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//If the address only corresponds to one person, we can delete it now
		if(onePersonAddress) {
			JDBCUtils.deleteAddress(addressId, conn);
		}
		
		//If the state only corresponds to one address, we can delete it
		if(oneStateAddress && onePersonAddress) {
			JDBCUtils.deleteState(stateId, conn);
		}
		
		//If the city only corresponds to one address, we can delete it
		if(oneCityAddress && onePersonAddress) {
			JDBCUtils.deleteCity(cityId, conn);
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

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
		
		//If they have a blank/null broker type this enters the individual
		if(!personExist && (brokerType == null) ) {
			String query2 = "insert into Person(personCode, addressId, firstName, lastName) values (?, (Select a.addressId from Address a left join City c on"
								+ " a.cityId = c.cityId left join State s on s.stateId = a.stateId where (a.street = ? AND a.zip = ? AND a.country = ?) ), ?, ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, personCode);
				ps.setString(2, street);
				ps.setString(3, zip);
				ps.setString(4, country);
				ps.setString(5, firstName);
				ps.setString(6, lastName);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		//If they have a brokerType they are entered here
		} else if(!personExist && (brokerType.contains("J") || brokerType.contains("E")) ) {
			String query2 = "insert into Person(personCode, addressId, firstName, lastName, brokerStatus, secIdentity) values (?,"
								+ " (Select a.addressId from Address a left join City c on a.cityId = c.cityId left join State s on s.stateId = a.stateId" 
								+ " where (a.street = ? AND a.zip = ? AND a.country = ?) ), ?, ?, ?, ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, personCode);
				ps.setString(2, street);
				ps.setString(3, zip);
				ps.setString(4, country);
				ps.setString(5, firstName);
				ps.setString(6, lastName);
				ps.setString(7, brokerType);
				ps.setString(8, secBrokerId);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select e.email from Email e where e.email = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean emailExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, email);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				emailExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!emailExist) {
			String query2 = "insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = ?), ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, personCode);
				ps.setString(2, email);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select assetCode from Asset";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String assetCode = rs.getString("assetCode");
				removeAsset(assetCode);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {
		startJDBC();
		Connection conn = getConnection();
		
		//Check if the asset exists
		String query = "select a.assetId from Asset a where a.assetCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean assetExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, assetCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				assetExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//If the asset information exists, delete it from both Asset and AssetPortfolio (Connecting table to Portfolio holding the value)
		if(assetExist) {
			
			String query2 = "delete from AssetPortfolio where assetId = (select a.assetId from Asset a where a.assetCode = ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, assetCode);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String query4 = "delete from Asset where assetCode = ?;";
			PreparedStatement ps4 = null;
			try {
				ps4 = conn.prepareStatement(query4);
				ps4.setString(1, assetCode);
				ps4.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr - Annual Percentage Rate on the scale [0, 1]
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select a.assetCode from Asset a where a.assetCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean accountExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, assetCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				accountExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!accountExist) {
			String query2 = "insert into Asset(assetCode, label, apr) values (?, ?, ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, assetCode);
				ps.setString(2, label);
				ps.setDouble(3, apr);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
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
			Double baseRateOfReturn, Double baseOmega, Double totalValue) {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select a.assetCode from Asset a where a.assetCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean accountExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, assetCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				accountExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!accountExist) {
			String query2 = "insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue) values(?, ?, ?, ?, ?, ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, assetCode);
				ps.setString(2, label);
				ps.setDouble(3, quarterlyDividend);
				ps.setDouble(4, baseRateOfReturn);
				ps.setDouble(5, baseOmega);
				ps.setDouble(6, totalValue);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
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
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select a.assetCode from Asset a where a.assetCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean accountExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, assetCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				accountExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!accountExist) {
			String query2 = "insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, betaMeasure, stockSymbol, sharePrice) values(?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, assetCode);
				ps.setString(2, label);
				ps.setDouble(3, quarterlyDividend);
				ps.setDouble(4, baseRateOfReturn);
				ps.setDouble(5, beta);
				ps.setString(6, stockSymbol);
				ps.setDouble(7, sharePrice);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select p.portfolioCode from Portfolio p;";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String portfolioCode = rs.getString("portfolioCode");
				removePortfolio(portfolioCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {
		startJDBC();
		Connection conn = getConnection();
		
		//Check if the portfolio exists
		String query = "select p.portfolioCode from Portfolio p where p.portfolioCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean portfolioExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, portfolioCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				portfolioExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//If the portfolio exists, delete it's data from Portfolio and AssetPortfolio
		if(portfolioExist) {
			
			String query2 = "delete from AssetPortfolio where portfolioId = (select a.portfolioId from Portfolio a where a.portfolioCode = ?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, portfolioCode);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String query3 = "delete from Portfolio where portfolioCode = ?;";
			PreparedStatement ps3 = null;
			try {
				ps3 = conn.prepareStatement(query3);
				ps3.setString(1, portfolioCode);
				ps3.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		startJDBC();
		Connection conn = getConnection();
		
		//Error checking probably not necessary, but used for good design
		//Check if the owner exists
		String query = "select p.personCode from Person p where p.personCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean ownerExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, ownerCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				ownerExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Check if the manager exists
		String query2 = "select p.personCode from Person p where p.personCode = ?;";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		boolean managerExist = true;
		try {
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, managerCode);
			rs2 = ps2.executeQuery();
			if(!rs2.next()) {
				managerExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Check if the beneficiary exists
		String query4 = "select p.personCode from Person p where p.personCode = ?;";
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		boolean beneficiaryExist = true;
		try {
			ps4 = conn.prepareStatement(query4);
			ps4.setString(1, beneficiaryCode);
			rs4 = ps4.executeQuery();
			if(!rs4.next()) {
				beneficiaryExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Check if the portfolio exists
		String query5 = "select p.portfolioCode from Portfolio p where p.portfolioCode = ?;";
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		boolean portfolioExist = true;
		try {
			ps5 = conn.prepareStatement(query5);
			ps5.setString(1, portfolioCode);
			rs5 = ps5.executeQuery();
			if(!rs5.next()) {
				portfolioExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(ownerExist && managerExist && beneficiaryExist && !portfolioExist) {
			String query3 = "insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values (?, ?, ?, (select personId from Person p where p.personCode = ?));";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query3);
				ps.setString(1, portfolioCode);
				ps.setString(2, ownerCode);
				ps.setString(3, beneficiaryCode);
				ps.setString(4, ownerCode);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
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
	public static void addAsset(String portfolioCode, String assetCode, double value) {
		startJDBC();
		Connection conn = getConnection();
		
		String query = "select a.assetCode from Asset a where a.assetCode = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean assetExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, assetCode);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				assetExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query2 = "select p.portfolioCode from Portfolio p where p.portfolioCode = ?;";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		boolean portfolioExist = true;
		try {
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, portfolioCode);
			rs2 = ps2.executeQuery();
			if(!rs2.next()) {
				portfolioExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(assetExist && portfolioExist) {
			String query3 = "insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = ?),"
								+ " ?, (select a.assetId from Asset a where a.assetCode = ?));";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query3);
				ps.setString(1, portfolioCode);
				ps.setDouble(2, value);
				ps.setString(3, assetCode);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
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

