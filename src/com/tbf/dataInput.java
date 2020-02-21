package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class dataInput {

	public static User[] parsePersons(String file) {

		Scanner input = null;

		try {
			input = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Get the size of the file (how many lines of data there are an initialize an
		// array of users
		int size = Integer.parseInt(input.nextLine());
		User inputUsers[] = new User[size];

		// Splits each line of data into its correct parts
		for (int i = 0; i < size; i++) {
			String token[] = input.nextLine().split(";", -5);
			String broker[] = token[1].split(",", -2);
			String names[] = token[2].split(",", -2);
			String address[] = token[3].split(",");
			Address p1 = new Address(address[0], address[1], address[2], address[3], address[4]);
			String email[] = token[4].split(",");
			Set<String> emails = new HashSet<String>();

			// For loop to put all email addresses into the list
			for (int j = 0; j < email.length; j++) {
				emails.add(email[j]);
			}

			// If/else statement to separate when the user is a broker or not
			if (broker[0].contains("E") || broker[0].contains("J")) {
				inputUsers[i] = new User(token[0], p1, names[1].replaceAll("\\s+", ""), names[0], broker[0], broker[1],
						emails);
			} else {
				inputUsers[i] = new User(token[0], p1, names[1].replaceAll("\\s+", ""), names[0], "", "", emails);
			}
		}
		input.close();

		return inputUsers;
	}

	public static Asset[] parseAssets(String file) {

		Scanner input = null;

		try {
			input = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Get the size of the file (how many lines of data there are an initialize an
		// array of users
		int size = Integer.parseInt(input.nextLine());
		Asset inputUsers[] = new Asset[size];

		// Splits each line of data into its correct parts
		for (int i = 0; i < size; i++) {
			String temp = input.nextLine();

			// Parses the data based on the type of account
			if (temp.contains(";D;")) {
				String token[] = temp.split(";", -4);
				Deposit tempB = new Deposit(token[0], token[2], Double.parseDouble(token[3]) / 100);
				inputUsers[i] = tempB;
			} else if (temp.contains(";S;")) {
				String token[] = temp.split(";", -8);
				Stocks tempB = new Stocks(token[0], token[2], Double.parseDouble(token[3]),
						Double.parseDouble(token[4]) / 100, Double.parseDouble(token[5]), token[6],
						Double.parseDouble(token[7]));
				inputUsers[i] = tempB;
			} else if (temp.contains(";P;")) {
				String token[] = temp.split(";", -7);
				PrivateInvestments tempB = new PrivateInvestments(token[0], token[2], Double.parseDouble(token[3]),
						Double.parseDouble(token[4]) / 100, Double.parseDouble(token[5]), Double.parseDouble(token[6]));
				inputUsers[i] = tempB;
			}
		}
		input.close();

		return inputUsers;
	}
}
