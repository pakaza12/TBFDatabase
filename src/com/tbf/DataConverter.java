/*
 * Authors: Parker Z and Jayden C.
 * This program has two methods implemented in it. The first method takes in a file thats a report of user data. The method then 
 * breaks up each part to put into an xml file.
 * The second method does similar actions. It takes in a file containing assets to a specific user and splits it up and prints to an xml file
 * Date:02/14/20
 */

package com.tbf;

public class DataConverter {

	public static void main(String[] args) {

		User[] person = dataInput.parsePersons("data/Persons.dat");
		dataOutput.peopleToXML(person, "data/Persons.xml");
		Asset[] assets = dataInput.parseAssets("data/Assets.dat");
		dataOutput.assetsToXML(assets, "data/Assets.xml");
		Portfolio[] portfolios = dataInput.parsePortfolio("data/Portfolios.dat");
	}

}
