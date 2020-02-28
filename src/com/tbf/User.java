package com.tbf;

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
		return (this.lastName + ", " + this.firstName  + "\n" + this.getEmail() + "\n" + this.address.getStreet() + "\n" + this.address.getCity() + ", " + this.address.getState() + " " + this.address.getCountry() + " " + this.address.getZip());
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
}
