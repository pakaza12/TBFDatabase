package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class DataConverter {

	public static void main(String[] args) {
		
		//Persons();
		Assets();
		
	}
	
	public static void Persons() {
		try {
			//Open a file and process it for input
			Scanner input = new Scanner(new File("data/Persons.dat"));

			//Get the size of the file (how many lines of data there are an initialize an array of users
			int size = Integer.parseInt(input.nextLine());
			User inputUsers[] = new User[size];
			
			//Splits each line of data into its correct parts
			for(int i = 0; i < size; i++) {
				String token[] = input.nextLine().split(";", -5);
				String broker[] = token[1].split(",", -2);
				String names[] = token[2].split(",", -2);
				String address[] = token[3].split(",");
				Address p1 = new Address(address[0], address[1], address[2], address[3], address[4]);
				String email[] = token[4].split(",");
				Set<String> emails = new HashSet<String>();
				//For loop to put all email addresses into the list
				for(int j = 0; j < email.length; j++) {
					emails.add(email[j]);
				}
				//If/else statement to separate when the user is a broker or not
				if(broker[0].contains("E") || broker[0].contains("J")) {
					inputUsers[i] = new User(token[0], p1, names[1].replaceAll("\\s+", ""), names[0], broker[0], broker[1], emails);
				} else {
					inputUsers[i] = new User(token[0], p1, names[1].replaceAll("\\s+", ""), names[0], "", "", emails);
				}
			}
			input.close();			
			
			//Calls XStream in order to change each user to an xml string
			String result = "";
			XStream xstream = new XStream();
			xstream.alias("person", User.class);
			for(int i = 0; i < size; i++) {
				String xml = xstream.toXML(inputUsers[i]);
				if(i != size-1) {
					result += (xml + "\n");
				} else {
					result += xml;
				}
			}
			
			//Opens PrintWriter in order to output the xml string
			File output = new File("data/Persons.xml");
			PrintWriter pw = new PrintWriter(output);
			pw.println(result);
			
			pw.close();
			// Error checking
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void Assets() {
		try {
			//Open a file and process it for input
			Scanner input = new Scanner(new File("data/Assets.dat"));

			//Get the size of the file (how many lines of data there are an initialize an array of users
			int size = Integer.parseInt(input.nextLine());
			Accounts inputUsers[] = new Accounts[size];
			
			//Splits each line of data into its correct parts
			for(int i = 0; i < size; i++) {
				String temp = "";
				temp += input.nextLine();
				if(temp.contains(";D;")) {
					String token[] = temp.split(";", -4);
					Deposit tempB = new Deposit(token[0], token[2], Double.parseDouble(token[3]));
					inputUsers[i] = tempB;
				} else if(temp.contains(";S;")) {
					String token[] = temp.split(";", -8);
					Stocks tempB = new Stocks(token[0], token[2], Double.parseDouble(token[3]), Double.parseDouble(token[4]), Double.parseDouble(token[5]), token[6], Double.parseDouble(token[7]));
					inputUsers[i] = tempB;	
				} else if(temp.contains(";P;")) {
					String token[] = temp.split(";", -7);
					PrivateInvestments tempB = new PrivateInvestments(token[0], token[2], Double.parseDouble(token[3]), Double.parseDouble(token[4]), Double.parseDouble(token[5]), Double.parseDouble(token[6]));
					inputUsers[i] = tempB;
				}
			}
			input.close();			
			
			//Calls XStream in order to change each user to an xml string
			String result = "";
			//XStream xstream = new XStream();
			XStream xstream = new XStream(new StaxDriver()) {
			      @Override
			      protected void setupConverters() {
			      }
			    };
			    xstream.registerConverter(new ReflectionConverter(xstream.getMapper(), xstream.getReflectionProvider()), XStream.PRIORITY_VERY_LOW);
			    xstream.registerConverter(new IntConverter(), XStream.PRIORITY_NORMAL);
			    xstream.registerConverter(new StringConverter(), XStream.PRIORITY_NORMAL);
			    xstream.registerConverter(new CollectionConverter(xstream.getMapper()), XStream.PRIORITY_NORMAL);
			for(int i = 0; i < size; i++) {
				String xml = xstream.toXML(inputUsers[i]);
				if(i != size-1) {
					result += (xml + "\n");
				} else {
					result += xml;
				}
			}

			//Opens PrintWriter in order to output the xml string
			File output = new File("data/Assets.xml");
			PrintWriter pw = new PrintWriter(output);
			pw.println(result);
			
			pw.close();
			// Error checking
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
