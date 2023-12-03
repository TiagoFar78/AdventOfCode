package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {
	
	public static void run() {
		int sum = 0;
		
		try {
	        File myObj = new File("InputFiles/1.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          sum += getLineValue(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		System.out.println(sum);
	}
	
	private static int getLineValue(String line) {
		String auxLine = "";
		
		for (int i = 0; i < line.length(); i++) {
			int value;
			try {
				value = Integer.parseInt(line.substring(i, i + 1));
		    } catch(Exception e) {
		    	value = getNumberExtense(line, i, false);
		        if (value == -1) {
		        	 continue;
		        }
		    }
			
			auxLine += Integer.toString(value);
			break;
		}
		
		for (int i = line.length() - 1; i >= 0; i--) {
			int value;
			try {
				value = Integer.parseInt(line.substring(i, i + 1));
		    } catch(Exception e) { 
		    	value = getNumberExtense(line, i, true);
		        if (value == -1) {
		        	 continue;
		        }
		    }
			
			auxLine += Integer.toString(value); 
			break;
		}
		
		int returnCode = 0;
		try {
			returnCode = Integer.parseInt(auxLine);
	    } catch(NumberFormatException e) {
	         e.printStackTrace();
	    } catch(NullPointerException e) {
	         e.printStackTrace();
	    }
		
		return returnCode;
	}
	
	private static String[] numbers =  { "one" , "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	
	private static int getNumberExtense(String line, int index, boolean walkBackwards) {
		if (index < 0) {
			return -1;
		}
		
		if (index >= line.length()) {
			return -1;
		}
		
		for (int i = 0; i < numbers.length; i++) {
			if (!walkBackwards ? isMatch(line, index, numbers[i]) : isMatchBackward(line, index, numbers[i])) {
				return i + 1;
			}
		}
		
		return -1;		
	}
	
	private static boolean isMatch(String line, int index, String word) {
		if (index < 0) {
			return false;
		}
		
		if (index + word.length() - 1 >= line.length()) {
			return false;
		}
		
		int j = 0;
		for (int i = index; i < index + word.length(); i++) {
			if (line.charAt(i) != word.charAt(j)) {
				return false;
			}
			
			j++;
		}
		
		return true;
	}
	
	private static boolean isMatchBackward(String line, int index, String word) {
		if (index - word.length() + 1 < 0) {
			return false;
		}
		
		return isMatch(line, index - word.length() + 1, word);
	}

}
