package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.thoughtworks.xstream.XStream;

/**
 * This is a class to handle the output of data
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class dataOutput {

	public static void peopleToXML(User[] users, String outputFile) {
		
		// Calls XStream in order to change each user to an xml string
		XStream xstream = new XStream();
		xstream.processAnnotations(User.class);
		String result = xstream.toXML(users);

		File output = null;
		PrintWriter pw = null;

		try {
			output = new File(outputFile);
			pw = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		pw.println(result);
		pw.close();
	}
	
	public static void assetsToXML(Asset[] assets, String outputFile) {
		
		// Calls XStream in order to change each user to an xml string
		XStream xstream = new XStream();
		xstream.processAnnotations(Asset.class);
		xstream.processAnnotations(Deposit.class);
		xstream.processAnnotations(PrivateInvestment.class);
		xstream.processAnnotations(Stocks.class);
		String result = xstream.toXML(assets);

		File output = null;
		PrintWriter pw = null;

		try {
			output = new File(outputFile);
			pw = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		pw.println(result);
		pw.close();
	}
	
}
