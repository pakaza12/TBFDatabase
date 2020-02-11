package com.tbf;

import java.util.Set;

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
	
}
