package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class represents a User
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
@XStreamAlias("person")
public class User {

	private String personCode;
	private Address address;
	private String firstName;
	private String lastName;
	private String brokerStatus;
	private String secIdentity;
	private Set<String> email;
	
	public User(String personCode, Address address, String firstName, String lastName, String brokerStatus,
			String secIdentity, Set<String> email) {
		super();
		this.personCode = personCode;
		this.address = address;
		this.firstName = firstName;
		this.lastName = lastName;
		this.brokerStatus = brokerStatus;
		this.secIdentity = secIdentity;
		this.email = email;
	}

	public String getPersonCode() {
		return this.personCode;
	}

	public String getAddress() {
		return this.getAddress();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getBrokerStatus() {
		return this.brokerStatus;
	}

	public String getSecidnetity() {
		return this.secIdentity;
	}

	public Set<String> getEmail() {
		return this.email;
	}

	public String toString() {
		return (this.lastName + ", " + this.firstName + "\n" + this.getEmail() + "\n" + this.address.getStreet() + "\n"
				+ this.address.getCity() + ", " + this.address.getState() + " " + this.address.getCountry() + " "
				+ this.address.getZip());
	}

	public boolean isJuniorBroker() {
		if (this.brokerStatus.equals("J")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExpertBroker() {
		if (this.brokerStatus.equals("E")) {
			return true;
		} else {
			return false;
		}
	}

	public double getCommission(double annualReturn) {
		if (this.brokerStatus.contains("J")) {
			return annualReturn * 0.0125;
		} else if (this.brokerStatus.contains("E")) {
			return annualReturn * 0.0375;
		} else
			return 0;
	}

	public static ArrayList<User> loadUsers() {
		ArrayList<User> b = new ArrayList<User>();
		
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
                    + "left join State s on ad.stateId = s.stateId";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
      while(rs.next()) { //Might have to call rs.next before this if errors out here
        int personId = rs.getInt("personId");
        String personCode = rs.getString("personCode");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String brokerStatus = rs.getString("brokerStatus");
        String secIdentity = rs.getString("secIdentity");
        String street = rs.getString("street");
        String city = rs.getString("city");
        String state = rs.getString("state");
        String zip = Integer.toString(rs.getInt("zip"));
        String country = rs.getString("country");
        Set<String> email = loadEmail(personId);
        Address a = new Address(street, city, state, zip, country);

        if(brokerStatus != null && secIdentity != null && (brokerStatus.contains("J") || brokerStatus.contains("E"))) {
          b.add(new User(personCode, a, firstName, lastName, brokerStatus, secIdentity, email));
        } else {
          b.add(new User(personCode, a, firstName, lastName, "", "", email));
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

		String query = "select e.email from Email e where e.personId = " + personId;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
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
