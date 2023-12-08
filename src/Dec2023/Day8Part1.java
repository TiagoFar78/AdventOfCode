package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day8Part1 {
	
	private static Day8Part1 day8 = new Day8Part1();
	
	private static final String START_NODE = "AAA";
	private static final String DESTINATION_NODE = "ZZZ";
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
	
	private static String path;
	private static Hashtable<String, Node> nodes = new Hashtable<String, Node>();
	
	public static void run() {
		initializePathAndNodes();
		
		String currentNode = START_NODE;
		int iterationCount = 0;
		while (!currentNode.equals(DESTINATION_NODE)) {
			Node node = nodes.get(currentNode);
			
			int pathIndex = iterationCount % path.length();
			String direction = Character.toString(path.charAt(pathIndex));
			
			currentNode = node.getDestination(direction);
			iterationCount++;
		}
		
		System.out.println(iterationCount);		
	}
	
	private static void initializePathAndNodes() {
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
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

}
