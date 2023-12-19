package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day18Part2 {
	
	private static Day18Part2 day18 = new Day18Part2();
	
	private class Position implements Comparable<Position> {
		
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

		@Override
		public int compareTo(Position o) {
			return getLine() - o.getLine();
		}
		
	}
	
	private static List<List<Position>> edges;
	
	public static void run() {
		initializeEdges();
		
		int sum = 0;

		int nextPositionsIndex = 1;
		while (edges.size() > 1) {
			System.err.println("a");
			List<Position> positions = edges.get(0);
			
			Position abovePos = positions.get(0);
			Position belowPos = positions.get(1);
			List<Position> nextPositions = edges.get(nextPositionsIndex);
			
			Position aboveNextPos = null;
			Position belowNextPos = null;
			
			boolean foundMatch = false;
			for (int i = 0; i < nextPositions.size(); i++) {
				Position nextPosition = nextPositions.get(i);
				
				if (nextPosition.getLine() == abovePos.getLine()) {
					sum += (belowPos.getLine() - abovePos.getLine()) * (nextPosition.getCol() - abovePos.getCol());
					nextPositions.remove(i);
					i--;
					aboveNextPos = day18.new Position(belowPos.getLine(), nextPosition.getCol());
					foundMatch = true;					
				}
				else if (nextPosition.getLine() == belowPos.getLine()) {
					sum += (belowPos.getLine() - abovePos.getLine()) * (nextPosition.getCol() - belowPos.getCol());
					nextPositions.remove(i);
					belowNextPos = day18.new Position(abovePos.getLine(), nextPosition.getCol());
					foundMatch = true;
					break;
				}
			}
			
			if (foundMatch) {
				if (aboveNextPos != null) {
					nextPositions.add(aboveNextPos);
				}

				if (belowNextPos != null) {
					nextPositions.add(belowNextPos);
				}
				
				positions.remove(0);
				positions.remove(0);
				if (positions.size() == 0) {
					edges.remove(0);
				}
				
				Collections.sort(nextPositions);
				
				nextPositionsIndex = 1;
			}
			else {
				nextPositionsIndex++;
			}
		}
		
		System.out.println(sum);
	}
	
	private static void initializeEdges() {
		edges = new ArrayList<List<Position>>();
		
		int currentLine = 0;
		int currentCol = 0;
		
		try {
	        File myObj = new File("InputFiles/18.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            String direction = line.substring(0, 1);
	            int amount = stringToInt(line.substring(2, line.lastIndexOf(" ")));
	            
	            if (direction.equals("U")) {
	            	currentLine -= amount;
	            }
	            else if (direction.equals("D")) {
	            	currentLine += amount;
	            }
	            else if (direction.equals("L")) {
	            	currentCol -= amount;
	            }
	            else if (direction.equals("R")) {
	            	currentCol += amount;
	            }
	            
	            boolean wasInserted = false;
	            for (int i = 0; i < edges.size(); i++) {
	            	List<Position> colsList = edges.get(i);
	            	if (colsList.get(0).getCol() == currentCol) {
	            		colsList.add(day18.new Position(currentLine, currentCol));
	            		wasInserted = true;
	            	}
	            }
	            
	            if (!wasInserted) {
	            	List<Position> newColsList = new ArrayList<Position>();
	            	newColsList.add(day18.new Position(currentLine, currentCol));
	            	edges.add(newColsList);
	            }
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		Comparator<List<Position>> listComparator = new Comparator<List<Position>>() {

			@Override
			public int compare(List<Position> o1, List<Position> o2) {
				return o1.get(0).getCol() - o2.get(0).getCol();
			}
		};
		
		edges.sort(listComparator);
		
		for (List<Position> positions : edges) {
			Collections.sort(positions);
		}
		
		// DEBUG
		
		List<String> grid = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			String line = "";
			for (int j = 0; j < 7; j++) {
				line += ".";
			}
			grid.add(line);
		}
		
		for (List<Position> a : edges) {
			for (Position b : a) {
				setOnGrid(grid, b.getLine(), b.getCol(), "E");
			}
		}
		
		System.out.println("Grid");
		for (String a : grid) {
			System.out.println(a);
		}
	}
	
	private static void setOnGrid(List<String> grid, int line, int col, String element) {
		String previousLine = grid.get(line);
		
		String newLine = previousLine.substring(0, col) + element + previousLine.substring(col + 1);
		
		grid.set(line, newLine);
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
