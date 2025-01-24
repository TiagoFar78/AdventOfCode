package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day04 {
	
	public static void run() {
		solvePart1();
		
		solvePart2();
	}
	
	public static void solvePart1() {
		int sum = 0;

		try {
	        File myObj = new File("InputFiles/4.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          sum += getCardValue(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		System.out.println(sum);
	}
	
	private static int getCardValue(String card) {
		int value = 0;
		
		List<Integer> winningNumbers = new ArrayList<Integer>();
		
		String numberComposition = "";
		
		int startingIndex;
		for (int i = card.lastIndexOf(":") + 2; ; i++) {
			String charAt = Character.toString(card.charAt(i));
			if (charAt.equals("|")) {
				startingIndex = i + 2;
				break;
			}
			
			if (charAt.equals(" ")) {
				int winningNumber = stringToInteger(numberComposition);
				if (winningNumber != -1) {
					winningNumbers.add(winningNumber);
					numberComposition = "";
				}
			}
			
			int number = stringToInteger(charAt);
			if (number != -1) {
				numberComposition += charAt;
			}
		}
		
		for (int i = startingIndex; i < card.length(); i++) {
			String charAt = Character.toString(card.charAt(i));
			
			int number = stringToInteger(charAt);
			if (number != -1) {
				numberComposition += charAt;
			}
			
			if (charAt.equals(" ") || i == card.length() -1) {
				int hadNumber = stringToInteger(numberComposition);

				if (hadNumber != -1) {
					if (winningNumbers.contains(hadNumber)) {
						value = value == 0 ? 1 : value * 2;
					}
					
					numberComposition = "";
				}
			}
		}
		
		return value;
	}
	
	private static List<Integer> cardsAmount = new ArrayList<Integer>();
	
	public static void solvePart2() {
		int sum = 0;

		try {
	        File myObj = new File("InputFiles/4.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        int index = 0;
	        while (myReader.hasNextLine()) {	        	
	        	updateListDirectly(index, 1);
	        	
	            String line = myReader.nextLine();
	            updateCardsAmount(line, index);
	            sum += cardsAmount.get(index);
	          
	            index++;
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

		System.out.println(sum);
	}
	
	private static void updateListDirectly(int index, int increaseAmount) {		
		if (cardsAmount.size() <= index) {
			int size = cardsAmount.size();
			for (int i = size; i <= index; i++) {
	    		cardsAmount.add(increaseAmount);
			}
    	}
    	else {
    		cardsAmount.set(index, cardsAmount.get(index) + increaseAmount);
    	}
	}
	
	private static void updateCardsAmount(String card, int index) {
		int indexIncreased = 1;
		
		List<Integer> winningNumbers = new ArrayList<Integer>();
		
		String numberComposition = "";
		
		int startingIndex;
		for (int i = card.lastIndexOf(":") + 2; ; i++) {
			String charAt = Character.toString(card.charAt(i));
			if (charAt.equals("|")) {
				startingIndex = i + 2;
				break;
			}
			
			if (charAt.equals(" ")) {
				int winningNumber = stringToInteger(numberComposition);
				if (winningNumber != -1) {
					winningNumbers.add(winningNumber);
					numberComposition = "";
				}
			}
			
			int number = stringToInteger(charAt);
			if (number != -1) {
				numberComposition += charAt;
			}
		}
		
		for (int i = startingIndex; i < card.length(); i++) {
			String charAt = Character.toString(card.charAt(i));
			
			int number = stringToInteger(charAt);
			if (number != -1) {
				numberComposition += charAt;
			}
			
			if (charAt.equals(" ") || i == card.length() -1) {
				int hadNumber = stringToInteger(numberComposition);

				if (hadNumber != -1) {
					if (winningNumbers.contains(hadNumber)) {
						updateListDirectly(index + indexIncreased, cardsAmount.get(index));
						indexIncreased++;
					}
					
					numberComposition = "";
				}
			}
		}
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
