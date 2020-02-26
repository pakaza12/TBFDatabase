/*
 * Authors: Parker Z and Jayden C.
 * This program has two methods implemented in it. The first method takes in a file thats a report of user data. The method then 
 * breaks up each part to put into an xml file.
 * The second method does similar actions. It takes in a file containing assets to a specific user and splits it up and prints to an xml file
 * Date:02/14/20
 */

package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

public class DataConverter {

	public static void main(String[] args) {

		User[] person = dataInput.parsePersons("data/Persons.dat");
		dataOutput.peopleToXML(person, "data/Persons.xml");
		Asset[] assets = dataInput.parseAssets("data/Assets.dat");
		dataOutput.assetsToXML(assets, "data/Assets.xml");
		//dataOutput.peopleToXML(dataInput.parsePersons("data/Persons.dat"), "data/Persons.xml");
		//dataOutput.assetsToXML(dataInput.parseAssets("data/Assets.dat"), "data/Assets.xml");		
		
	}

}
