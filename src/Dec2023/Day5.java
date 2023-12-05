package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5 {
	
	private static Day5 day5 = new Day5();
	
	private class RangeMap {
		
		private double _sourceStart;
		private double _destinationStart;
		private double _rangeLength;
		
		public RangeMap(double sourceStart, double destinationStart, double rangeLength) {
			_sourceStart = sourceStart;
			_destinationStart = destinationStart;
			_rangeLength = rangeLength;
		}
		
		public double map(double key) {
			if (key < _sourceStart || key >= _sourceStart + _rangeLength) {
				return -1;
			}
			
			return _destinationStart + key - _sourceStart;
		}
	}
	
	private class SpecialMap {
		
		private List<RangeMap> _rangeMaps = new ArrayList<RangeMap>();
		
		public void addRangeMap(RangeMap rangeMap) {
			_rangeMaps.add(rangeMap);
		}
		
		public double map(double key) {
			for (RangeMap rangeMap : _rangeMaps) {
				double value = rangeMap.map(key);
				if (value != -1) {
					return value;
				}
			}
			
			return key;
		}
	}
	
	public static void run() {
		initializeSeedsAndMaps();
		
		solvePart1();
		
		solvePart2();
	}
	
	private static List<Double> seeds = new ArrayList<Double>();
	private static List<SpecialMap> maps = new ArrayList<SpecialMap>();
	
	private static void solvePart1() {
		double min = -1;
		
		for (double seed : seeds) {
			double key = seed;
			for (SpecialMap specialMap : maps) {
				key = specialMap.map(key);
			}
			
			if (min == -1 || key < min) {
				min = key;
			}
		}
		
		System.out.printf("%f\n", min);
	}
	
	private static void solvePart2() {
		double min = -1;
		
		for (int i = 0; i < seeds.size(); i += 2) {
			for (double key = seeds.get(i); key < seeds.get(i) + seeds.get(i + 1); key++) {
				double mappingKey = key;
				
				for (SpecialMap specialMap : maps) {
					mappingKey = specialMap.map(mappingKey);
				}
				
				if (min == -1 || mappingKey < min) {
					min = mappingKey;
				}
			}
		}
		
		System.out.printf("%f\n", min);
	}
	
	private static void initializeSeedsAndMaps() {
		String seedsString = "seeds: ";
		String mapString = "map";
		
		SpecialMap specialMap = null;
		
		try {
	        File myObj = new File("InputFiles/5.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            String numberComposition = "";
	            
	            if (line.startsWith(seedsString)) {
	            	for (int i = seedsString.length(); i < line.length(); i++) {
	            		String charAt = Character.toString(line.charAt(i));
	            		
	            		double doubleegerAt = stringToDouble(charAt);
	            		if (doubleegerAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if (charAt.equals(" ") || i == line.length() - 1) {
	            			seeds.add(stringToDouble(numberComposition));
	            			numberComposition = "";
	            		}
	            	}
	            	
	            	myReader.nextLine();
	            }
	            else if (line.contains(mapString)) {
	            	specialMap = day5.new SpecialMap();
	            }
	            else if (line.equals("")) {
	            	maps.add(specialMap);
	            }
	            else {
	            	double sourceStart = -1;
	            	double destinationStart = -1;
	            	double rangeLength = -1;
	            	
	            	for (int i = 0; i < line.length(); i++) {
	            		String charAt = Character.toString(line.charAt(i));
	            		
	            		double doubleegerAt = stringToDouble(charAt);
	            		if (doubleegerAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if (charAt.equals(" ") || i == line.length() - 1) {
	            			double number = stringToDouble(numberComposition);
	            			if (sourceStart != -1) {
	            				rangeLength = number;
	            			}
	            			else if (destinationStart != -1) {
	            				sourceStart = number;
	            			}
	            			else {
	            				destinationStart = number;
	            			}
	            			
	            			numberComposition = "";
	            		}
	            	}
	            	
	            	RangeMap rangeMap = day5.new RangeMap(sourceStart, destinationStart, rangeLength);
	            	specialMap.addRangeMap(rangeMap);
	            	
	            	if (!myReader.hasNextLine()) {
		            	maps.add(specialMap);
	            	}
	            }
	            
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	private static double stringToDouble(String s) {
		try {
			return Double.parseDouble(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
