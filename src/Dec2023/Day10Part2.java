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
		
		Position previousPosition = day10.new Position(startLine, startColumn);
		Position currentPosition = startingPositions.get(0);
		
		String charAt = "";
		while (!charAt.equals("S")) {
			int line = currentPosition.getLine();
			int col = currentPosition.getColumn();
			charAt = Character.toString(surface.get(line).charAt(col));
			
			currentPosition = getNextPosition(previousPosition, charAt, currentPosition);
			
			previousPosition = day10.new Position(line, col);
			
			leaveTrailBehind(previousPosition, cleanSurface, charAt);
		}
		
		replaceStartIcon(cleanSurface);
		
		insertInsideMark(cleanSurface, true);
		insertInsideMark(cleanSurface, false);
		
		int sum = 0;
		
		for (String line : cleanSurface) {
			for (int i = 0; i < line.length(); i++) {
				charAt = Character.toString(line.charAt(i));
				if (charAt.equals("I")) {
					sum++;
				}
			}
		}
		
		System.out.println(sum);
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
					leaveTrailBehind(day10.new Position(line, col), surface, "*");
				}
				else if (isInsideTheLoop && charAt.equals("*")) {
					leaveTrailBehind(day10.new Position(line, col), surface, "I");
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
	
	private static void replaceStartIcon(List<String> surface) {
		for (int i = 0; i < surface.size(); i++) {
			String line = surface.get(i);
			if (line.contains("S")) {
				for (int j = 0; j < line.length(); j++) {
					if (Character.toString(line.charAt(j)).equals("S")) {
						String newLine = line.substring(0, j) + getStartIconReplacement(surface, i, j) + line.substring(j + 1);
						surface.set(i, newLine);
						return;
					}
				}
			}
		}
	}
	
	private static String getStartIconReplacement(List<String> surface, int startLine, int startColumn) {
		int[] lines = { startLine - 1, startLine, startLine + 1, startLine };
		int[] cols = { startColumn, startColumn + 1, startColumn, startColumn - 1 };
		String[][] connectedPipes = { { "|" , "7", "F" }, { "-", "7", "J" }, { "|", "J", "L" }, { "-", "F", "L" } };
		String[][] possiblePipes = { { "|", "J", "L" }, { "-", "F", "L" }, { "|", "F", "7" }, { "-", "7", "J" } };
		
		List<String> selectedPipes = new ArrayList<String>();
		
		for (int i = 0; i < lines.length; i++) {
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
		
		int[] lines = { startLine - 1, startLine, startLine + 1, startLine };
		int[] cols = { startColumn, startColumn + 1, startColumn, startColumn - 1 };
		String[][] connectedPipes = { { "|" , "7", "F" }, { "-", "7", "J" }, { "|", "J", "L" }, { "-", "F", "L" } };
		
		for (int i = 0; i < lines.length; i++) {
			String charAt = Character.toString(surface.get(lines[i]).charAt(cols[i]));
			
			for (String pipe : connectedPipes[i]) {
				if (charAt.equals(pipe)) {
					positions.add(day10.new Position(lines[i], cols[i]));
					break;
				}
			}
			
			if (positions.size() == 2) {
				break;
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
