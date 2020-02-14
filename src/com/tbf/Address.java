package com.tbf;

/**
 * This class represents an address
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class Address {

	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;

	
	/**
	 * Constructs an address structure
	 * @param String street
	 * @param String city
	 * @param String state
	 * @param String zip 
	 * @param String country
	 */
	public Address(String street, String city, String state, String zip, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
}
