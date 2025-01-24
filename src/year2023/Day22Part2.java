package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day22Part2 {
	
	private static Day22Part2 day22 = new Day22Part2();
	
	private class Brick implements Comparable<Brick> {
		
		private int _id;
		private int _lowerX;
		private int _lowerY;
		private int _lowerZ;
		private int _higherX;
		private int _higherY;
		private int _higherZ;
		
		public Brick(int id, int lowerX, int lowerY, int lowerZ, int higherX, int higherY, int higherZ) {
			_id = id;
			
			int[] sortedX = sortNumbers(lowerX, higherX);
			_lowerX = sortedX[0];
			_higherX = sortedX[1];
			
			int[] sortedY = sortNumbers(lowerY, higherY);
			_lowerY = sortedY[0];
			_higherY = sortedY[1];
			
			int[] sortedZ = sortNumbers(lowerZ, higherZ);
			_lowerZ = sortedZ[0];
			_higherZ= sortedZ[1];
		}
		
		public int getID() {
			return _id;
		}
		
		private int[] sortNumbers(int number1, int number2) {
			if (number1 < number2) {
				int[] sortedNumbers = { number1, number2 };
				return sortedNumbers;
			}
			
			int[] sortedNumbers = { number2, number1 };
			return sortedNumbers;
		}
		
		public int[] getXBoundaries() {
			int[] boundaries = { _lowerX, _higherX };
			return boundaries;
		}
		
		public int[] getYBoundaries() {
			int[] boundaries = { _lowerY, _higherY };
			return boundaries;
		}
		
		public int[] getZBoundaries() {
			int[] boundaries = { _lowerZ, _higherZ };
			return boundaries;			
		}
		
		public boolean isOnLocation(int x, int y, int z) {
			return x >= _lowerX && x <= _higherX && y >= _lowerY && y <= _higherY && z >= _lowerZ && z <= _higherZ;
		}

		@Override
		public int compareTo(Brick o) {
			return _lowerZ - o.getZBoundaries()[0];
		}
		
	}
	
	private static List<Brick> bricks;
	
	private static boolean isSupported(Brick brick, List<Brick> bricks) {
		int[] xBoundaries = brick.getXBoundaries();
		int[] yBoundaries = brick.getYBoundaries();
		int[] zBoundaries = brick.getZBoundaries();
		
		if (zBoundaries[0] == 1) {
			return true;
		}
		
		for (int i = xBoundaries[0]; i <= xBoundaries[1]; i++) {
			for (int j = yBoundaries[0]; j <= yBoundaries[1]; j++) {
				for (Brick supporterBrick : bricks) {
					if (supporterBrick.isOnLocation(i, j, zBoundaries[0] - 1)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static void run() {
		initializeBricks();
		
		Collections.sort(bricks);
		
		for (int i = 0; i < bricks.size(); i++) {
			Brick brick = bricks.get(i);
			
			if (!isSupported(brick, bricks)) {
				int[] xBoundaries = brick.getXBoundaries();
				int[] yBoundaries = brick.getYBoundaries();
				int[] zBoundaries = brick.getZBoundaries();
				
				bricks.set(i, day22.new Brick(brick.getID(), xBoundaries[0], yBoundaries[0], zBoundaries[0] - 1, xBoundaries[1], yBoundaries[1], zBoundaries[1] - 1));
				i--;
			}
		}
		
		int otherBricksDesintegrated = 0;
		
		for (int i = 0; i < bricks.size(); i++) {
			List<Brick> clonedBricks = cloneList(bricks);
			clonedBricks.remove(i);
			
			for (int j = 0; j < clonedBricks.size(); j++) {
				if (!isSupported(clonedBricks.get(j), clonedBricks)) {
					otherBricksDesintegrated++;
					clonedBricks.remove(j);
					j--;
				}
			}
 		}
		
		System.out.println(otherBricksDesintegrated);
	}
	
	private static List<Brick> cloneList(List<Brick> bricks) {
		List<Brick> clonedList = new ArrayList<Brick>();
		
		for (Brick brick : bricks) {
			int[] xBoundaries = brick.getXBoundaries();
			int[] yBoundaries = brick.getYBoundaries();
			int[] zBoundaries = brick.getZBoundaries();
			
			clonedList.add(day22.new Brick(brick.getID(), xBoundaries[0], yBoundaries[0], zBoundaries[0], xBoundaries[1], yBoundaries[1], zBoundaries[1]));
		}
		
		return clonedList;
	}
	
	private static void initializeBricks() {
		bricks = new ArrayList<Brick>();
		
		int id = 1;
		
		try {
	        File myObj = new File("InputFiles/22.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            int tildeIndex = line.lastIndexOf("~");
	            String[] lowerPosition = line.substring(0, tildeIndex).split(",");
	            String[] higherPosition = line.substring(tildeIndex + 1).split(",");
	            
	            bricks.add(day22.new Brick(id, stringToInt(lowerPosition[0]), stringToInt(lowerPosition[1]), 
	            		stringToInt(lowerPosition[2]), stringToInt(higherPosition[0]), 
	            		stringToInt(higherPosition[1]), stringToInt(higherPosition[2])));
	            id++;
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
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
