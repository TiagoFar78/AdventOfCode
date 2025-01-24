package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day21Part1 {
	
	private static final int STEPS = 64;
	
	private static Day21Part1 day21 = new Day21Part1();
	
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
		List<Position> positions = new ArrayList<Position>();
		
		int startLine = startPos.getLine();
		int startCol = startPos.getCol();
		
		int[] lines = { startLine - 1, startLine, startLine + 1, startLine };
		int[] cols = { startCol, startCol + 1, startCol, startCol - 1 };
		
		for (int i = 0; i < lines.length; i++) {
			int line = lines[i];
			int col = cols[i];
			
			if (line < 0 || line > grid.size() || col < 0 || col > grid.get(0).length()) {
				continue;
			}
			
			String charAt = Character.toString(grid.get(line).charAt(col));
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

}
