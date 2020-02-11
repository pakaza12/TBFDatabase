package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

public class DataConverter {

	public static void main(String[] args) {
		
		try {
			//Open a file and process it for input
			Scanner input = new Scanner(new File("data/Persons.dat"));

			int size = Integer.parseInt(input.nextLine());
			User inputUsers[] = new User[size];
			
			for(int i = 0; i < 2; i++) {
				String token[] = input.nextLine().split(";", -5);
				//ToDo, put if statement for broker if it breaks code
				String broker[] = token[1].split(",", -2);
				String names[] = token[2].split(",", -2);
				String address[] = token[3].split(",");
				Address p1 = new Address(address[0], address[1], address[2], address[3], address[4]);
				String email[] = token[4].split(",");
				Set<String> emails = new HashSet<String>();
				for(int j = 0; j < email.length; j++) {
					emails.add(email[j]);
				}
				if(broker[0].contains("E") || broker[0].contains("J")) {
					inputUsers[i] = new User(token[0], p1, names[1], names[0], broker[0], broker[1], emails);
				} else {
					inputUsers[i] = new User(token[0], p1, names[1], names[0], "", "", emails);
				}
				System.out.println(token[0] + "/" + p1 + "/" + names[1] + "/" + names[0] + "/" + emails);
			}
			input.close();
			
			String result = "";
			XStream xstream = new XStream();
			result = xstream.toXML(inputUsers[0]);
			System.out.println(result);
			/*for(int i = 0; i < size; i++) {
				System.out.print(inputUsers[i]);
				String xml = xstream.toXML(inputUsers[i]);
				result += xml;
			}*/
			
			
			
			File output = new File("data/Persons.xml");
			PrintWriter pw = new PrintWriter(output);
			
			pw.println(result);
			
			pw.close();
			// Error checking for File I/O
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InputMismatchException ime) {
			throw new RuntimeException("Bad file man");
		}
	}

}
