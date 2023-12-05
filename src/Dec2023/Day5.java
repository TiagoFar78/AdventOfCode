package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day5 {
	
	private static Day5 day5 = new Day5();
	
	private class Range implements Comparable<Range> {
		
		private long _start;
		private long _length;
		
		public Range(long start, long length) {
			_start = start;
			_length = length;
		}
		
		public long getStart() {
			return _start;
		}
		
		public long getEnd() {
			return _start + _length - 1;
		}

		@Override
		public int compareTo(Range o) {
			long comparison = _start - o._start;
			if (comparison < Integer.MIN_VALUE) {
				return -1;
			}
			
			if (comparison > Integer.MAX_VALUE) {
				return 1;
			}
			
			return (int) (_start - o._start);
		}
	}
	
	private class RangeMap {
		
		private long _sourceStart;
		private long _destinationStart;
		private long _rangeLength;
		
		public RangeMap(long sourceStart, long destinationStart, long rangeLength) {
			_sourceStart = sourceStart;
			_destinationStart = destinationStart;
			_rangeLength = rangeLength;
		}
		
		public long map(long key) {
			if (key < _sourceStart || key >= _sourceStart + _rangeLength) {
				return -1;
			}
			
			return _destinationStart + key - _sourceStart;
		}
		
		public long getRangeExpandability(Range range) {
			if (range.getStart() < _sourceStart || range.getStart() > _sourceStart + _rangeLength - 1) {
				return -1;
			}
			
			return range.getEnd() >= _sourceStart + _rangeLength - 1 ? _sourceStart + _rangeLength - 1 : range.getEnd();
		}
		
		public Range map(Range key) {
			return day5.new Range(map(key.getStart()), key._length);
		}
	}
	
	private class SpecialMap {
		
		private List<RangeMap> _rangeMaps = new ArrayList<RangeMap>();
		
		public void addRangeMap(RangeMap rangeMap) {
			_rangeMaps.add(rangeMap);
		}
		
		public long map(long key) {
			for (RangeMap rangeMap : _rangeMaps) {
				long value = rangeMap.map(key);
				if (value != -1) {
					return value;
				}
			}
			
			return key;
		}
		
		public List<Range> map(Range key) {
			List<Range> mappedRanges = new ArrayList<Range>();
			
			while (key != null) {
				
				boolean wasKeyChanged = false;
				for (RangeMap rangeMap : _rangeMaps) {
					long subrangeEnd = rangeMap.getRangeExpandability(key);
					if (subrangeEnd != -1) {
						Range splitedRange = day5.new Range(key.getStart(), subrangeEnd - key.getStart() + 1);
						mappedRanges.add(rangeMap.map(splitedRange));
						
						if (subrangeEnd == key.getEnd()) {
							wasKeyChanged = true;
							key = null;
							break;
						}
						else {
							wasKeyChanged = true;
							key = day5.new Range(subrangeEnd + 1, key.getEnd() - subrangeEnd + 1);
							break;
						}						
					}
				}
				
				if (!wasKeyChanged) {
					mappedRanges.add(key);
					break;
				}
			}
			
			return mappedRanges;
		}
	}
	
	public static void run() {
		initializeSeedsAndMaps();
		
		solvePart1();
		
		solvePart2();
	}
	
	private static List<Long> seeds = new ArrayList<Long>();
	private static List<SpecialMap> maps = new ArrayList<SpecialMap>();
	
	private static void solvePart1() {
		long min = -1;
		
		for (long seed : seeds) {
			long key = seed;
			for (SpecialMap specialMap : maps) {
				key = specialMap.map(key);
			}
			
			if (min == -1 || key < min) {
				min = key;
			}
		}
		
		System.out.printf("%d\n", min);
	}
	
	private static void solvePart2() {
		long min = -1;
		
		for (int i = 0; i < seeds.size(); i += 2) {
			Range range = day5.new Range(seeds.get(i), seeds.get(i + 1));
			
			List<Range> mainRangesList = new ArrayList<Range>();
			mainRangesList.add(range);
			
			for (SpecialMap specialMap : maps) {
				List<Range> auxiliarRangesList = new ArrayList<Range>();
				
				for (Range subRange : mainRangesList) {
					auxiliarRangesList.addAll(specialMap.map(subRange));
				}
				
				mainRangesList = auxiliarRangesList;
			}
			
			Collections.sort(mainRangesList);
			
			long possibleMin = mainRangesList.get(0)._start;
			if (min == -1 || possibleMin < min) {
				min = possibleMin;
			}
		}
		
		System.out.printf("%d\n", min);
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
	            		
	            		long longegerAt = stringTolong(charAt);
	            		if (longegerAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if (charAt.equals(" ") || i == line.length() - 1) {
	            			seeds.add(stringTolong(numberComposition));
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
	            	long sourceStart = -1;
	            	long destinationStart = -1;
	            	long rangeLength = -1;
	            	
	            	for (int i = 0; i < line.length(); i++) {
	            		String charAt = Character.toString(line.charAt(i));
	            		
	            		long longegerAt = stringTolong(charAt);
	            		if (longegerAt != -1) {
	            			numberComposition += charAt;
	            		}
	            		
	            		if (charAt.equals(" ") || i == line.length() - 1) {
	            			long number = stringTolong(numberComposition);
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

	private static long stringTolong(String s) {
		try {
			return Long.parseLong(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
