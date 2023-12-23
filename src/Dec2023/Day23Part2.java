package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day23Part2 {
	
	private static Day23Part2 day23 = new Day23Part2();
	
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

	private static List<String> map;
	
//	private static Hashtable<Integer, Integer> registeredPathsLength = new Hashtable<Integer, Integer>();
//	
//	private static int positionsToKey(int currentLine, int currentCol, int prevLine, int prevCol) {
//		return currentLine * 150 * 150 * 150 + currentCol * 150 * 150 + + prevLine * 150 + prevCol;
//	}
	
	public static void run() {
		System.out.println("Startou");
		initializeMap();
		
		removeSlopes(map);
		
		int startingCol = getStartCol(map);
		
		int largerPathTiles = largerPathTiles(map, 0, startingCol);
		
		System.out.println(largerPathTiles);
	}
	
	private static int largerPathTiles(List<String> map, int line, int col) {
		int tiles = 0;
		
		while (true) {
			tiles++;
			setOnMap(map, line, col, "O");
			
			List<Position> possibleDirections = getPossibleDirections(map, line, col);
			
			if (possibleDirections.size() == 0) {
				return line == map.size() - 1 ? tiles - 1 : 0;
			}
			
			if (possibleDirections.size() > 1) {
				int largerPathTiles = 0;
				
				for (Position pos : possibleDirections) {
					
					int pathTiles;
					
					List<String> clonedMap = cloneList(map);
					
					pathTiles = largerPathTiles(clonedMap, pos.getLine(), pos.getCol());
					
					if (pathTiles > largerPathTiles) {
						largerPathTiles = pathTiles;
					}
				}
				
				return largerPathTiles == 0 ? 0 : tiles + largerPathTiles;
			}
			
			line = possibleDirections.get(0).getLine();
			col = possibleDirections.get(0).getCol();
		}
	}
	
	private static List<Position> getPossibleDirections(List<String> map, int line, int col) {
		List<Position> positions = new ArrayList<Position>();
		
		int[] lines = { line - 1, line, line + 1, line };
		int[] cols = { col, col + 1, col, col - 1 };
		String[][] slopes = { { ">" }, { ">", "v" }, { "v", ">" }, { "v" } };
		
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] < 0 || lines[i] >= map.size() || cols[i] < 0 || cols[i] >= map.get(0).length()) {
				continue;
			}
			
			String charAt = Character.toString(map.get(lines[i]).charAt(cols[i]));
			if (charAt.equals(".") || contains(slopes[i], charAt)) {
				positions.add(day23.new Position(lines[i], cols[i]));
			}
		}
		
		return positions;
	}
	
	private static boolean contains(String[] list, String element) {
		for (String o : list) {
			if (o.equals(element)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static int getStartCol(List<String> map) {
		for (int i = 0; i < map.get(0).length(); i++) {
			String charAt = Character.toString(map.get(0).charAt(i));
			
			if (charAt.equals(".")) {
				return i;
			}
		}
		
		return -1;
	}
	
	private static List<String> cloneList(List<String> list) {
		List<String> clonedList = new ArrayList<String>();
		
		for (String element : list) {			
			clonedList.add(element.substring(0));
		}
		
		return clonedList;
	}
	
	private static void setOnMap(List<String> map, int line, int col, String element) {
		String prevLine = map.get(line);
		
		String nextLine = prevLine.substring(0, col) + element + prevLine.substring(col + 1);
 		
		map.set(line, nextLine);
	}
	
	private static void initializeMap() {
		map = new ArrayList<String>();
		
		try {
	        File myObj = new File("InputFiles/23.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            map.add(line);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void removeSlopes(List<String> map) {
		for (int i = 0; i < map.size(); i++) {
			while (true) {
				int lastIndexOfSlope = map.get(i).lastIndexOf(">");
				if (lastIndexOfSlope == -1) {
					lastIndexOfSlope = map.get(i).lastIndexOf("v");
					
					if (lastIndexOfSlope == -1) {
						break;
					}
				}
				
				setOnMap(map, i, lastIndexOfSlope, ".");
			}
		}
	}
	
}
