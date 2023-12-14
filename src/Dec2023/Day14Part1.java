package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day14Part1 {
	
	private static List<String> dish;
	
	public static void run() {
		initializeDish();
		
		int loadWeight = 0;
		
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
					
					loadWeight += dish.size() - (lineAux + 1);
				}
			}
		}
		
		System.out.println(loadWeight);
	}
	
	private static void setOnDish(int line, int col, String value) {
		String previousLine = dish.get(line);
		String newLine = previousLine.substring(0, col) + value + previousLine.substring(col + 1);
		
		dish.set(line, newLine);
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
