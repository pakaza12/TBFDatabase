package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class holds some JDBC extra methods to help us add/delete 
 * data from the database easier
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class JDBCUtils {

	public static void addAddress(String street, String zip, String country, String city, String state, Connection conn) {		
		//Check to see if the address exists, if it doesn't, insert it
		String query = "select a.street from Address a left join State s on s.stateId = a.stateId left join City c on c.cityId = a.cityId where a.street = ? AND a.country = ? AND a.zip = ?;";
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		boolean addressExist = true;
		try {
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, street);
			ps1.setString(2, country);
			ps1.setString(3, zip);
			rs = ps1.executeQuery();
			if(!rs.next()) {
				addressExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!addressExist) {
			String query2 = "insert into Address(street, zip, country, cityId, stateId) values (?, ?, ?, (select c.cityId from City c where c.city = ?), (select s.stateId from State s where s.state = ?));";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, street);
				ps.setString(2, zip);
				ps.setString(3, country);
				ps.setString(4, city);
				ps.setString(5, state);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addCity(String city, Connection conn) {		
		//Check to see if the city exists, if it doesn't, insert it
		String cityQuery = "select c.city from City c where c.city = ?;";
		PreparedStatement cityps = null;
		ResultSet rs = null;
		boolean cityExist = true;
		try {
			cityps = conn.prepareStatement(cityQuery);
			cityps.setString(1, city);
			rs = cityps.executeQuery();
			if(!rs.next()) {
				cityExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!cityExist) {
			String cityQuery2 = "insert into City(city) values (?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(cityQuery2);
				ps.setString(1, city);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addState(String state, Connection conn) {		
		//Check to see if the city exists, if it doesn't, insert it
		String stateQuery = "select s.State from State s where s.state = ?;";
		PreparedStatement stateps = null;
		ResultSet rs = null;
		boolean stateExist = true;
		try {
			stateps = conn.prepareStatement(stateQuery);
			stateps.setString(1, state);
			rs = stateps.executeQuery();
			if(!rs.next()) {
				stateExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Inserts the state if it isn't in database
		if(!stateExist) {
			String stateQuery2 = "insert into State(state) values (?);";
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(stateQuery2);
				ps.setString(1, state);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteState(int stateId, Connection conn) {
		String query5 = "delete from State where stateId = ?;";
		PreparedStatement ps5 = null;
		try {
			ps5 = conn.prepareStatement(query5);
			ps5.setInt(1, stateId);
			ps5.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCity(int cityId, Connection conn) {
		String query6 = "delete from City where cityId = ?;";
		PreparedStatement ps6 = null;
		try {
			ps6 = conn.prepareStatement(query6);
			ps6.setInt(1, cityId);
			ps6.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAddress(int addressId, Connection conn) {
		String query7 = "delete from Address where addressId = ?;";
		PreparedStatement ps7 = null;
		try {
			ps7 = conn.prepareStatement(query7);
			ps7.setInt(1, addressId);
			ps7.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean doesAssetBelongToMore(String assetCode, Connection conn) {
		//Check if the asset has more than one corresponding person/portfolio
		String query2 = "select count(assetId) as numAssets from AssetPortfolio where assetId = (select assetId from Asset where assetCode = ?);";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		boolean oneAsset = false;
		try {
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, assetCode);
			rs2 = ps2.executeQuery();
			int numAssets = 0;
			while(rs2.next()) {
				numAssets += rs2.getInt("numAssets");
			}
			if(numAssets > 1) {
				oneAsset = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oneAsset;
	}
	
}
