package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataConverter {

	public static void main(String[] args) {
		
		try {
			//Open a file and process it for input
			Scanner input = new Scanner(new File("data/Persons.dat"));

			int size = Integer.parseInt(input.nextLine());
			User inputUsers[] = new User[size];
			
			for(int i = 0; i < size; i++) {
				String token[] = input.nextLine().split(";", -5);
				String broker[] = token[1].split(",", -2);
				String names[] = token[2].split(",", -2);
				String address[] = token[3].split(",");
				String email[] = token[4].split(",");
				User(token[0], token);
			}
			

			// Close the file
			input.close();
			// Error checking for File I/O
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InputMismatchException ime) {
			throw new RuntimeException("Bad file man");
		}

	}

}
