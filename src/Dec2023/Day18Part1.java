package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day18Part1 {
	
	private static Day18Part1 day18 = new Day18Part1();
	
	private class DigPlanPart {
		
		private int _verticalDirection;
		private int _horizontalDirection;
		private int _amount;
		
		public DigPlanPart(String direction, int amount) {
			
			_verticalDirection = 0;
			_horizontalDirection = 0;
			
			switch (direction) {
			case "U":
				_verticalDirection = -1;
				break;
			case "R":
				_horizontalDirection = 1;
				break;
			case "D": 
				_verticalDirection = 1;
				break;
			case "L":
				_horizontalDirection = -1;
				break;
			}
			
			_amount = amount;
		}
		
		public int getVerticalDirection() {
			return _verticalDirection;
		}
		
		public int getHorizontalDirection() {
			return _horizontalDirection;
		}
		
		public int getAmount() {
			return _amount;
		}
		
	}
	
	private static List<String> terrain;
	private static List<DigPlanPart> digPlan;
	
	public static void run() {
		initializeDigPlan();
		
		int currentLine = -terrainMinLine;
		int currentCol = -terrainMinCol;
		setOnGrid(currentLine, currentCol, "S");
		
		for (DigPlanPart digPlanPart : digPlan) {
			int verticalDirection = digPlanPart.getVerticalDirection();
			int horizontalDirection = digPlanPart.getHorizontalDirection();
			String directionElement = verticalDirection == 0 ? "-" : "|";
			
			int amount = digPlanPart.getAmount();
			
			for (int i = 1; i < amount; i++) {
				setOnGrid(currentLine + verticalDirection * i, currentCol + horizontalDirection * i, directionElement);
			}
			
			currentLine += verticalDirection * amount;
			currentCol += horizontalDirection * amount;
			setOnGrid(currentLine, currentCol, "S");
		}
		
		replaceStartIcons(terrain);
		
		insertInsideMark(terrain, true);
		insertInsideMark(terrain, false);
		
		int sum = 0;
		
		for (String line : terrain) {
			for (int i = 0; i < line.length(); i++) {
				String charAt = Character.toString(line.charAt(i));
				if (!charAt.equals(".") && !charAt.equals("*")) {
					sum++;
				}
			}
		}
		
		System.out.println(sum);
	}
	
	private static void setOnGrid(int line, int col, String element) {
		String previousLine = terrain.get(line);
		
		String newLine = previousLine.substring(0, col) + element + previousLine.substring(col + 1);
		
		terrain.set(line, newLine);
	}
	
	private static void insertInsideMark(List<String> surface, boolean isHorizontal) {
		int stopingIndex1 = isHorizontal ? surface.size() : surface.get(0).length();
		int stopingIndex2 = !isHorizontal ? surface.size() : surface.get(0).length();
		
		List<String> wallStarter = new ArrayList<String>();
		List<String> wallEnder = new ArrayList<String>();
		if (isHorizontal) {
			wallStarter.add("|");
			wallStarter.add("F");
			wallStarter.add("L");
			wallEnder.add("7");
			wallEnder.add("J");
		}
		else {
			wallStarter.add("-");
			wallStarter.add("F");
			wallStarter.add("7");
			wallEnder.add("L");
			wallEnder.add("J");
		}
		
		for (int i = 0; i < stopingIndex1; i++) {
			boolean isInsideTheLoop = false;
			String enteredWallWithChar = "";
			for (int j = 0; j < stopingIndex2; j++) {
				int line = isHorizontal ? i : j;
				int col = !isHorizontal ? i : j;
				String charAt = Character.toString(surface.get(line).charAt(col));
				
				if (isInsideTheLoop && charAt.equals(".")) {
					setOnGrid(line, col, "*");
				}
				else if (isInsideTheLoop && charAt.equals("*")) {
					setOnGrid(line, col, "I");
				}
				else if (charAt.equals(wallStarter.get(0))) {
					isInsideTheLoop = !isInsideTheLoop;
					enteredWallWithChar = "";
				}
				else if (charAt.equals(wallEnder.get(0))) {
					if (enteredWallWithChar.equals(wallStarter.get(1))) {
						isInsideTheLoop = !isInsideTheLoop;
					}
					enteredWallWithChar = "";
				}
				else if (charAt.equals(wallEnder.get(1))) {
					if (enteredWallWithChar.equals(wallStarter.get(2))) {
						isInsideTheLoop = !isInsideTheLoop;
					}
					enteredWallWithChar = "";
				}
				else if (charAt.equals(wallStarter.get(1)) || charAt.equals(wallStarter.get(2))) {
					enteredWallWithChar = charAt;
					isInsideTheLoop = !isInsideTheLoop;
				}
			}
		}
		
	}
	
	private static void replaceStartIcons(List<String> surface) {
		for (int i = 0; i < surface.size(); i++) {
			String line = surface.get(i);
			if (line.contains("S")) {
				for (int j = 0; j < line.length(); j++) {
					if (Character.toString(surface.get(i).charAt(j)).equals("S")) {
						String newLine = surface.get(i).substring(0, j) + getStartIconReplacement(surface, i, j) + surface.get(i).substring(j + 1);
						surface.set(i, newLine);
					}
				}
			}
		}
	}
	
	private static String getStartIconReplacement(List<String> surface, int startLine, int startColumn) {
		int[] lines = { startLine - 1, startLine, startLine + 1, startLine };
		int[] cols = { startColumn, startColumn + 1, startColumn, startColumn - 1 };
		String[][] connectedPipes = { { "|" , "7", "F", "S" }, { "-", "7", "J", "S" }, { "|", "J", "L", "S" }, { "-", "F", "L", "S" } };
		String[][] possiblePipes = { { "|", "J", "L" }, { "-", "F", "L" }, { "|", "F", "7" }, { "-", "7", "J" } };
		
		List<String> selectedPipes = new ArrayList<String>();
		
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] < 0 || lines[i] >= surface.size() || cols[i] < 0 || cols[i] >= surface.get(i).length()) {
				continue;
			}
			
			String charAt = Character.toString(surface.get(lines[i]).charAt(cols[i]));
			
			for (String pipe : connectedPipes[i]) {
				if (charAt.equals(pipe)) {
					for (String selectedPipe : possiblePipes[i]) {
						selectedPipes.add(selectedPipe);
					}
					
					break;
				}
			}
		}
		
		return getMostFrequentEntry(selectedPipes);
	}
	
	private static String getMostFrequentEntry(List<String> list) {
		int max_count = 0;
	    String maxfreq = "";
	     
	    for (int i = 0; i < list.size(); i++){
	       int count = 0;
	       for (int j = 0; j < list.size(); j++){
	          if (list.get(i) == list.get(j)){
	             count++;
	          }
	       }
	      
	       if (count > max_count){
	          max_count = count;
	          maxfreq = list.get(i);
	       }
	    }
	    
	    return maxfreq;
	}	

	private static int terrainMinLine = 0;
	private static int terrainMinCol = 0;
	
	private static void initializeDigPlan() {
		digPlan = new ArrayList<DigPlanPart>();
		
		int terrainMaxLine = 1;
		int terrainMaxCol = 1;
		
		int currentLine = 1;
		int currentCol = 1;
		
		try {
	        File myObj = new File("InputFiles/18.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            String direction = line.substring(0, 1);
	            int amount = stringToInt(line.substring(2, line.lastIndexOf(" ")));
	            
	            if (direction.equals("U")) {
	            	currentLine -= amount;
	            	if (currentLine <= terrainMinLine) {
	            		terrainMinLine = currentLine - 1;
	            	}
	            }
	            else if (direction.equals("D")) {
	            	currentLine += amount;
	            	if (currentLine > terrainMaxLine) {
	            		terrainMaxLine = currentLine;
	            	}
	            }
	            else if (direction.equals("L")) {
	            	currentCol -= amount;
	            	if (currentCol <= terrainMinCol) {
	            		terrainMinCol = currentCol - 1;
	            	}
	            }
	            else if (direction.equals("R")) {
	            	currentCol += amount;
	            	if (currentCol > terrainMaxCol) {
	            		terrainMaxCol = currentCol;
	            	}
	            } 
	            
	            digPlan.add(day18.new DigPlanPart(direction, amount));
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		terrain = new ArrayList<String>();
		for (int i = 0; i < terrainMaxLine - terrainMinLine; i++) {
			String line = "";
			for (int j = 0; j < terrainMaxCol - terrainMinCol; j++) {
				line += ".";
			}
			terrain.add(line);
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
