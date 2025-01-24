package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day15Part2 {
	
	private static final int BOXES_AMOUNT = 256;
	
	private static Day15Part2 day15 = new Day15Part2();
	
	private class Len {
		
		String _name;
		int _focalLength;
		
		private Len(String name, int focalLength) {
			_name = name;
			_focalLength = focalLength;
		}
		
		public boolean hasSameName(String oName) {
			return _name.equals(oName);
		}
		
		public int getFocalLength() {
			return _focalLength;
		}
		
	}
	
	private static String[] stringsList;
	
	private static List<List<Len>> hashmap;
	
	public static void run() {
		initializeStringsList();
		initializeHashmap();
		
		for (String s : stringsList) {
			executeInHashmap(s);
		}
		
		int totalFocusingPower = 0;
		
		for (int boxIndex = 0; boxIndex < BOXES_AMOUNT; boxIndex++) {
			List<Len> box = hashmap.get(boxIndex);
			for (int lenIndex = 0; lenIndex < box.size(); lenIndex++) {
				totalFocusingPower += (boxIndex + 1) * (lenIndex + 1) * box.get(lenIndex).getFocalLength();
			}
		}
		
		System.out.println(totalFocusingPower);
	}
	
	private static void executeInHashmap(String s) {
		if (s.lastIndexOf("-") != -1) {
			String lenName = s.substring(0, s.length() - 1);
			List<Len> box = hashmap.get(calculateHashValue(lenName));
			
			for (int i = 0; i < box.size(); i++) {
				if (box.get(i).hasSameName(lenName)) {
					box.remove(i);
				}
			}
			
			return;
		}
		
		int numberStart = s.lastIndexOf("=") + 1;
		int focusLength = stringToInt(s.substring(numberStart));
		String lenName = s.substring(0, numberStart - 1);
		List<Len> box = hashmap.get(calculateHashValue(lenName));
		
		Len len = day15.new Len(lenName, focusLength);
		
		for (int i = 0; i < box.size(); i++) {
			if (box.get(i).hasSameName(lenName)) {
				box.set(i, len);
				return;
			}
		}
		
		box.add(len);
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
	
	private static void initializeHashmap() {
		hashmap = new ArrayList<List<Len>>();
		
		for (int i = 0; i < BOXES_AMOUNT; i++) {
			hashmap.add(new ArrayList<Len>());
		}
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

	private static int stringToInt(String s) {
		try {
			return Integer.parseInt(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
