package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day08Part2 {
	
	private static Day08Part2 day8 = new Day08Part2();
	
	private static final int NODE_SIZE = 3;
	
	private class Node {
		
		private List<String> _connectedNodes = new ArrayList<String>();
		
		public Node(String left, String right) {
			_connectedNodes.add(left);
			_connectedNodes.add(right);
		}
		
		public String getDestination(String followedPath) {
			if (followedPath.equals("L")) {
				return _connectedNodes.get(0);
			}
			
			return _connectedNodes.get(1);
		}
		
	}
	
	private static List<String> individualSearches;
	private static String path;
	private static Hashtable<String, Node> nodes;
	private static List<Long> minimalCommonMultipliers;
	
	private static void initializeMinimalCommonMultipliers() {
		minimalCommonMultipliers = new ArrayList<Long>();
		
		for (int i = 1; i < individualSearches.size(); i++) {
			minimalCommonMultipliers.add((long) 0);
		}
	}
	
	public static void run() {
		initializePathAndNodes();
		initializeMinimalCommonMultipliers();
		
		for (int i = 1; i < individualSearches.size(); i++) {
			long iterationCount = 0;
			
			List<Integer> testingNumbers = new ArrayList<Integer>();
			testingNumbers.add(0);
			testingNumbers.add(i);
			
			while (true) {
				boolean allEndWithZ = true;
				for (int j : testingNumbers) {
					String currentNode = individualSearches.get(j);
					
					Node node = nodes.get(currentNode);
					
					int pathIndex = (int) (iterationCount % path.length());
					String direction = Character.toString(path.charAt(pathIndex));
					
					String newNode = node.getDestination(direction);
					individualSearches.set(j, newNode);
					
					if (!Character.toString(newNode.charAt(NODE_SIZE - 1)).equals("Z")) {
						allEndWithZ = false;
					}
				}
				
				
				iterationCount++;
				if (allEndWithZ) {
					break;
				}
			}
			
			minimalCommonMultipliers.set(i - 1, iterationCount);
		}
		
		long lcm = lcm(minimalCommonMultipliers.get(0), minimalCommonMultipliers.get(1));
		for (int i = 2; i < minimalCommonMultipliers.size(); i++) {
			lcm = lcm(lcm, minimalCommonMultipliers.get(i));
		}
		
		System.out.println(lcm);		
	}
	
	public static long lcm(long number1, long number2) {
	    if (number1 == 0 || number2 == 0) {
	        return 0;
	    }
	    
	    long absHigherNumber = Math.max(number1, number2);
	    long absLowerNumber = Math.min(number1, number2);
	    long lcm = absHigherNumber;
	    
	    while (lcm % absLowerNumber != 0) {
	        lcm += absHigherNumber;
	    }
	    
	    return lcm;
	}
	
	private static void initializePathAndNodes() {
		individualSearches = new ArrayList<String>();
		path = "";
		nodes = new Hashtable<String, Node>();
		
		try {
	        File myObj = new File("InputFiles/8.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        String line = myReader.nextLine();
	        for (int i = 0; i < line.length(); i++) {
        		String charAt = Character.toString(line.charAt(i));
        		
        		path += charAt;
        	}
	        
	        myReader.nextLine();
	        
	        String betweenFatherAndLeft = " = (";
	        String betweenLeftAndRight = ", ";
	        
	        while (myReader.hasNextLine()) {	        	
	            line = myReader.nextLine();
        		
        		String fatherNode = line.substring(0, NODE_SIZE);
        		int startIndex = NODE_SIZE + betweenFatherAndLeft.length();
        		String leftNode = line.substring(startIndex, startIndex + NODE_SIZE);
        		startIndex = startIndex + NODE_SIZE + betweenLeftAndRight.length();
        		String rightNode = line.substring(startIndex, startIndex + NODE_SIZE);
        		
        		Node node = day8.new Node(leftNode, rightNode);
        		nodes.put(fatherNode, node);
        		
        		if (Character.toString(fatherNode.charAt(NODE_SIZE - 1)).equals("A")) {
        			individualSearches.add(fatherNode);
        		}
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	

}
