package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10Part1 {
	
	private static Day10Part1 day10 = new Day10Part1();
	
	private class Position {
		
		private int _line;
		private int _column;
		
		public Position(int line, int column) {
			_line = line;
			_column = column;
		}
		
		private int getLine() {
			return _line;
		}
		
		private int getColumn() {
			return _column;
		}
		
		@Override
		public boolean equals(Object o) {
			Position oPos = (Position)o;
			
			return getLine() == oPos.getLine() && getColumn() == oPos.getColumn();
		}
		
	}
	
	private static List<String> surface;
	private static int startLine;
	private static int startColumn;

	public static void run() {
		initializeSurface();

		List<Position> startingPositions = getStartingPositions();
		
		Position previousFirstPosition = day10.new Position(startLine, startColumn);
		Position currentFirstPosition = startingPositions.get(0);
		
		Position previousSecondPosition = day10.new Position(startLine, startColumn);
		Position currentSecondPosition = startingPositions.get(1);		
		
		int iterationCount = 1;
		while (!currentFirstPosition.equals(currentSecondPosition) && !previousFirstPosition.equals(currentSecondPosition)) {
			int line = currentFirstPosition.getLine();
			int col = currentFirstPosition.getColumn();
			
			String charAt = Character.toString(surface.get(line).charAt(col));
			
			currentFirstPosition = getNextPosition(previousFirstPosition, charAt, currentFirstPosition);
			
			previousFirstPosition = day10.new Position(line, col);

			line = currentSecondPosition.getLine();
			col = currentSecondPosition.getColumn();
			
			charAt = Character.toString(surface.get(line).charAt(col));
			
			currentSecondPosition = getNextPosition(previousSecondPosition, charAt, currentSecondPosition);
			
			previousSecondPosition = day10.new Position(line, col);
			
			iterationCount++;
		}
		
		System.out.println(iterationCount);
	}
	
	private static List<Position> getStartingPositions() {
		List<Position> positions = new ArrayList<Position>();
		
		int line = startLine - 1;
		int col = startColumn;
		
		String charAt = Character.toString(surface.get(line).charAt(col));
		if (charAt.equals("|") || charAt.equals("7") || charAt.equals("F")) {
			positions.add(day10.new Position(line, col));
		}
		
		line = startLine;
		col = startColumn + 1;
		charAt = Character.toString(surface.get(line).charAt(col));
		
		if (charAt.equals("-") || charAt.equals("7") || charAt.equals("J")) {
			positions.add(day10.new Position(line, col));
			if (positions.size() == 2) {
				return positions;
			}
		}
		
		line = startLine + 1;
		col = startColumn;
		charAt = Character.toString(surface.get(line).charAt(col));
		
		if (charAt.equals("|") || charAt.equals("J") || charAt.equals("L")) {
			positions.add(day10.new Position(line, col));
			if (positions.size() == 2) {
				return positions;
			}
		}
		
		line = startLine;
		col = startColumn - 1;
		charAt = Character.toString(surface.get(line).charAt(col));
		
		if (charAt.equals("-") || charAt.equals("F") || charAt.equals("L")) {
			positions.add(day10.new Position(line, col));
			if (positions.size() == 2) {
				return positions;
			}
		}
		
		return positions;
	}
	
	private static Position getNextPosition(Position posFrom, String pipeType, Position pipePos) {
		int nextPostLine = pipePos.getLine();
		int nextPosCol = pipePos.getColumn();
		
		int lineChange = 0;
		int colChange = 0;
		if (pipeType.equals("-")) {
			nextPosCol = posFrom.getColumn() < pipePos.getColumn() ? pipePos.getColumn() + 1 : pipePos.getColumn() - 1;
		}
		else if (pipeType.equals("|")) {
			nextPostLine = posFrom.getLine() < pipePos.getLine() ? pipePos.getLine() + 1 : pipePos.getLine() - 1;
		}
		else if (pipeType.equals("F")) {
			lineChange = 1;
			colChange = 1;
		}
		else if (pipeType.equals("7")) {
			lineChange = 1;
			colChange = -1;
		}
		else if (pipeType.equals("J")) {
			lineChange = -1;
			colChange = -1;
		}
		else if (pipeType.equals("L")) {
			lineChange = -1;
			colChange = 1;		
		}
		

		if (posFrom.getLine() == pipePos.getLine()) {
			nextPostLine = pipePos.getLine() + lineChange;
		}
		else {
			nextPosCol = pipePos.getColumn() + colChange;
		}	
		
		return day10.new Position(nextPostLine, nextPosCol);
	}
	
	private static void initializeSurface() {
		surface = new ArrayList<String>();
		
		try {
	        File myObj = new File("InputFiles/10.txt");
	        Scanner myReader = new Scanner(myObj);
	        int lineCounter = 0;
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            surface.add(line);
	            
	            if (line.contains("S")) {
	            	startLine = lineCounter;
	            	startColumn = line.lastIndexOf("S");
	            }
	            
	            lineCounter++;
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
}
