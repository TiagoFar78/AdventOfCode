package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day24Part1 {
	
	private static final Long MIN_TEST_BOUNDARY = 200000000000000L;
	private static final Long MAX_TEST_BOUNDARY = 400000000000000L;

	private static Day24Part1 day24 = new Day24Part1();
	
	private class Hailstone {
		
		private long _px;
		private long _vx;
		private long _py;
		private long _vy;
		
		public Hailstone(long px, long vx, long py, long vy) {
			_px = px;
			_vx = vx;
			_py = py;
			_vy = vy;
		}
		
		public double calculateXPosition(double time) {
			return (double) _px + (double) _vx * time;
		}
		
		public double calculateYPosition(double time) {
			return (double) _py + (double) _vy * time;
		}
		
		public double getIntersectionTime(Hailstone o) {
			double possibleZero = _vy - o._vy * _vx / o._vx;
			if (possibleZero == 0) {
				return 0;
			}
			
			double time = ((double) o._py + (double) ((double) o._vy/(double) o._vx)*((double) _px - (double) o._px) - (double) _py) / ((double) _vy - (double) o._vy * (double) _vx / (double) o._vx);
			
			return time;
		}
		
		public double getOtherPathIntersectionTime(Hailstone o, double time) {
			double otherPathTime = ((double) _px + time * (double) _vx - (double) o._px) / (double) o._vx;
			
			return otherPathTime;
		}
		
	}
	
	private static List<Hailstone> hailstones;
	
	public static void run() {
		initializeHailstones();
		
		int totalIntersections = 0;
		
		for (int i = 0; i < hailstones.size(); i++) {
			Hailstone hailstone = hailstones.get(i);
			
			for (int j = i + 1; j < hailstones.size(); j++) {
				double intersectionTime = hailstone.getIntersectionTime(hailstones.get(j));
				double otherPathIntersectionTime = hailstone.getOtherPathIntersectionTime(hailstones.get(j), intersectionTime);
				
				if (intersectionTime <= 0 || otherPathIntersectionTime < 0) {
					continue;
				}
				
				double intersectionX = hailstone.calculateXPosition(intersectionTime);
				double intersectionY = hailstone.calculateYPosition(intersectionTime);
				
				if (intersectionX >= MIN_TEST_BOUNDARY && intersectionX <= MAX_TEST_BOUNDARY &&
						intersectionY >= MIN_TEST_BOUNDARY && intersectionY <= MAX_TEST_BOUNDARY) {
					totalIntersections++;
				}
			}
		}
		
		System.out.println(totalIntersections);
	}
	
	private static void initializeHailstones() {
		hailstones = new ArrayList<Hailstone>();
		
		try {
	        File myObj = new File("InputFiles/24.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            int indexOfAt = line.lastIndexOf("@");
	            
	            String pPart = line.substring(0, indexOfAt - 1);
	            String vPart = line.substring(indexOfAt + 2);
	            
	            String[] pCoords = pPart.split(", ");
	            String[] vCoords = vPart.split(", ");
	            
	            Hailstone hailstone = day24.new Hailstone(stringToLong(pCoords[0]), stringToLong(vCoords[0]),
	            		stringToLong(pCoords[1]), stringToLong(vCoords[1]));
	            hailstones.add(hailstone);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	private static long stringToLong(String s) {
		try {			
			return Long.parseLong(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}
	
}
