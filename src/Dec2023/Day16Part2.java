package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day16Part2 {
	
	private static List<String> mainGrid;
	private static List<String> grid;
	private static List<Integer> energinedTilesLine;
	private static List<Integer> energizedTilesCol;
	private static List<String> trail_register;
	
	public static void run() {
		initializeMainGrid();
		
		int lines = mainGrid.size();
		int cols = mainGrid.get(0).length();
		
		int maxEnergizedTiles = 0;

		for (int i = 0; i < lines; i++) {
			restoreVariables();
			
			beamFlow(1, 0, i, 0);
			
			fillEnergizedTiles();
			
			int energizedTiles = countEnergizedTiles();
			
			if (maxEnergizedTiles < energizedTiles) {
				maxEnergizedTiles = energizedTiles;
			}
		}

		for (int i = 0; i < lines; i++) {
			restoreVariables();
			
			beamFlow(-1, 0, i, cols - 1);
			
			fillEnergizedTiles();
			
			int energizedTiles = countEnergizedTiles();
			
			if (maxEnergizedTiles < energizedTiles) {
				maxEnergizedTiles = energizedTiles;
			}
		}

		for (int i = 0; i < lines; i++) {
			restoreVariables();
			
			beamFlow(0, 1, 0, i);
			
			fillEnergizedTiles();
			
			int energizedTiles = countEnergizedTiles();
			
			if (maxEnergizedTiles < energizedTiles) {
				maxEnergizedTiles = energizedTiles;
			}
		}

		for (int i = 0; i < lines; i++) {
			restoreVariables();
			
			beamFlow(0, -1, lines - 1, i);
			
			fillEnergizedTiles();
			
			int energizedTiles = countEnergizedTiles();
			
			if (maxEnergizedTiles < energizedTiles) {
				maxEnergizedTiles = energizedTiles;
			}
		}
		
		System.out.println(maxEnergizedTiles);
	}
	
	private static void beamFlow(int horizontalDirectionFrom, int verticalDirectionFrom, int line, int col) {
		if (line < 0 || line >= grid.size() || col < 0 || col >= grid.size()) {
			return;
		}
		
		String trail = getTrail(horizontalDirectionFrom, verticalDirectionFrom);
		
		if (isRepeatingPath(line, col, trail)) {
			return;
		}
		
		energinedTilesLine.add(line);
		energizedTilesCol.add(col);
		trail_register.add(trail);
		String charAt = Character.toString(grid.get(line).charAt(col));
		
		if (charAt.equals(".")) {			
			beamFlow(horizontalDirectionFrom, verticalDirectionFrom, line + verticalDirectionFrom, col + horizontalDirectionFrom);
		}
		else if (charAt.equals("-")) {
			if (verticalDirectionFrom == 0) {
				beamFlow(horizontalDirectionFrom, verticalDirectionFrom, line, col + horizontalDirectionFrom);
			}
			else {
				beamFlow(-1, 0, line, col - 1);
				beamFlow(1, 0, line, col + 1);
			}
		}
		else if (charAt.equals("|")) {
			if (horizontalDirectionFrom == 0) {
				beamFlow(horizontalDirectionFrom, verticalDirectionFrom, line + verticalDirectionFrom, col);
			}
			else {
				beamFlow(0, -1, line - 1, col);
				beamFlow(0, 1, line + 1, col);
			}
		}
		else if (charAt.equals("/")) {
			beamFlow(-verticalDirectionFrom, -horizontalDirectionFrom, line - horizontalDirectionFrom, col - verticalDirectionFrom);
		}
		else if (charAt.equals("\\")) {
			beamFlow(verticalDirectionFrom, horizontalDirectionFrom, line + horizontalDirectionFrom, col + verticalDirectionFrom);
		}
	}
	
	private static String getTrail(int horizontalDirectionFrom, int verticalDirectionFrom) {
		if (horizontalDirectionFrom == 0) {
			if (verticalDirectionFrom == 1) {
				return "v";
			}
			else {
				return "^";
			}
		}
		else if (horizontalDirectionFrom == 1) {
			return ">";
		}
		else {
			return "<";
		}
	}
	
	private static boolean isRepeatingPath(int line, int col, String trail) {
		for (int i = 0; i < energinedTilesLine.size(); i++) {
			if (energinedTilesLine.get(i) == line && energizedTilesCol.get(i) == col && trail_register.get(i).equals(trail)) {
				return true;
			}
		}
		
		return false;		
	}
	
	private static void fillEnergizedTiles() {
		for (int i = 0; i < energinedTilesLine.size(); i++) {
			setOnGrid(energinedTilesLine.get(i), energizedTilesCol.get(i), "#");
		}
	}
	
	private static void setOnGrid(int line, int col, String item) {
		String previousLine = grid.get(line);
		
		String newLine = previousLine.substring(0, col) + item + previousLine.substring(col + 1);
		
		grid.set(line, newLine);
	}
	
	private static int countEnergizedTiles() {
		int energizedTiles = 0;
		
		for (String line : grid) {
			for (int i = 0; i < line.length(); i++) {
				String charAt = Character.toString(line.charAt(i));
				if (charAt.equals("#")) {
					energizedTiles++;
				}
			}
		}
		
		return energizedTiles;
	}
	
	private static void restoreVariables() {
		grid = cloneList(mainGrid);
		energinedTilesLine = new ArrayList<Integer>();
		energizedTilesCol = new ArrayList<Integer>();
		trail_register = new ArrayList<String>();
	}
	
	private static List<String> cloneList(List<String> list) {
		List<String> clonedList = new ArrayList<String>();
		
		for (String element : list) {
			clonedList.add(element.substring(0));
		}
		
		return clonedList;
	}
	
	private static void initializeMainGrid() {
		mainGrid = new ArrayList<String>();
		
		try {
	        File myObj = new File("InputFiles/16.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            mainGrid.add(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

}
