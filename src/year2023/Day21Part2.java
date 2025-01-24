package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day21Part2 {
	
	private static final int STEPS = 100;
	
	private static Day21Part2 day21 = new Day21Part2();
	
	private class Position {
		
		private int _line;
		private int _col;
		
		public Position(int line, int col) {
			_line = line;
			_col = col;
		}
		
		public int getLine() {
			return _line;
		}
		
		public int getCol() {
			return _col;
		}
		
	}
	
	private static List<String> garden;
	
	private static List<Position> reachedGardenPositions;
	
	private static List<Position> removeDuplicatePositions(List<Position> positions) {
		List<Position> newList = new ArrayList<Position>();
		
		for (int i = 0; i < positions.size(); i++) {
			Position position = positions.get(i); 
			
			boolean alreadyExists = false;
			for (int j = 0; j < newList.size(); j++) {
				Position newPosition = newList.get(j);
				
				if (position.getLine() == newPosition.getLine() && position.getCol() == newPosition.getCol()) {
					alreadyExists = true;
					break;
				}
			}
			
			if (!alreadyExists) {
				newList.add(position);
			}
		}
		
		return newList;
	}
	
	private static Position getStartingPosition(List<String> grid) {
		for (int i = 0; i < grid.size(); i++) {
			if (grid.get(i).contains("S")) {
				return day21.new Position(i, grid.get(i).lastIndexOf("S"));
			}
		}
		
		return null;
	}
	
	private static List<Position> getPositionsAfterStep(Position startPos, List<String> grid) {
		int maxLines = grid.size();
		int maxCols = grid.get(0).length();
		
		List<Position> positions = new ArrayList<Position>();
		
		int startLine = startPos.getLine();
		int startCol = startPos.getCol();
		
		int[] lines = { startLine - 1, startLine, startLine + 1, startLine };
		int[] cols = { startCol, startCol + 1, startCol, startCol - 1 };
		
		for (int i = 0; i < lines.length; i++) {
			int line = lines[i];
			int col = cols[i];
			
			int gridLine = line;
			int gridCol = col;
			
			if (line < 0) {
				gridLine = (line % maxLines + maxLines) % maxLines;
			}
			else if (line >= maxLines) {
				gridLine = line % maxLines;
			}
			
			if (col < 0) {
				gridCol = (col % maxCols + maxCols) % maxCols;
			}
			else if (col >= maxCols) {
				gridCol = col % maxCols;
			}
			
			String charAt = Character.toString(grid.get(gridLine).charAt(gridCol));
			if (!charAt.equals("#")) {
				positions.add(day21.new Position(line, col));
			}
		}
		
		return positions;
	}
	
	public static void run() {
		initializeGarden();
		
		reachedGardenPositions.add(getStartingPosition(garden));
		
		for (int i = 0; i < STEPS; i++) {
			List<Position> newPositions = new ArrayList<Position>();
			for (Position pos : reachedGardenPositions) {
				newPositions.addAll(getPositionsAfterStep(pos, garden));
			}
			
			reachedGardenPositions = removeDuplicatePositions(newPositions);
			
			System.out.println("Para o step " + i + " ja temos " + reachedGardenPositions.size() + " posicoes quando deviam haver " + (i + 2)*(i + 2) + ", logo houve " + ((i + 2)*(i + 2) - reachedGardenPositions.size()) + " erros");
			
//			printGarden();
		}
		
		System.out.println(reachedGardenPositions.size());
	}
	
	private static void initializeGarden() {
		garden = new ArrayList<String>();
		
		reachedGardenPositions = new ArrayList<Position>();
		
		try {
	        File myObj = new File("InputFiles/21.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            garden.add(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	// DEBUG
	
	/*private static void printGarden() {
		List<String> grid = cloneList(garden); 
		
		for (Position pos : reachedGardenPositions) {
			setOnGrid(grid, pos.getLine(), pos.getCol(), "O");
		}
		
		System.out.println("Garden");
		for (String a : grid) {
			System.out.println(a);
		}
		System.out.println();		
	}
	
	private static void setOnGrid(List<String> grid, int line, int col, String element) {
		String prevLine = grid.get(line);
		
		String newLine = prevLine.substring(0, col) + element + prevLine.substring(col + 1);
		
		grid.set(line, newLine);
	}
	
	private static List<String> cloneList(List<String> list) {
		List<String> clonedList = new ArrayList<String>();
		
		for (String element : list) {
			clonedList.add(element.substring(0));
		}
		
		return clonedList;
	}*/

}
