package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12Part1 {
	
	private static List<String> springs;
	private static List<List<Integer>> damagedSpringsCodes;
	
	public static void run() {
		initializeSprings();
		
		int possibleArrangements = 0;
		
		for (int i = 0; i < springs.size(); i++) {
			int a = countPossibleArrangements(springs.get(i), damagedSpringsCodes.get(i));
			System.out.println("Index " + i + ": " + a);
			possibleArrangements += a;
		}
		
		System.out.println(possibleArrangements);
	}
	
	private static String getSmallerEquivalentString(String s) {
		String smallerString = "";
		
		boolean hasDotBefore = true;
		for (int i = 0; i < s.length(); i++) {
			String charAt = Character.toString(s.charAt(i));
			if (!charAt.equals(".")) {
				hasDotBefore = false;
				smallerString += charAt;
			}
			else if (!hasDotBefore) {
				hasDotBefore = true;
				smallerString += charAt;				
			}
		}
		
		int lastIndexOfDot = smallerString.lastIndexOf(".");
		while (lastIndexOfDot == smallerString.length() - 1 && smallerString.length() > 0) {
			smallerString = smallerString.substring(0, lastIndexOfDot);
			lastIndexOfDot = smallerString.lastIndexOf(".");
		}
		
		return smallerString;
	}
	
	private static int countPossibleArrangements(String spring, List<Integer> damagedSpringsCodes) {
		spring = getSmallerEquivalentString(spring);
		damagedSpringsCodes = new ArrayList<Integer>(damagedSpringsCodes);
		if (damagedSpringsCodes.size() == 0) {
			if (spring.length() == 0 || !spring.contains("#")) {
//				System.out.println("Esta spring tá com " + spring + " e tamos a ver o code vazio, mas deu 1");
				return 1;
			}
			
			return 0;
		}

//		System.out.println("Esta spring tá com " + spring + " e tamos a ver o code " + damagedSpringsCodes.get(0));
		
		int damagedSpringsAmount = damagedSpringsCodes.get(0);
		
		int minimumLengthLeft = 0;
		for (int damagedSpringLeft : damagedSpringsCodes) {
			minimumLengthLeft += damagedSpringLeft + 1;
		}
		minimumLengthLeft--;
		
		if (spring.length() < minimumLengthLeft) {
			return 0;
		}
		
		if (spring.substring(0, damagedSpringsAmount).contains(".")) {
			int firstSpringIndexAfterDot = getFirstOccurenceOf(spring, ".") + 1;				
			return countPossibleArrangements(spring.substring(firstSpringIndexAfterDot), damagedSpringsCodes);
		}
		
		if (spring.length() == damagedSpringsAmount) {			
//			System.out.println("Deu 1");
			return 1;
		}
		
		List<Integer> reducedDamagedSpringsCodes = new ArrayList<Integer>(damagedSpringsCodes);
		reducedDamagedSpringsCodes.remove(0);
		
		if (Character.toString(spring.charAt(damagedSpringsAmount)).equals("#")) {
			if (spring.startsWith("#")) {
				return 0;
			}
			
			return countPossibleArrangements(spring.substring(1), damagedSpringsCodes);
		}
		
		if (spring.startsWith("#")) {
			return countPossibleArrangements(spring.substring(damagedSpringsAmount + 1), reducedDamagedSpringsCodes);
		}
		
		return countPossibleArrangements(spring.substring(damagedSpringsAmount + 1), reducedDamagedSpringsCodes) + 
				countPossibleArrangements(spring.substring(1), damagedSpringsCodes);
	}
	
	private static int getFirstOccurenceOf(String s, String c) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.toString(s.charAt(i)).equals(c)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private static void initializeSprings() {
		springs = new ArrayList<String>();
		damagedSpringsCodes = new ArrayList<List<Integer>>();
		
		try {
	        File myObj = new File("InputFiles/12.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            String springLine = line.substring(0, line.lastIndexOf(" "));
	            springs.add(springLine);
	            
	            List<Integer> damagedSpringCode = new ArrayList<Integer>();
	            
	            String numberComposition = "";
	            
	            String damagedSpringsCodeLine = line.substring(line.lastIndexOf(" ") + 1) + ",";
	            for (int i = 0; i < damagedSpringsCodeLine.length(); i++) {
	            	String charAt = Character.toString(damagedSpringsCodeLine.charAt(i));
	            	if (!charAt.equals(",")) {
	            		numberComposition += charAt;
	            	}
	            	else {
	            		damagedSpringCode.add(stringToInt(numberComposition));
	            		numberComposition = "";
	            	}
	            }
	            
	            damagedSpringsCodes.add(damagedSpringCode);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	private static int stringToInt(String s) {
		try {
			return Integer.parseInt(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
