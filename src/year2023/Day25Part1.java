package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day25Part1 {
	
	private static Hashtable<String, List<String>> nodes;
	
	public static void run() {
		initializeNodes();
		
		Hashtable<String, List<String>> testedConnections = new Hashtable<String, List<String>>();
		
		List<List<String>> groups = new ArrayList<List<String>>();
		
		for (String nodeName : nodes.keySet()) {
			for (String connectionName : nodes.get(nodeName)) {
				if (wasConnectionTested(testedConnections, nodeName, connectionName)) {
					continue;
				}
				
				int mutualConnections = countMutualConnections(nodeName, connectionName);
				if (mutualConnections >= 3) {
					addToGroups(groups, nodeName, connectionName);
				}
			}
		}
		
		for (List<String> a : groups) {
			System.out.println("Cena:");
			for (String b : a) {
				System.out.println("- " + b);
			}
		}
		
		List<List<String>> finalGroups = new ArrayList<>();
		
		// calculate final Groups		
		
		System.out.println(finalGroups.get(0).size() * finalGroups.get(1).size());
	}
	
//	private static boolean isRemovableConnection(List<List<String>> groups, String node1, String node2) {
//		for (List<String> group : groups) {
//			if (group.contains(node1) && group.contains(node2)) {
//				return false;
//			}
//		}
//		
//		return true;
//	}
//	
//	private static void removeConnections(Hashtable<String, List<String>> nodes, String node1, String node2) {
//		nodes.get(node1).remove(node2);
//		nodes.get(node2).remove(node1);
//	}
	
	private static void addToGroups(List<List<String>> groups, String node1, String node2) {
		int groupAddedTo = -1;
		for (int i = 0; i < groups.size(); i++) {
			List<String> group = groups.get(i);
			
			boolean contains1 = group.contains(node1);
			boolean contains2 = group.contains(node2);
			if (contains1 || contains2) {
				if (groupAddedTo != -1) {
					group.remove(node1);
					group.remove(node2);
					groups.get(groupAddedTo).addAll(group);
					groups.remove(i);
					return;
				}
				
				groupAddedTo = i;
				if (!contains1) {
					group.add(node1);
				}
				else if (!contains2) {
					group.add(node2);
				}
				else {
					return;
				}
			}
		}
		
		if (groupAddedTo == -1) {
			List<String> group = new ArrayList<String>();
			group.add(node1);
			group.add(node2);
			groups.add(group);
		}
	}
	
//	private static void removeConnections(Hashtable<String, List<String>> nodes, List<String> toDeleteConnections) {
//		for (int i = 0; i < toDeleteConnections.size(); i += 2) {
//			String node1 = toDeleteConnections.get(i);
//			String node2 = toDeleteConnections.get(i + 1);
//			
//			nodes.get(node1).remove(node2);
//			nodes.get(node2).remove(node1);
//		}
//	}
	
//	private static List<List<String>> formGroups(Hashtable<String, List<String>> nodes) {
//		List<List<String>> groups = new ArrayList<List<String>>();
//		
//		List<String> executeList = new ArrayList<String>();
//		
//		List<String> firstList = new ArrayList<String>();
//		String firstNode = nodes.keySet().iterator().next();
//		executeList.add(firstNode);
//		while (executeList.size() > 0) {
//			String node = executeList.get(0);
//			firstList.add(node);
//			
//			for (String connectedNode : nodes.get(node)) {
//				if (!firstList.contains(connectedNode)) {
//					executeList.add(connectedNode);
//				}
//			}
//			
//			executeList.remove(0);
//		}
//		
//		groups.add(firstList);
//		
//		List<String> secondList = new ArrayList<String>();
//		
//		Iterator<String> iterator = nodes.keySet().iterator();
//		for (int i = 0; i < nodes.size(); i++) {
//			firstNode = iterator.next();
//			if (!firstList.contains(firstNode)) {
//				break;
//			}
//		}
//		
//		secondList.add(firstNode);
//		
//		while (executeList.size() > 0) {
//			String node = executeList.get(0);
//			secondList.add(node);
//			
//			for (String connectedNode : nodes.get(node)) {
//				if (!secondList.contains(connectedNode)) {
//					executeList.add(connectedNode);
//				}
//			}
//			
//			executeList.remove(0);
//		}
//		
//		groups.add(secondList);
//		
//		if (nodes.size() != groups.get(0).size() + groups.get(1).size()) {
//			System.out.println("OUTRA MERDA");
//			return null;
//		}
//		
//		return groups;
//	}
	
	private static boolean wasConnectionTested(Hashtable<String, List<String>> testedConnections, String node1, String node2) {
		if (!testedConnections.containsKey(node1)) {
			List<String> connections = new ArrayList<String>();
			testedConnections.put(node2, connections);
			connections.add(node1);
			
			return false;
		}
		
		List<String> connections = testedConnections.get(node1);
		if (connections.contains(node2)) {
			return true;
		}
		
		if (testedConnections.containsKey(node2)) {
			connections = testedConnections.get(node2);
		}
		else {
			connections = new ArrayList<String>();
			testedConnections.put(node2, connections);
		}
		
		connections.add(node1);
		
		return false;
	}
	
	private static int countMutualConnections(String node1, String node2) {
		int count = 0;
		
		List<String> node1Connections = nodes.get(node1);
		List<String> node2Connections = nodes.get(node2);
		
		for (String node1Connection : node1Connections) {
			if (node2Connections.contains(node1Connection)) {
				count++;
			}
		}
		
		return count;
	}
	
	private static void initializeNodes() {
		nodes = new Hashtable<>();
		
		try {
	        File myObj = new File("InputFiles/25.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            int indexOfCollon = line.lastIndexOf(":");
	            
	            String key = line.substring(0, indexOfCollon);
	            
	            String[] connections = line.substring(indexOfCollon + 2).split(" ");
	            
	            for (String connection : connections) {
	            	addToNodes(nodes, key, connection);
	            	addToNodes(nodes, connection, key);
	            }
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void addToNodes(Hashtable<String, List<String>> nodes, String node1, String node2) {
		if (nodes.containsKey(node1)) {
			nodes.get(node1).add(node2);
			return;
		}
		
		List<String> connections = new ArrayList<String>();
		connections.add(node2);
		nodes.put(node1, connections);
	}

}
