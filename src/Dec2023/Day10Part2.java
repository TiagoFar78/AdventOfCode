package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10Part2 {
	
	private static Day10Part2 day10 = new Day10Part2();
	
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
		
		List<String> cleanSurface = initializeCleanSurface();

		List<Position> startingPositions = getStartingPositions();
		
		Position previousFirstPosition = day10.new Position(startLine, startColumn);
		Position currentFirstPosition = startingPositions.get(0);
		
		Position previousSecondPosition = day10.new Position(startLine, startColumn);
		Position currentSecondPosition = startingPositions.get(1);
		
		leaveTrailBehind(previousFirstPosition, cleanSurface, "S");
		
		while (!currentFirstPosition.equals(currentSecondPosition) && !previousFirstPosition.equals(currentSecondPosition)) {
			int line = currentFirstPosition.getLine();
			int col = currentFirstPosition.getColumn();
			
			String charAt = Character.toString(surface.get(line).charAt(col));
			
			currentFirstPosition = getNextPosition(previousFirstPosition, charAt, currentFirstPosition);
			
			previousFirstPosition = day10.new Position(line, col);
			
			leaveTrailBehind(previousFirstPosition, cleanSurface, charAt);

			line = currentSecondPosition.getLine();
			col = currentSecondPosition.getColumn();
			
			charAt = Character.toString(surface.get(line).charAt(col));
			
			currentSecondPosition = getNextPosition(previousSecondPosition, charAt, currentSecondPosition);
			
			previousSecondPosition = day10.new Position(line, col);
			
			leaveTrailBehind(previousSecondPosition, cleanSurface, charAt);
		}
		
		int currentLine = currentFirstPosition.getLine();
		int currentCol = currentFirstPosition.getColumn();
		String charAt = Character.toString(surface.get(currentLine).charAt(currentCol));
		leaveTrailBehind(currentFirstPosition, cleanSurface, charAt);
		
//		System.out.println("Surface");
//		for (String line : surface) {
//			System.out.println(line);
//		}
		
		replaceStartIcon(cleanSurface);
		
		int sum = 0;
		
		for (int line = 0; line < cleanSurface.size(); line++) {
			boolean isInsideTheLoop = false;
			String enteredWallWithChar = "";
			for (int col = 0; col < cleanSurface.get(line).length(); col++) {
				charAt = Character.toString(cleanSurface.get(line).charAt(col));
				
				if (isInsideTheLoop && charAt.equals(".")) {
					leaveTrailBehind(day10.new Position(line, col), cleanSurface, "I");
				}
				else if (charAt.equals("|")) {
					isInsideTheLoop = !isInsideTheLoop;
					enteredWallWithChar = "";
				}
				else if (charAt.equals("7")) {
					if (enteredWallWithChar.equals("F")) {
						isInsideTheLoop = !isInsideTheLoop;
					}
//					else {
//						isInsideTheLoop = true;
//					}
//					enteredWallWithChar = "";
				}
				else if (charAt.equals("J")) {
					if (enteredWallWithChar.equals("L")) {
						isInsideTheLoop = !isInsideTheLoop;
					}
//					else {
//						isInsideTheLoop = false;
//					}
					enteredWallWithChar = "";
				}
				else if (charAt.equals("L") || charAt.equals("F")) {
					enteredWallWithChar = charAt;
					isInsideTheLoop = !isInsideTheLoop;
				}
			}
		}
		
		for (int col = 0; col < cleanSurface.get(0).length(); col++) {
			boolean isInsideTheLoop = false;
			String enteredWallWithChar = "";
			for (int line = 0; line < cleanSurface.size(); line++) {
				charAt = Character.toString(cleanSurface.get(line).charAt(col));
				
				if (!isInsideTheLoop && charAt.equals("I")) {
					leaveTrailBehind(day10.new Position(line, col), cleanSurface, ".");
				}
				
				if (isInsideTheLoop && charAt.equals(".")) {
					leaveTrailBehind(day10.new Position(line, col), cleanSurface, "I");
				}
				else if (charAt.equals("-")) {
					isInsideTheLoop = !isInsideTheLoop;
					enteredWallWithChar = "";
				}
				else if (charAt.equals("L")) {
					if (enteredWallWithChar.equals("F")) {
						isInsideTheLoop = !isInsideTheLoop;
					}
					enteredWallWithChar = "";
				}
				else if (charAt.equals("J")) {
					if (enteredWallWithChar.equals("7")) {
						isInsideTheLoop = !isInsideTheLoop;
					}
					enteredWallWithChar = "";
				}
				else if (charAt.equals("7") || charAt.equals("F")) {
					enteredWallWithChar = charAt;
					isInsideTheLoop = !isInsideTheLoop;
				}
			}
		}
		
		for (String line : cleanSurface) {
			for (int i = 0; i < line.length(); i++) {
				charAt = Character.toString(line.charAt(i));
				if (charAt.equals("I")) {
					sum++;
				}
			}
		}
		
		System.out.println("Clean Surface");
		for (String line : cleanSurface) {
			System.out.println(line);
		}
		
		System.out.println(sum);
	}
	
	private static void replaceStartIcon(List<String> surface) {
		for (int i = 0; i < surface.size(); i++) {
			String line = surface.get(i);
			if (line.contains("S")) {
				for (int j = 0; j < line.length(); j++) {
					if (Character.toString(line.charAt(j)).equals("S")) {
						String newLine = line.substring(0, j) + "-" + line.substring(j + 1);
						surface.set(i, newLine);
						return;
					}
				}
			}
		}
	}
	
	private static boolean canCloseWall(String wallOpenedWith, String closingChar) {
		if (wallOpenedWith.equals("F")) {
			return closingChar.equals("7") || closingChar.equals("|");
		}
		
		return closingChar.equals("J") || closingChar.equals("|");
	}
	
	
	private static boolean areConnected(String pipe1, String pipe2, boolean isHorizontally) {
		if (isHorizontally) {
			return !(pipe1.equals("J") || pipe1.equals("7") || pipe1.equals("|")) &&
					(pipe2.equals("7") || pipe2.equals("J") || pipe2.equals("-") || pipe2.equals("S"));
		}
		
		return !(pipe1.equals("J") || pipe1.equals("L") || pipe1.equals("-")) &&
				(pipe2.equals("J") || pipe1.equals("L") || pipe1.equals("|") || pipe2.equals("S"));
	}
	
	private static List<String> initializeCleanSurface() {
		List<String> cleanSurface = new ArrayList<String>();
		
		for (int i = 0; i < surface.size(); i++) {
			String cleanLine = "";
			for (int j = 0; j < surface.get(i).length(); j++) {
				cleanLine += ".";
			}
			
			cleanSurface.add(cleanLine);
		}
		
		return cleanSurface;
	}
	
	private static void leaveTrailBehind(Position pos, List<String> surface, String trail) {
		String line = surface.get(pos.getLine());
		
		String newLine = line.substring(0, pos.getColumn()) + trail + line.substring(pos.getColumn() + 1);
		
		surface.set(pos.getLine(), newLine);
	}
	
	
	private static List<Position> getStartingPositions() {
		List<Position> positions = new ArrayList<Position>();
		
		int line = startLine - 1;
		int col = startColumn;
		String charAt;
		if (line > 0) {
			charAt = Character.toString(surface.get(line).charAt(col));

			if (charAt.equals("|") || charAt.equals("7") || charAt.equals("F")) {
				positions.add(day10.new Position(line, col));
			}
		}
		
		line = startLine;
		col = startColumn + 1;
		charAt = Character.toString(surface.get(line).charAt(col));
		if (col > 0 && col < surface.get(line).length())
		if (charAt.equals("-") || charAt.equals("7") || charAt.equals("J")) {
			positions.add(day10.new Position(line, col));
			if (positions.size() == 2) {
				return positions;
			}
		}
		
		line = startLine + 1;
		col = startColumn;
		charAt = Character.toString(surface.get(line).charAt(col));
		if (line < surface.size())
		if (charAt.equals("|") || charAt.equals("J") || charAt.equals("L")) {
			positions.add(day10.new Position(line, col));
			if (positions.size() == 2) {
				return positions;
			}
		}
		
		line = startLine;
		col = startColumn - 1;
		charAt = Character.toString(surface.get(line).charAt(col));
		if (col > 0 && col < surface.get(line).length())
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
