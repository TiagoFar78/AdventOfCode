package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day19Part1 {
	
	private static final String INITIAL_WORKFLOW = "in";
	
	private static Day19Part1 day19 = new Day19Part1();
	
	private enum MachinePartCategory {
		
		EXTREMELY_COOL_LOOCKING,
		MUSICAL,
		AERODYNAMIC,
		SHINY;
		
	}
	
	private class Rule {
		
		private MachinePartCategory _category;
		private Comparator<Integer> _comparator;
		private int _value;
		private String _destination;
		
		public Rule(String category, Comparator<Integer> comparator, int value, String destination) {
			_category = category.equals("x") ? MachinePartCategory.EXTREMELY_COOL_LOOCKING : category.equals("m") ? MachinePartCategory.MUSICAL :
				category.equals("a") ? MachinePartCategory.AERODYNAMIC : MachinePartCategory.SHINY;
			_comparator = comparator;
			_value = value;
			_destination = destination;
		}
		
		public boolean isSatisfied(MachinePart part) {
			int partAtribute = _category == MachinePartCategory.EXTREMELY_COOL_LOOCKING ? part.getExtremelyCoolLookingValue() :
				_category == MachinePartCategory.MUSICAL ? part.getMusicalValue() : 
				_category == MachinePartCategory.AERODYNAMIC ? part.getAerodynamicValue() : part.getShinyValue();
			
			return _comparator.compare(partAtribute, _value) > 0;
		}
		
		public String getDestination() {
			return _destination;
		}
	}
	
	private class Workflow {
		
		private List<Rule> _rules = new ArrayList<Rule>();
		private String _finalDestination;
		
		public void addRule(Rule rule) {
			_rules.add(rule);
		}
		
		public void setFinalDestination(String destination) {
			_finalDestination = destination;
		}
		
		public String getDestination(MachinePart part) {
			for (Rule rule : _rules) {
				if (rule.isSatisfied(part)) {
					return rule.getDestination();
				}
			}
			
			return _finalDestination;
		}
	}
	
	private class MachinePart {
		
		private int _x;
		private int _m;
		private int _a;
		private int _s;
		
		public MachinePart(int x, int m, int a, int s) {
			_x = x;
			_m = m;
			_a = a;
			_s = s;
		}
		
		public int getExtremelyCoolLookingValue() {
			return _x;
		}
		
		public int getMusicalValue() {
			return _m;
		}
		
		public int getAerodynamicValue() {
			return _a;
		}
		
		public int getShinyValue() {
			return _s;
		}
		
		public int getValuesSum() {
			return _x + _m + _a + _s;
		}
		
	}
	
	private static Hashtable<String, Workflow> _workflows;
	private static List<MachinePart> _parts;
	
	public static void run() {
		initializeSystem();
		
		int sum = 0;
		
		for (MachinePart part : _parts) {
			String destination = INITIAL_WORKFLOW;
			
			while (!destination.equals("R")) {
				destination = _workflows.get(destination).getDestination(part);
				
				if (destination.equals("A")) {
					sum += part.getValuesSum();
					break;
				}
			}
		}
		
		System.out.println(sum);
	}
	
	private static void initializeSystem() {
		_workflows = new Hashtable<String, Workflow>();
		_parts = new ArrayList<MachinePart>();
		
		Comparator<Integer> biggerComparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		};
		
		Comparator<Integer> lowerComparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		};
		
		boolean isWorkflow = true;
		try {
	        File myObj = new File("InputFiles/19.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            if (line.equals("")) {
	            	isWorkflow = false;
	            	continue;
	            }
	            
	            if (isWorkflow) {
	            	String name = line.substring(0, line.lastIndexOf("{"));
	            	Workflow workflow = day19.new Workflow();
	            	
	            	String finalDestination = line.substring(line.lastIndexOf(",") + 1, line.length() - 1);
	            	workflow.setFinalDestination(finalDestination);
	            	
	            	String conditionsLine = line.substring(0, line.lastIndexOf(","));
	            	while (true) {
	            		int lastIndexOfComma = conditionsLine.lastIndexOf(",");
	            		int lastConditionStart = lastIndexOfComma != -1 ? lastIndexOfComma : conditionsLine.lastIndexOf("{");
	            		
	            		String lastCondition = conditionsLine.substring(lastConditionStart + 1);
	            		
	            		String attribute = lastCondition.substring(0, 1);
	            		Comparator<Integer> comparator = lastCondition.substring(1, 2).equals(">") ? biggerComparator : lowerComparator;
	            		int value = stringToInt(lastCondition.substring(2, lastCondition.lastIndexOf(":")));
	            		String destination = lastCondition.substring(lastCondition.lastIndexOf(":") + 1);
	            		
	            		Rule rule = day19.new Rule(attribute, comparator, value, destination);
	            		workflow.addRule(rule);
	            		
	            		lastIndexOfComma = conditionsLine.lastIndexOf(",");
	            		if (lastIndexOfComma == -1) {
	            			break;
	            		}
	            		
	            		conditionsLine = conditionsLine.substring(0, lastIndexOfComma);
	            	}
            		
            		Collections.reverse(workflow._rules);
            		
            		_workflows.put(name, workflow);
	            }
	            else {	            	
	            	line = line.substring(0, line.length() - 1);
	            	
	            	int s = stringToInt(line.substring(line.lastIndexOf("=") + 1));
	            	line = line.substring(0, line.lastIndexOf(","));
	            	
	            	int a = stringToInt(line.substring(line.lastIndexOf("=") + 1));
	            	line = line.substring(0, line.lastIndexOf(","));
	            	
	            	int m = stringToInt(line.substring(line.lastIndexOf("=") + 1));
	            	line = line.substring(0, line.lastIndexOf(","));
	            	
	            	int x = stringToInt(line.substring(line.lastIndexOf("=") + 1));
	            	
	            	MachinePart part = day19.new MachinePart(x, m, a, s);
	            	_parts.add(part);
	            }
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
//		printSystem();
	}

	private static int stringToInt(String s) {
		try {
			return Integer.parseInt(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}
	
	private static void printSystem() {
		System.out.println("Workflows");
		for (String name : _workflows.keySet()) {
			System.out.println(name + ":");
			for (Rule rule : _workflows.get(name)._rules) {
				System.out.println("- " + rule._category.toString() + " " + rule._value + " -> " + rule.getDestination());
			}
			System.out.println("-> " + _workflows.get(name)._finalDestination);
		}
	}
	
}
