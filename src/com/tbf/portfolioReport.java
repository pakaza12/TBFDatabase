package com.tbf;

import java.util.HashMap;
import java.util.Set;

public class portfolioReport {
	
	
	
	public static double value(HashMap<String, Double> a, Asset[] i) {
	double value = 0;
	
	Set<String> b = a.keySet();
	for(String c : b) {
		for(int j = 0; j < i.length; j++) {
			if(c.contains(i[j].getCode())) {
				if(i[j].contains("Deposit")) {
					
				}
			}
		}
	}
	
		
	return value;
	}
	
	
	public static void summary (Portfolio[] report, User[] p, Asset[] a) {
		for(Portfolio s: report) {
			
		System.out.println();
		
	}
	}
}
