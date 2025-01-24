package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day15Part1 {
	
	private static String[] stringsList;
	
	public static void run() {
		initializeStringsList();
		
		int sum = 0;
		
		for (String s : stringsList) {
			sum += calculateHashValue(s);
		}
		
		System.out.println(sum);		
	}
	
	private static int calculateHashValue(String s) {
		int total = 0;
		
		for (int i = 0; i < s.length(); i++) {
			total += (int) s.charAt(i);
			total *= 17;
			total = total % 256;
		}
		
		return total;
	}
	
	private static void initializeStringsList() {
		try {
		    File myObj = new File("InputFiles/15.txt");
		    Scanner myReader = new Scanner(myObj);
		    while (myReader.hasNextLine()) {	        	
		        String line = myReader.nextLine();
		        stringsList = line.split(",");
		    }
		    
		    myReader.close();
		}
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	}

}
