package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day14Part2 {
	
	private static List<String> dish;
	
	private final static int CYCLES = 1000000000;
	
	public static void run() {
		initializeDish();
		
		List<List<String>> firstCyclesDishes = new ArrayList<List<String>>();
		
		int executedCyclesCount = 0;
		int equalInListIndex = 0;
		
		for (int i = 0; i < CYCLES; i++) {
			tiltNorth();
			tiltWest();
			tiltSouth();
			tiltEast();
			
			for (int j = 0; j < firstCyclesDishes.size(); j++) {
				if (areSameLists(dish, firstCyclesDishes.get(j))) {
					executedCyclesCount = i + 1;
					equalInListIndex = j;
					break;
				}
			}
			
			if (equalInListIndex != 0) {
				break;
			}
			
			firstCyclesDishes.add(cloneList(dish));
		}
		
		int leftCycles = CYCLES - executedCyclesCount;
		leftCycles = leftCycles % (executedCyclesCount - equalInListIndex - 1);
		
		for (int i = 0; i < leftCycles; i++) {		
			tiltNorth();
			tiltWest();
			tiltSouth();
			tiltEast();
		}
		
		int loadWeight = countNorthLoad();
		
		System.out.println(loadWeight);
	}
	
	private static int countNorthLoad() {
		int load = 0;
		
		for (int i = 0; i < dish.size(); i++) {
			load += (dish.size() - i) * countOccurencesOf(dish.get(i), "O");
		}
		
		return load;
	}
	
	private static int countOccurencesOf(String s, String c) {
		int total = 0;
		
		for (int i = 0; i < s.length(); i++) {
			if (Character.toString(s.charAt(i)).equals(c)) {
				total++;
			}
		}
		
		return total;
	}
	
	private static void tiltNorth() {		
		for (int col = 0; col < dish.get(0).length(); col++) {
			for (int line = 0; line < dish.size(); line++) {
				String charAt = Character.toString(dish.get(line).charAt(col));
				
				if (charAt.equals("O")) {
					int lineAux = line - 1;
					while (lineAux >= 0) {
						String charAtAux = Character.toString(dish.get(lineAux).charAt(col));
						if (!charAtAux.equals(".")) {
							break;
						}
						
						setOnDish(lineAux, col, "O");
						setOnDish(lineAux + 1, col, ".");
						
						lineAux--;
					}
				}
			}
		}
	}
	
	private static void tiltSouth() {		
		for (int col = 0; col < dish.get(0).length(); col++) {
			for (int line = dish.size() - 1; line >= 0 ; line--) {
				String charAt = Character.toString(dish.get(line).charAt(col));
				
				if (charAt.equals("O")) {
					int lineAux = line + 1;
					while (lineAux < dish.size()) {
						String charAtAux = Character.toString(dish.get(lineAux).charAt(col));
						if (!charAtAux.equals(".")) {
							break;
						}
						
						setOnDish(lineAux, col, "O");
						setOnDish(lineAux - 1, col, ".");
						
						lineAux++;
					}
				}
			}
		}
	}
	
	private static void tiltWest() {
		for (int line = 0; line < dish.size(); line++) {
			for (int col = 0; col < dish.get(0).length(); col++) {
				String charAt = Character.toString(dish.get(line).charAt(col));
				
				if (charAt.equals("O")) {
					int colAux = col - 1;
					while (colAux >= 0) {
						String charAtAux = Character.toString(dish.get(line).charAt(colAux));
						if (!charAtAux.equals(".")) {
							break;
						}
						
						setOnDish(line, colAux, "O");
						setOnDish(line, colAux + 1, ".");
						
						colAux--;
					}
				}
			}
		}
	}
	
	private static void tiltEast() {
		for (int line = 0; line < dish.size(); line++) {
			for (int col = dish.get(0).length() - 1; col >= 0; col--) {
				String charAt = Character.toString(dish.get(line).charAt(col));
				
				if (charAt.equals("O")) {
					int colAux = col + 1;
					while (colAux < dish.get(0).length()) {
						String charAtAux = Character.toString(dish.get(line).charAt(colAux));
						if (!charAtAux.equals(".")) {
							break;
						}
						
						setOnDish(line, colAux, "O");
						setOnDish(line, colAux - 1, ".");
						
						colAux++;
					}
				}
			}
		}
	}
	
	private static void setOnDish(int line, int col, String value) {
		String previousLine = dish.get(line);
		String newLine = previousLine.substring(0, col) + value + previousLine.substring(col + 1);
		
		dish.set(line, newLine);
	}
	
	private static List<String> cloneList(List<String> list) {
		List<String> clonedList = new ArrayList<String>();
		
		for (String element : list) {
			clonedList.add(element.substring(0));
		}
		
		return clonedList;
	}
	
	private static boolean areSameLists(List<String> l1, List<String> l2) {
		if (l1.size() != l2.size()) {
			return false;
		}
		
		for (int i = 0; i < l1.size(); i++) {
			if (!l1.get(i).equals(l2.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void initializeDish() {
		dish = new ArrayList<String>();
			
		try {
	        File myObj = new File("InputFiles/14.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            dish.add(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	

}
