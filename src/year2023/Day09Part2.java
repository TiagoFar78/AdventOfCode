package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day09Part2 {
	
	private static List<List<Integer>> histories;
	
	public static void run() {
		initializeHistories();
		
		int sum = 0;
		
		for (List<Integer> history : histories) {
			sum += getHistoryPreviousValue(history);
		}
		
		System.out.println(sum);
	}
	
	public static int getHistoryPreviousValue(List<Integer> history) {
		List<List<Integer>> differenceSequences = new ArrayList<List<Integer>>();
		differenceSequences.add(history);
		
		boolean areDifferencesAllZero = false;
		while (!areDifferencesAllZero) {
			List<Integer> newDifferenceSequence = new ArrayList<Integer>();
			
			List<Integer> sequenceAbove = differenceSequences.get(differenceSequences.size() - 1);
			areDifferencesAllZero = true;
			for (int i = 1; i < sequenceAbove.size(); i++) {
				int diff = sequenceAbove.get(i) - sequenceAbove.get(i - 1);
				
				newDifferenceSequence.add(diff);
				
				if (diff != 0) {
					areDifferencesAllZero = false;
				}
			}
			
			differenceSequences.add(newDifferenceSequence);
			
		}
		
		differenceSequences.get(differenceSequences.size() - 1).add(0);
		for (int i = differenceSequences.size() - 2; i >= 0; i--) {
			List<Integer> differenceSequece = differenceSequences.get(i);
			List<Integer> differenceSequeceBellow = differenceSequences.get(i + 1);
			
			int newDifference = differenceSequece.get(0) - differenceSequeceBellow.get(differenceSequeceBellow.size() - 1);
			
			differenceSequece.add(newDifference);
		}
		
		List<Integer> firstDifferenceSequece = differenceSequences.get(0);
		return firstDifferenceSequece.get(firstDifferenceSequece.size() - 1);
	}
	
	public static void initializeHistories() {
		histories = new ArrayList<List<Integer>>();

		try {
	        File myObj = new File("InputFiles/9.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            String[] parts = line.split(" ");
	            
	            List<Integer> intsLine = new ArrayList<Integer>();
	            for (String part : parts) {
	            	intsLine.add(stringToInt(part));
	            }
	            
	            histories.add(intsLine);
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
