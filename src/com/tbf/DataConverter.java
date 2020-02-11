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
