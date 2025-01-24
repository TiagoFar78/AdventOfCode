package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13Part1 {
	
	private static List<List<String>> patterns;
	
	public static void run() {
		initializePatterns();
		
		int sumarize = 0;
		
		for (int i = 0; i < patterns.size(); i++) {
			sumarize += getLinesBeforeMirror(patterns.get(i), true);
			sumarize += 100*getLinesBeforeMirror(patterns.get(i), false);
		}
		
		System.out.println(sumarize);
	}
	
	private static int getLinesBeforeMirror(List<String> pattern, boolean isVertical) {		
		int size = isVertical ? pattern.get(0).length() : pattern.size(); 
		
		int startOffset = 0;
		int endOffset = 0;
		
		for (startOffset = 0; startOffset < size - 1; startOffset++) {
			int linesBeforeMirror = getLinesBeforeMirror(pattern, isVertical, size, startOffset, endOffset);
			if (linesBeforeMirror != 0) {
				return linesBeforeMirror;
			}
		}
		
		startOffset = 0;

		for (endOffset = 0; size - 1 - endOffset > startOffset; endOffset++) {
			int linesBeforeMirror = getLinesBeforeMirror(pattern, isVertical, size, startOffset, endOffset);
			if (linesBeforeMirror != 0) {
				return linesBeforeMirror;
			}			
		}
		
		return 0;
	}
	
	private static int getLinesBeforeMirror(List<String> pattern, boolean isVertical, int size, int startOffset, int endOffset) {
		for (int i = 0; ; i++) {
			int baseLine = i + startOffset;
			int mirrowedLine = size - 1 - i - endOffset;
			
			boolean isMirrowed = isVertical ? isMirrowed(pattern, baseLine, mirrowedLine) : isMirrowed(pattern.get(baseLine), pattern.get(mirrowedLine));
			if (!isMirrowed || mirrowedLine - baseLine == 0) {
				return 0;
			}
			
			if (mirrowedLine - baseLine == 1) {
				return baseLine + 1;
			}
		}
	}
	
	private static boolean isMirrowed(String line1, String line2) {
		return line1.equals(line2);
	}
	
	private static boolean isMirrowed(List<String> pattern, int col1, int col2) {
		for (int i = 0; i < pattern.size(); i++) {
			String charAt1 = Character.toString(pattern.get(i).charAt(col1));
			String charAt2 = Character.toString(pattern.get(i).charAt(col2));
			if (!charAt1.equals(charAt2)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void initializePatterns() {
		patterns = new ArrayList<List<String>>();
		
		List<String> currentList = new ArrayList<String>();
			
		try {
	        File myObj = new File("InputFiles/13.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            if (!line.equals("")) {
	            	currentList.add(line);
	            }
	            
	            if (line.equals("") || !myReader.hasNextLine()) {
	            	patterns.add(currentList);
	            	currentList = new ArrayList<String>();
	            }
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
	}
	

}
