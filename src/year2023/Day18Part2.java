package year2023;

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
		
		private long _line;
		private long _col;
		
		public Position(long l, long m) {
			_line = l;
			_col = m;
		}
		
		public long getLine() {
			return _line;
		}
		
		public long getCol() {
			return _col;
		}

		@Override
		public int compareTo(Position o) {
			return (int) (getLine() - o.getLine());
		}
		
	}
	
	private static List<List<Position>> edges;
	
	public static void run() {
		initializeEdges();
		
		long sum = 0;

		int nextPositionsIndex = 1;
		while (edges.size() > 0) {			
			List<Position> positions = edges.get(0);
			if (positions.size() == 0) {
				nextPositionsIndex = 1;
				edges.remove(0);
				continue;
			}
			
			Position abovePos = positions.get(0);
			Position belowPos = positions.get(1);
			
			List<Position> nextPositions = edges.get(nextPositionsIndex);
			
			int aboveNextPosIndex = -1;
			int belowNextPosIndex = -1;
			
			List<Position> insideEdgesPositions = new ArrayList<Position>();
			for (int i = 0; i < nextPositions.size(); i++) {
				Position nextPosition = nextPositions.get(i);
				
				if (nextPosition.getLine() == abovePos.getLine()) {
					aboveNextPosIndex = i;			
				}
				else if (nextPosition.getLine() == belowPos.getLine()) {
					belowNextPosIndex = i;
				}
				else if (nextPosition.getLine() > abovePos.getLine() && nextPosition.getLine() < belowPos.getLine()) {
					insideEdgesPositions.add(nextPosition);
				}
			}
			
			if (aboveNextPosIndex != -1) {
				sum += (belowPos.getLine() - abovePos.getLine() + 1) * (nextPositions.get(aboveNextPosIndex).getCol() - abovePos.getCol());

				long aboveNextPostLine = nextPositions.get(aboveNextPosIndex).getLine();
				long belowNextPostLine = belowNextPosIndex == -1 ? 0 : nextPositions.get(belowNextPosIndex).getLine();
				sum += countSpacesBetweenInsideEdges(insideEdgesPositions, aboveNextPosIndex,
						aboveNextPostLine, belowNextPosIndex, belowNextPostLine);
				
				if (belowNextPosIndex != -1) {
					if (aboveNextPosIndex % 2 == 0 && insideEdgesPositions.size() == 0) {
						sum += nextPositions.get(belowNextPosIndex).getLine() - nextPositions.get(aboveNextPosIndex).getLine() + 1;						
					}
					nextPositions.remove(belowNextPosIndex);
				}
				else {
					nextPositions.add(day18.new Position(belowPos.getLine(), nextPositions.get(0).getCol()));
				}
				
				nextPositions.remove(aboveNextPosIndex);
			}
			else if (belowNextPosIndex != -1) {
				sum += (belowPos.getLine() - abovePos.getLine() + 1) * (nextPositions.get(belowNextPosIndex).getCol() - abovePos.getCol());
				
				long belowNextPostLine = nextPositions.get(belowNextPosIndex).getLine();
				sum += countSpacesBetweenInsideEdges(insideEdgesPositions, aboveNextPosIndex,
						0, belowNextPosIndex, belowNextPostLine);;
				
				nextPositions.remove(belowNextPosIndex);
				nextPositions.add(day18.new Position(abovePos.getLine(), nextPositions.get(0).getCol()));
			}
			else if (insideEdgesPositions.size() != 0) {
				sum += countSpacesBetweenInsideEdges(insideEdgesPositions, -1, 0, -1, 0);
				
				nextPositions.add(day18.new Position(abovePos.getLine(), insideEdgesPositions.get(0).getCol()));
				nextPositions.add(day18.new Position(belowPos.getLine(), insideEdgesPositions.get(0).getCol()));
				
				sum += (belowPos.getLine() - abovePos.getLine() + 1) * (insideEdgesPositions.get(0).getCol() - abovePos.getCol());
			}
			else {
				nextPositionsIndex++;
				continue;
			}
			
			Collections.sort(nextPositions);
			
			positions.remove(0);
			positions.remove(0);
			if (positions.size() == 0) {
				edges.remove(0);
			}
			
			nextPositionsIndex = 1;
		}
		
		System.out.println(sum);
	}
	
	private static long countSpacesBetweenInsideEdges(List<Position> insideEdgesPositions, int aboveNextPosIndex, long aboveNextPostLine, int belowNextPosIndex, long belowNextPostLine) {
		if (insideEdgesPositions.size() == 0) {
			return 0;
		}
		
		long sum = 0;
		

		List<Position> positionsConnectedToParalelNext = new ArrayList<Position>();
		
		if (aboveNextPosIndex != -1 && aboveNextPosIndex % 2 == 0) {
			sum += insideEdgesPositions.get(0).getLine() - aboveNextPostLine;
			positionsConnectedToParalelNext.add(insideEdgesPositions.get(0));
			insideEdgesPositions.remove(0);
		}
		
		if (belowNextPosIndex != -1 && belowNextPosIndex % 2 == 1) {
			sum += belowNextPostLine - insideEdgesPositions.get(insideEdgesPositions.size() - 1).getLine();
			positionsConnectedToParalelNext.add(insideEdgesPositions.get(insideEdgesPositions.size() - 1));
			insideEdgesPositions.remove(insideEdgesPositions.size() - 1);
		}
		
		for (int i = 0; i < insideEdgesPositions.size(); i += 2) {
			sum += insideEdgesPositions.get(i + 1).getLine() - insideEdgesPositions.get(i).getLine() - 1;
		}
		
		if (positionsConnectedToParalelNext.size() > 0) {
			insideEdgesPositions.addAll(positionsConnectedToParalelNext);
		}
		
		return sum;
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
	            
	            int indexOfCardinal = line.lastIndexOf("#");
	            
	            int amount = hexadecimalToInt(line.substring(indexOfCardinal + 1, indexOfCardinal + 6));
	            String direction = line.substring(indexOfCardinal + 6, indexOfCardinal + 7);
	            
	            if (direction.equals("3")) {
	            	currentLine -= amount;
	            }
	            else if (direction.equals("1")) {
	            	currentLine += amount;
	            }
	            else if (direction.equals("2")) {
	            	currentCol -= amount;
	            }
	            else if (direction.equals("0")) {
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
				return (int) (o1.get(0).getCol() - o2.get(0).getCol());
			}
		};
		
		edges.sort(listComparator);
		
		for (List<Position> positions : edges) {
			Collections.sort(positions);
		}
	}

	private static int hexadecimalToInt(String s) {
		try {
			return Integer.parseInt(s, 16);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
