package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day03 {
	
	private static List<String> engine = new ArrayList<String>();
	
	private static final String GEAR_SYMBOL = "*";
	
	public static void run() {
		solvePart1();
		
		solvePart2();
	}
	
	private static void solvePart1() {
		initializeEngine();
		
		int sum = 0;
		
		for (int i = 0; i < engine.size(); i++) {
			String engineLine = engine.get(i);
			
			String numberComposition = "";
			int partNumber = 0;
			boolean isAdjacentToSymbol = false;
			
			for (int j = 0; j < engineLine.length(); j++) {
				String charAt = Character.toString(engineLine.charAt(j));
				
				int charAtNumber = stringToInteger(charAt);
				if (charAtNumber != -1) {
					numberComposition += charAt;
					if (!isAdjacentToSymbol && isAdjacentToSymbol(i, j)) {
						isAdjacentToSymbol = true;
					}
					
					if (j != engineLine.length() - 1)
						continue;
				}
				
				if (!numberComposition.equals("")) {
					if (isAdjacentToSymbol) {
						partNumber = stringToInteger(numberComposition);
						sum += partNumber;
					}
					
					numberComposition = "";
					isAdjacentToSymbol = false;
				}
			}
		}
		
		System.out.println(sum);		
		
	}
	
	private static boolean isAdjacentToSymbol(int line, int row) {
		for (int i = line - 1; i <= line + 1; i++) {
			for (int j = row - 1; j <= row + 1; j++) {
				if (isOnEngine(i, j)) {
					String charAt = Character.toString(engine.get(i).charAt(j));
					
					if (stringToInteger(charAt) == -1 && !charAt.equals(".")) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private static void solvePart2() {
		engine = new ArrayList<String>();
		initializeEngine();
		
		int sum = 0;
		
		for (int i = 0; i < engine.size(); i++) {
			String engineLine = engine.get(i);
			for (int j = 0; j < engineLine.length(); j++) {
				String charAt = Character.toString(engineLine.charAt(j));
				
				if (charAt.equals(GEAR_SYMBOL)) {
					sum += getGearRatio(i, j);
				}
			}
		}
		
		System.out.println(sum);
	}
	
	private static int getGearRatio(int line, int row) {
		String numberBuilder = "";
		List<Integer> numbersArround = new ArrayList<Integer>();
		
		for (int i = line - 1; i <= line + 1; i++) {
			for (int j = row - 1; j <= row + 1; j++) {
				if (isOnEngine(i, j)) {
					String charAt = Character.toString(engine.get(i).charAt(j));
					int charNumber = stringToInteger(charAt);
					
					if (charNumber != -1) {
						numberBuilder = charAt;
						int startingIndex = j;
						
						for (int k = startingIndex - 1; isOnEngine(i, k); k--) {
							charAt = Character.toString(engine.get(i).charAt(k));
							if (stringToInteger(charAt) == -1) {
								break;
							}
							
							numberBuilder = charAt + numberBuilder;
						}
						
						int k;
						for (k = startingIndex + 1; isOnEngine(i, k); k++) {
							charAt = Character.toString(engine.get(i).charAt(k));
							if (stringToInteger(charAt) == -1) {
								break;
							}
							
							numberBuilder += charAt;
						}
						j = k - 1;
						
						numbersArround.add(stringToInteger(numberBuilder));
					}
				}
			}
		}
		
		if (numbersArround.size() != 2) {
			return 0;
		}
		
		return numbersArround.get(0) * numbersArround.get(1);
	}
	
	private static void initializeEngine() {
		try {
	        File myObj = new File("InputFiles/3.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          engine.add(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private static boolean isOnEngine(int line, int row) {
		return line >= 0 && line < engine.size() && row >= 0 && row < engine.get(line).length();
	}
	
	private static int stringToInteger(String s) {
		try {
			return Integer.parseInt(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
