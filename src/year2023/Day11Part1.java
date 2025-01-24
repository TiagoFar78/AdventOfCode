package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day11Part1 {
	
	private static List<String> space;
	private static List<Integer> galaxiesLines;
	private static List<Integer> galaxiesCols;
	
	public static void run() {
		initializeSpace();
		
		expandSpace();
		initializeGalaxies();
		
		int sum = 0;
		for (int i = 0; i < galaxiesLines.size(); i++) {
			for (int j = i + 1; j < galaxiesLines.size(); j++) {
				int line1 = galaxiesLines.get(i);
				int col1 = galaxiesCols.get(i);
				int line2 = galaxiesLines.get(j);
				int col2 = galaxiesCols.get(j);
				
				if (line1 != line2 || col1 != col2) {
					sum += getDistanceBetweenGalaxies(line1, col1, line2, col2);
				}
			}
		}
		
		System.out.println(sum);
	}
	
	private static int getDistanceBetweenGalaxies(int line1, int col1, int line2, int col2) {		
		return Math.abs(line1 - line2) + Math.abs(col1 - col2);
	}
	
	private static void expandSpace() {
		for (int i = 0; i < space.get(0).length(); i++) {
			boolean emptyCol = true;
			for (int j = 0; j < space.size(); j++) {
				String charAt = Character.toString(space.get(j).charAt(i));
				
				if (charAt.equals("#")) {
					emptyCol = false;
					break;
				}
			}
			
			if (emptyCol) {
				for (int j = 0; j < space.size(); j++) {
					String line = space.get(j);
					String newLine = line.substring(0, i) + "." + line.substring(i);
					
					space.set(j, newLine);
				}
				
				i++;
			}
		}
		
		for (int i = 0; i < space.size(); i++) {
			if (!space.get(i).contains("#")) {
				String temp = space.get(i);
				space.add("");
				for (int j = i + 1; j < space.size(); j++) {
					String temp2 = space.get(j);
					space.set(j, temp);
					
					temp = temp2;
				}
				
				i++;
			}
		}
	}
	
	private static void initializeGalaxies() {
		galaxiesLines = new ArrayList<Integer>();
		galaxiesCols = new ArrayList<Integer>();
		
		for (int i = 0; i < space.size(); i++) {
			String line = space.get(i);
			
			int lastIndexOfCardinal = line.lastIndexOf("#");
			while (lastIndexOfCardinal != -1) {
				galaxiesLines.add(i);
				galaxiesCols.add(lastIndexOfCardinal);
				
				lastIndexOfCardinal = line.substring(0, lastIndexOfCardinal).lastIndexOf("#");
			}
		}
	}
	
	private static void initializeSpace() {
		space = new ArrayList<String>();
		
		try {
	        File myObj = new File("InputFiles/11.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            space.add(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
	}

}
