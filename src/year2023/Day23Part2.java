package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day23Part2 {
	
	private static Day23Part2 day23 = new Day23Part2();
	
	private class PathToNode {
		
		private Position _pos;
		private int _pathLength;
		
		public PathToNode(Position pos, int pathLength) {
			_pos = pos;
			_pathLength = pathLength;
		}
		
		public Position getPos() {
			return _pos;
		}
		
		public int getPathLength() {
			return _pathLength;
		}
		
	}
	
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
	
	private static List<Integer> completedNodes;
	private static Hashtable<Integer, List<PathToNode>> nodes;
	
	private static int positionToKey(int currentLine, int currentCol) {
		return currentLine * 150 + currentCol;
	}
	
	public static void run() {
		initializeMap();
		removeSlopes(map);
		
		int startingCol = getStartCol(map);
		
		initializeNodes();
		
		int largerPathTiles = largerPathTiles(new ArrayList<Integer>(), 0, startingCol, 0);
		
		System.out.println(largerPathTiles);
	}
	
	private static int largerPathTiles(List<Integer> visitedNodes, int line, int col, int tiles) {
		int currentPositionKey = positionToKey(line, col);
		
		visitedNodes.add(currentPositionKey);
		
		List<PathToNode> allNodes = nodes.get(currentPositionKey);
		List<PathToNode> possibleNodes = new ArrayList<PathToNode>();
		for (PathToNode pathToNode : allNodes) {
			Position pos = pathToNode.getPos();
			if (!visitedNodes.contains(positionToKey(pos.getLine(), pos.getCol()))) {
				possibleNodes.add(pathToNode);
			}
		}
		
		if (possibleNodes.size() == 0) {
			return line == map.size() - 1 ? tiles : 0;
		}
		
		int largerPathTiles = 0;
		for (PathToNode pathToNode : possibleNodes) {
			Position pos = pathToNode.getPos();
			
			List<Integer> clonedVisistedNodes = cloneListInteger(visitedNodes);
			int pathTiles = largerPathTiles(clonedVisistedNodes, pos.getLine(), pos.getCol(), tiles + pathToNode.getPathLength());
			if (pathTiles > largerPathTiles) {
				largerPathTiles = pathTiles;
			}
			
		}
		
		return largerPathTiles;
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
	
	private static List<Integer> cloneListInteger(List<Integer> list) {
		List<Integer> clonedList = new ArrayList<Integer>();
		
		for (Integer element : list) {			
			clonedList.add(element);
		}
		
		return clonedList;
	}
	
	private static void setOnMap(List<String> map, int line, int col, String element) {
		String prevLine = map.get(line);
		
		String nextLine = prevLine.substring(0, col) + element + prevLine.substring(col + 1);
 		
		map.set(line, nextLine);
	}
	
	private static void initializeNodes() {
		nodes = new Hashtable<Integer, List<PathToNode>>();
		completedNodes = new ArrayList<Integer>();
		
		int startingCol = getStartCol(map);
		initializeNodes(map, 0, startingCol, null);
	}
	
	private static void initializeNodes(List<String> map, int line, int col, Position startingPos) {
		int tiles = 0;
		
		int startingLine = line;
		int startingCol = col;
		
		if (startingPos == null) {
			startingLine = line;
			startingCol = col;
			startingPos = day23.new Position(line, col);
		}
		else {
			startingLine = startingPos.getLine();
			startingCol = startingPos.getCol();
		}
		
		while (true) {
			tiles++;
			setOnMap(map, line, col, "O");
			
			List<Position> possibleDirections = getPossibleDirections(map, line, col);
			if (possibleDirections == null) {
				break;
			}
			
			if (possibleDirections.size() == 0) {
				PathToNode pathToNode1 = day23.new PathToNode(day23.new Position(line, col), tiles - 1);
				PathToNode pathToNode2 = day23.new PathToNode(startingPos, tiles - 1);
				
				addToNodes(startingLine, startingCol, pathToNode1);
				addToNodes(line, col, pathToNode2);
				
				break;
			}
			
			if (possibleDirections.size() > 1) {
				PathToNode pathToNode1 = day23.new PathToNode(day23.new Position(line, col), tiles);
				PathToNode pathToNode2 = day23.new PathToNode(startingPos, tiles);
				
				addToNodes(startingLine, startingCol, pathToNode1);
				addToNodes(line, col, pathToNode2);
				
				if (!completedNodes.contains(positionToKey(line, col))) {
					for (Position pos : possibleDirections) {					
						List<String> clonedMap = cloneList(map);
						
						initializeNodes(clonedMap, pos.getLine(), pos.getCol(), day23.new Position(line, col));
					}
				}
				
				break;
			}
			
			line = possibleDirections.get(0).getLine();
			col = possibleDirections.get(0).getCol();
		}	
		
		completedNodes.add(positionToKey(startingLine, startingCol));
	}
	
	private static void addToNodes(int line, int col, PathToNode pathToNode) {
		int key = positionToKey(line, col);
		
		List<PathToNode> paths;
		
		if (nodes.containsKey(key)) {
			paths = nodes.get(key);
		}
		else {
			paths = new ArrayList<PathToNode>();
			nodes.put(key, paths);
		}
		
		if (!contains(paths, pathToNode)) {
			paths.add(pathToNode);
		}
	}
	
	private static boolean contains(List<PathToNode> list, PathToNode element) {
		for (PathToNode path : list) {
			if (path.getPos().getLine() == element.getPos().getLine() &&
					path.getPos().getCol() == element.getPos().getCol() && path.getPathLength() == element.getPathLength()) {
				return true;
			}
		}
		
		return false;
	}
	
	private static List<Position> getPossibleDirections(List<String> map, int line, int col) {
		List<Position> positions = new ArrayList<Position>();
		
		int[] lines = { line - 1, line, line + 1, line };
		int[] cols = { col, col + 1, col, col - 1 };
		String[][] slopes = { { ">" }, { ">", "v" }, { "v", ">" }, { "v" } };
		
		int oCount = 0;
		
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] < 0 || lines[i] >= map.size() || cols[i] < 0 || cols[i] >= map.get(0).length()) {
				continue;
			}
			
			String charAt = Character.toString(map.get(lines[i]).charAt(cols[i]));
			if (charAt.equals(".") || contains(slopes[i], charAt)) {
				positions.add(day23.new Position(lines[i], cols[i]));
			}
			
			if (charAt.equals("O")) {
				oCount++;
			}
		}
		
		return oCount >= 2 ? null : positions;
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
