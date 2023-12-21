package Dec2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day19Part2 {
	
	private static final String INITIAL_WORKFLOW = "in";
	
	private static Day19Part2 day19 = new Day19Part2();
	
	private enum MachinePartCategory {
		
		EXTREMELY_COOL_LOOCKING,
		MUSICAL,
		AERODYNAMIC,
		SHINY;
		
	}
	
	private class Rule {
		
		private MachinePartCategory _category;
		private String _comparator;
		private int _value;
		private String _destination;
		
		public Rule(String category, String comparator, int value, String destination) {
			_category = category.equals("x") ? MachinePartCategory.EXTREMELY_COOL_LOOCKING : category.equals("m") ? MachinePartCategory.MUSICAL :
				category.equals("a") ? MachinePartCategory.AERODYNAMIC : MachinePartCategory.SHINY;
			_comparator = comparator;
			_value = value;
			_destination = destination;
		}
		
		public List<MachinePartRange> splitRanges(MachinePartRange range) {
			List<MachinePartRange> splitedRanges = new ArrayList<MachinePartRange>();
			
			int xMin = range.getExtremelyCoolLookingValue(true);
			int xMax = range.getExtremelyCoolLookingValue(false);
			int mMin = range.getMusicalValue(true);
			int mMax = range.getMusicalValue(false);
			int aMin = range.getAerodynamicValue(true);
			int aMax = range.getAerodynamicValue(false);
			int sMin = range.getShinyValue(true);
			int sMax = range.getShinyValue(false);
			
			if (_category == MachinePartCategory.EXTREMELY_COOL_LOOCKING) {
				List<String> destinations = getSplitedRangesDestinations(xMin, xMax);
				
				if (destinations.size() == 1) {
					MachinePartRange newRange = new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax);
					newRange.setDestination(destinations.get(0));
					
					splitedRanges.add(newRange);
				}
				else {
					if (destinations.get(0).equals("")) {
						MachinePartRange newRange1 = day19.new MachinePartRange(_value, xMax, mMin, mMax, aMin, aMax, sMin, sMax);
						newRange1.setDestination("");
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, _value - 1, mMin, mMax, aMin, aMax, sMin, sMax);
						newRange2.setDestination(_destination);
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
					else {
						MachinePartRange newRange1 = day19.new MachinePartRange(_value + 1, xMax, mMin, mMax, aMin, aMax, sMin, sMax);
						newRange1.setDestination(_destination);
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, _value, mMin, mMax, aMin, aMax, sMin, sMax);
						newRange2.setDestination("");
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
				}
			}
			else if (_category == MachinePartCategory.MUSICAL) {
				List<String> destinations = getSplitedRangesDestinations(mMin, mMax);
				
				if (destinations.size() == 1) {
					MachinePartRange newRange = new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax);
					newRange.setDestination(destinations.get(0));
					
					splitedRanges.add(newRange);
				}
				else {
					if (destinations.get(0).equals("")) {
						MachinePartRange newRange1 = day19.new MachinePartRange(xMin, xMax, _value, mMax, aMin, aMax, sMin, sMax);
						newRange1.setDestination("");
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, xMax, mMin, _value - 1, aMin, aMax, sMin, sMax);
						newRange2.setDestination(_destination);
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
					else {
						MachinePartRange newRange1 = day19.new MachinePartRange(xMin, xMax, _value + 1, mMax, aMin, aMax, sMin, sMax);
						newRange1.setDestination(_destination);
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, xMax, mMin, _value, aMin, aMax, sMin, sMax);
						newRange2.setDestination("");
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
				}
				
			}
			else if (_category == MachinePartCategory.AERODYNAMIC) {
				List<String> destinations = getSplitedRangesDestinations(aMin, aMax);
				
				if (destinations.size() == 1) {
					MachinePartRange newRange = new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax);
					newRange.setDestination(destinations.get(0));
					
					splitedRanges.add(newRange);
				}
				else {
					if (destinations.get(0).equals("")) {
						MachinePartRange newRange1 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, _value, aMax, sMin, sMax);
						newRange1.setDestination("");
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, aMin, _value - 1, sMin, sMax);
						newRange2.setDestination(_destination);
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
					else {
						MachinePartRange newRange1 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, _value + 1, aMax, sMin, sMax);
						newRange1.setDestination(_destination);
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, aMin, _value, sMin, sMax);
						newRange2.setDestination("");
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
				}
				
			}
			else {
				List<String> destinations = getSplitedRangesDestinations(sMin, sMax);
				
				if (destinations.size() == 1) {
					MachinePartRange newRange = new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax);
					newRange.setDestination(destinations.get(0));
					
					splitedRanges.add(newRange);
				}
				else {
					if (destinations.get(0).equals("")) {
						MachinePartRange newRange1 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, _value, sMax);
						newRange1.setDestination("");
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, sMin, _value - 1);
						newRange2.setDestination(_destination);
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
					else {
						MachinePartRange newRange1 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, _value + 1, sMax);
						newRange1.setDestination(_destination);
						
						MachinePartRange newRange2 = day19.new MachinePartRange(xMin, xMax, mMin, mMax, aMin, aMax, sMin, _value);
						newRange2.setDestination("");
						
						splitedRanges.add(newRange1);
						splitedRanges.add(newRange2);
					}
				}
				
			}
			
			return splitedRanges;
		}
		
		private List<String> getSplitedRangesDestinations(int min, int max) {
			List<String> destinations = new ArrayList<String>();
			
			if (_comparator.equals("<")) {
				if (max >= _value) {
					if (min >= _value) {
						destinations.add("");
					}
					else {
						destinations.add("");
						destinations.add(_destination);
					}
				}
				else {
					destinations.add(_destination);
				}
			}
			else {
				if (min <= _value) {
					if (max <= _value) {
						destinations.add("");
					}
					else {
						destinations.add(_destination);
						destinations.add("");
					}
				}
				else {
					destinations.add(_destination);
				}					
			}
			
			return destinations;
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
		
		public List<MachinePartRange> getDestination(MachinePartRange range) {
			List<MachinePartRange> newRanges = new ArrayList<MachinePartRange>();
			
			List<MachinePartRange> toSplitRanges = new ArrayList<MachinePartRange>();
			toSplitRanges.add(range);
			
			for (Rule rule : _rules) {
				List<MachinePartRange> toSplitRangesTemp = new ArrayList<MachinePartRange>();				
				for (MachinePartRange toSplitRange : toSplitRanges) {
					List<MachinePartRange> splitedRanges = rule.splitRanges(toSplitRange);
					for (MachinePartRange splitedRange : splitedRanges) {
						if (splitedRange.getDestination().equals("")) {
							toSplitRangesTemp.add(splitedRange);
						}
						else {
							newRanges.add(splitedRange);
						}
					}
				}
				
				toSplitRanges.clear();
				toSplitRanges.addAll(toSplitRangesTemp);
			}
			
			for (MachinePartRange undestinedRange : toSplitRanges) {
				undestinedRange.setDestination(_finalDestination);
			}
			
			newRanges.addAll(toSplitRanges);
			
			return newRanges;
		}
	}
	
	private class MachinePartRange {
		
		private String _destination = "";		
		private int _xMin;
		private int _xMax;
		private int _mMin;
		private int _mMax;
		private int _aMin;
		private int _aMax;
		private int _sMin;
		private int _sMax;
		
		public MachinePartRange(int xMin, int xMax, int mMin, int mMax, int aMin, int aMax, int sMin, int sMax) {
			_xMin = xMin;
			_xMax = xMax;
			_mMin = mMin;
			_mMax = mMax;
			_aMin = aMin;
			_aMax = aMax;
			_sMin = sMin;
			_sMax = sMax;
		}
		
		public String getDestination() {
			return _destination;
		}
		
		public void setDestination(String destination) {
			_destination = destination;
		}
		
		public int getExtremelyCoolLookingValue(boolean isMinValue) {
			return isMinValue ? _xMin : _xMax;
		}
		
		public int getMusicalValue(boolean isMinValue) {
			return isMinValue ? _mMin : _mMax;
		}
		
		public int getAerodynamicValue(boolean isMinValue) {
			return isMinValue ? _aMin : _aMax;
		}
		
		public int getShinyValue(boolean isMinValue) {
			return isMinValue ? _sMin : _sMax;
		}
		
		public long getValuesMultiplication() {
			int xComb = _xMax - _xMin + 1;
			int mComb = _mMax - _mMin + 1;
			int aComb = _aMax - _aMin + 1;
			int sComb = _sMax - _sMin + 1;
			
			long xTimesm = xComb * mComb;
			long aTimess = aComb * sComb;
			
			return xTimesm * aTimess;
		}
		
	}
	
	private static Hashtable<String, Workflow> _workflows;
	
	public static void run() {
		initializeSystem();
		
		long sum = 0;
		
		MachinePartRange initialRange = day19.new MachinePartRange(1, 4000, 1, 4000, 1, 4000, 1, 4000);
		initialRange.setDestination(INITIAL_WORKFLOW);
		
		List<MachinePartRange> activeRanges = new ArrayList<MachinePartRange>();
		activeRanges.add(initialRange);
		while (activeRanges.size() > 0) {
			String destination = activeRanges.get(0).getDestination();
			
			if (destination.equals("A")) {
				sum += activeRanges.get(0).getValuesMultiplication();
			}
			else if (!destination.equals("R")) {
				Workflow workflow = _workflows.get(destination);
				activeRanges.addAll(workflow.getDestination(activeRanges.get(0)));
			}
			
			activeRanges.remove(0);
		}
		
		System.out.println(sum);
	}
	
	private static void initializeSystem() {
		_workflows = new Hashtable<String, Workflow>();
		
		try {
	        File myObj = new File("InputFiles/19.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            
	            if (line.equals("")) {
	            	break;
	            }
	            
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
            		String comparator = lastCondition.substring(1, 2);
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
