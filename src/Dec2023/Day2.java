package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
	
	private static int RED_LIMIT = 12;
	private static int GREEN_LIMIT = 13;
	private static int BLUE_LIMIT = 14;
	
	public static void run() {
		solvePart1();
		solvePart2();
	}
	
	private static void solvePart1() {
		int sum = 0;
		
		try {
	        File myObj = new File("InputFiles/2.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          sum += getGameValue(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		System.out.println(sum);		
	}
	
	// Game value = game id if possible and 0 if not possible
	private static int getGameValue(String game) {
		String numberComposition = "";
		int numberComposed = 0;
		
		for (int i = game.lastIndexOf(":") + 1; i < game.length(); i++) {
			String charAt = Character.toString(game.charAt(i));
			
			if (charAt.equals(" ")) {
				numberComposed = stringToInteger(numberComposition);
			}
			else if (charAt.equals("r")) {
				i += "red, ".length() - 2;
				numberComposition = "";
				if (numberComposed > RED_LIMIT) {
					return 0;
				}
			}
			else if (charAt.equals("b")) {
				i += "blue, ".length() - 2;
				numberComposition = "";
				if (numberComposed > BLUE_LIMIT) {
					return 0;
				}
			}
			else if (charAt.equals("g")) {
				i += "green, ".length() - 2;
				numberComposition = "";
				if (numberComposed > GREEN_LIMIT) {
					return 0;
				}
			}
			else {
				int number = stringToInteger(charAt);
				if (number != -1) {
					numberComposition += number;
				}
			}
		}
		
		return getGameId(game);
	}
	
	private static int getGameId(String game) {
		int lastIndexOfId = game.lastIndexOf(":");
		
		String gameIdString = game.substring("Game ".length(), lastIndexOfId);
		return stringToInteger(gameIdString);		
	}
	
	private static void solvePart2() {
		int sum = 0;
		
		try {
	        File myObj = new File("2.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          sum += getGamePower(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		System.out.println(sum);		
	}
	
	private static int getGamePower(String game) {
		int redMinBalls = 0;
		int blueMinBalls = 0;
		int greenMinBalls = 0;
		
		String numberComposition = "";
		int numberComposed = 0;
		
		for (int i = game.lastIndexOf(":") + 1; i < game.length(); i++) {
			String charAt = Character.toString(game.charAt(i));
			
			if (charAt.equals(" ")) {
				numberComposed = stringToInteger(numberComposition);
			}
			else if (charAt.equals("r")) {
				i += "red, ".length() - 2;
				numberComposition = "";
				if (numberComposed > redMinBalls) {
					redMinBalls = numberComposed;
				}
			}
			else if (charAt.equals("b")) {
				i += "blue, ".length() - 2;
				numberComposition = "";
				if (numberComposed > blueMinBalls) {
					blueMinBalls = numberComposed;
				}
			}
			else if (charAt.equals("g")) {
				i += "green, ".length() - 2;
				numberComposition = "";
				if (numberComposed > greenMinBalls) {
					greenMinBalls = numberComposed;
				}
			}
			else {
				int number = stringToInteger(charAt);
				if (number != -1) {
					numberComposition += number;
				}
			}
		}
		
		return redMinBalls * blueMinBalls * greenMinBalls;
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
