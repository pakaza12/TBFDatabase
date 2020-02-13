package com.tbf;

import java.util.List;
import java.util.Set;

public class User {

	protected String personCode;
	protected Address address;
	protected String firstName;
	protected String lastName;
	protected String brokerStatus;
	protected String secIdentity;
	protected Set<String> email;
	protected List<Accounts> bankAccounts;
	
	
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
	
	public String getPersonCode(){
		return this.personCode;
	}
	
	public String getAddress() {
		return this.getAddress();
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public String getBrokerStatus(){
		return this.brokerStatus;
	}
	
	public String getSecidnetity(){
		return this.secIdentity;
	}
	
	public Set<String> getEmail(){
		return this.email;
	}
	
	
}
