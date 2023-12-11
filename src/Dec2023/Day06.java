package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 {
	
	public static void run() {
		solvePart1();
		
		solvePart2();
	}
	
	private static List<Long> durations;
	private static List<Long> distances;
	
	private static void solvePart1() {
		initializeDurationsAndDistances();
		
		int mult = 1;
		
		for (int i = 0; i < durations.size(); i++) {
			//System.out.println(i + ": " + beatRecordPossibilities(durations.get(i), distances.get(i)));
			mult *= beatRecordPossibilities(durations.get(i), distances.get(i));
		}
		
		System.out.println(mult);
	}
	
	private static void solvePart2() {
		initializeDurationsAndDistances2();
		
		int mult = 1;
		
		for (int i = 0; i < durations.size(); i++) {
			mult *= beatRecordPossibilities(durations.get(i), distances.get(i));
		}
		
		System.out.println(mult);
	}
	
	private static long beatRecordPossibilities(long duration, long currentRecord) {
		long lower = 0;
		long higher = duration;
		
		
		for (long i = 1; i < duration; i++) {
			long runDistance = getRunDistance(duration, i);
			if (runDistance > currentRecord) {
				lower = i;
				break;
			}
		}
		
		for (long i = duration - 1; i > 0; i--) {
			long runDistance = getRunDistance(duration, i);
			if (runDistance > currentRecord) {
				higher = i;
				break;
			}
		}
		
		return higher < lower ? 0 : higher - lower + 1;
	}
	
	private static long getRunDistance(long duration, long holdButtonDuration) {
		return (duration - holdButtonDuration) * holdButtonDuration;
	}
	
	private static void initializeDurationsAndDistances() {
		durations = new ArrayList<Long>();
		distances = new ArrayList<Long>();
		String timeString = "Time: ";
		String distanceString = "Distance: ";
		
		try {
	        File myObj = new File("InputFiles/6.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            String numberComposition = "";
	            
	            if (line.startsWith(timeString)) {
	            	for (int i = timeString.length(); i < line.length(); i++) {
	            		String charAt = Character.toString(line.charAt(i));
	            		
	            		long intAt = stringToLong(charAt);
	            		if (intAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if ((charAt.equals(" ") || i == line.length() - 1) && !numberComposition.equals("")) {
	            			durations.add(stringToLong(numberComposition));
	            			numberComposition = "";
	            		}
	            	}
	            }
	            else {
	            	for (int i = distanceString.length(); i < line.length(); i++) {
	            		String charAt = Character.toString(line.charAt(i));
	            		
	            		long intAt = stringToLong(charAt);
	            		if (intAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if ((charAt.equals(" ") || i == line.length() - 1) && !numberComposition.equals("")) {
	            			distances.add(stringToLong(numberComposition));
	            			numberComposition = "";
	            		}
	            	}
	            }
	            
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void initializeDurationsAndDistances2() {
		durations = new ArrayList<Long>();
		distances = new ArrayList<Long>();
		String timeString = "Time: ";
		String distanceString = "Distance: ";
		
		try {
	        File myObj = new File("InputFiles/6.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            String numberComposition = "";
	            
	            if (line.startsWith(timeString)) {
	            	for (int i = timeString.length(); i < line.length(); i++) {
	            		String charAt = Character.toString(line.charAt(i));
	            		
	            		long intAt = stringToLong(charAt);
	            		if (intAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if (i == line.length() - 1 && !numberComposition.equals("")) {
	            			durations.add(stringToLong(numberComposition));
	            			numberComposition = "";
	            		}
	            	}
	            }
	            else {
	            	for (int i = distanceString.length(); i < line.length(); i++) {
	            		String charAt = Character.toString(line.charAt(i));
	            		
	            		long intAt = stringToLong(charAt);
	            		if (intAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if (i == line.length() - 1 && !numberComposition.equals("")) {
	            			distances.add(stringToLong(numberComposition));
	            			numberComposition = "";
	            		}
	            	}
	            }
	            
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	private static long stringToLong(String s) {
		try {
			return Long.parseLong(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
