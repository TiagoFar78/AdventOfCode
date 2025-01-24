package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day20Part1 {
	
	private static final int CLICKS = 1000;
	
	private static Day20Part1 day20 = new Day20Part1();
	
	private enum Pulse {
		
		LOW,
		HIGH;
		
	}
	
	private abstract class Module {
		
		private List<String> _destinationModules = new ArrayList<String>();
		
		public void setDestinationModules(String[] destinationModules) {
			for (String destinationModule : destinationModules) {
				_destinationModules.add(destinationModule);
			}
		}
		
		public abstract Pulse propagatePulse(Pulse pulse);
		
		public List<String> getDestinationModules() {
			return _destinationModules;
		}
		
	}
	
	private class FlipFlop extends Module {
		
		private boolean _isTurnedOn = false;
		
		@Override
		public Pulse propagatePulse(Pulse pulse) {
			if (pulse == Pulse.HIGH) {
				return null;
			}
			
			_isTurnedOn = !_isTurnedOn;
			
			return _isTurnedOn ? Pulse.HIGH : Pulse.LOW;
		}
		
	}
	
	private class Conjunction extends Module {
		
		private Hashtable<String, Pulse> _senderModulesLastPulse = new Hashtable<String, Pulse>();
		
		public void addSenderModule(String name) {
			_senderModulesLastPulse.put(name, Pulse.LOW);
		}
		
		public void senderPropagatedPulse(String name, Pulse pulse) {
			_senderModulesLastPulse.put(name, pulse);
		}
		
		@Override
		public Pulse propagatePulse(Pulse pulse) {
			boolean allHighPulse = true;
			for (String registeredSender : _senderModulesLastPulse.keySet()) {
				if (_senderModulesLastPulse.get(registeredSender) == Pulse.LOW) {
					allHighPulse = false;
				}
			}
			
			return allHighPulse ? Pulse.LOW : Pulse.HIGH;
		}
		
	}
	
	private class Broadcaster extends Module {
		
		@Override
		public Pulse propagatePulse(Pulse pulse) {
			return pulse;
		}
		
	}
	
	private class Button extends Module {
		
		@Override
		public Pulse propagatePulse(Pulse pulse) {
			return Pulse.LOW;
		}
		
	}
	
	private static Hashtable<String, Module> modules;
	
	public static void run() {
		initializeModules();
		
		int lowPulsesSent = 0;
		int highPulsesSent = 0;
		List<Integer> lowPulsesSentByEachClick = new ArrayList<Integer>();
		List<Integer> highPulsesSentByEachClick = new ArrayList<Integer>();
		
		int buttonClicks = 0;
		List<String> pulsePropagationLog = new ArrayList<String>();
		int buttonsClicksLog = 0;
		List<String> pulsePropagationComparator = new ArrayList<String>();
		int buttonClicksComparator = 0;
		
		List<String> modulesQueue = new ArrayList<String>();
		List<Pulse> pulsesQueue = new ArrayList<Pulse>();
		
		while (buttonClicks < CLICKS) {
			buttonClicks++;
			buttonClicksComparator++;
			
			modulesQueue.add("button");
			pulsesQueue.add(Pulse.LOW);
			
			while (modulesQueue.size() > 0) {
				String currentModuleName = modulesQueue.get(0);
				Module currentModule = modules.get(currentModuleName);
				Pulse currentPulse = pulsesQueue.get(0);
				
				Pulse nextPulse = currentModule.propagatePulse(currentPulse);
				List<String> nextModulesNames = currentModule.getDestinationModules();
				
				if (nextPulse != null) {
					for (String nextModuleName : nextModulesNames) {
						Module nextModel = modules.get(nextModuleName);
						
						if (nextModel instanceof Conjunction) {
							((Conjunction) nextModel).senderPropagatedPulse(currentModuleName, nextPulse);
						}
						
						if (nextModel != null) {
							modulesQueue.add(nextModuleName);
							pulsesQueue.add(nextPulse);
						}
						
						pulsePropagationComparator.add(currentModuleName + " -" + nextPulse.toString().toLowerCase() + "-> " + nextModuleName);
					}
					
					if (nextPulse == Pulse.LOW) {
						int valueBefore = lowPulsesSentByEachClick.size() == 0 ? 0 : lowPulsesSentByEachClick.get(lowPulsesSentByEachClick.size() - 1);
						lowPulsesSentByEachClick.add(valueBefore + nextModulesNames.size());
						
						lowPulsesSent += nextModulesNames.size();
					}
					else {
						int valueBefore = highPulsesSentByEachClick.size() == 0 ? 0 : highPulsesSentByEachClick.get(highPulsesSentByEachClick.size() - 1);
						highPulsesSentByEachClick.add(valueBefore + nextModulesNames.size());
						
						highPulsesSent += nextModulesNames.size();
					}
				}
				
				modulesQueue.remove(0);
				pulsesQueue.remove(0);
			}
			
			if (buttonsClicksLog == buttonClicksComparator) {
				if (equals(pulsePropagationLog, pulsePropagationComparator)) {
					break;
				}
				else {
					buttonsClicksLog += buttonClicksComparator;
					buttonClicksComparator = 0;
					pulsePropagationLog.addAll(pulsePropagationComparator);
					pulsePropagationComparator.clear();
				}
			}
			else if (buttonsClicksLog < buttonClicksComparator) {
				buttonsClicksLog += buttonClicksComparator;
				buttonClicksComparator = 0;
				pulsePropagationLog.addAll(pulsePropagationComparator);
				pulsePropagationComparator.clear();
			}
		}
		
		int clicksPerCycle = buttonClicks / 2;
		
		int cyclesUntilLastClick = CLICKS / clicksPerCycle;
		int rest = CLICKS % clicksPerCycle;
		
		int lowPulsesSentPerCycle = lowPulsesSent / 2;
		int highPulsesSentPerCycle = highPulsesSent / 2;
		
		int totalLowPulsesSent = lowPulsesSentPerCycle * cyclesUntilLastClick;
		int totalHighPulsesSent = highPulsesSentPerCycle * cyclesUntilLastClick;
		
		if (rest > 0) {
			totalLowPulsesSent = lowPulsesSentByEachClick.get(rest - 1);
			totalHighPulsesSent = highPulsesSentByEachClick.get(rest - 1);
		}
		
		long totalPulses = totalLowPulsesSent * totalHighPulsesSent;
		
		System.out.println(totalPulses);
	}
	
	private static boolean equals(List<String> l1, List<String> l2) {
		for (int i = 0; i < l1.size(); i++) {
			if (!l1.get(i).equals(l2.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void initializeModules() {
		modules = new Hashtable<String, Module>();
		
		String[] buttonDestination = { "broadcaster" };
		
		Module buttonModule = day20.new Button();
		buttonModule.setDestinationModules(buttonDestination);
		
		modules.put("button", buttonModule);
		
		List<String> conjunctionModules = new ArrayList<String>();
		List<String> secondLoop = new ArrayList<String>();
		
		try {
	        File myObj = new File("InputFiles/20.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
	            secondLoop.add(line);
	            
	            Module module;
	            String name = line.substring(1, line.lastIndexOf(" -> "));
	            
	            if (line.startsWith("%")) {
	            	module = day20.new FlipFlop();
	            }
	            else if (line.startsWith("&")) {
	            	module = day20.new Conjunction();
	            	conjunctionModules.add(name);
	            }
	            else {
	            	name = "broadcaster";
	            	module = day20.new Broadcaster();
	            }
	            
	            module.setDestinationModules(getDestinationModules(line));
	            modules.put(name, module);
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		for (String conjunctionModuleName : conjunctionModules) {
			Conjunction conjunction = (Conjunction) modules.get(conjunctionModuleName);
			
			for (String line : secondLoop) {
				int conjunctionNameIndex = line.lastIndexOf(conjunctionModuleName);
				
				if (conjunctionNameIndex != -1 && line.lastIndexOf(" -> ") < conjunctionNameIndex) {
					int startIndex = line.startsWith("%") || line.startsWith("&") ? 1 : 0;
		            String name = line.substring(startIndex, line.lastIndexOf(" -> "));
		            
		            conjunction.addSenderModule(name);
				}
			}
		}
		
	}
	
	private static String[] getDestinationModules(String line) {
		line = line.substring(line.lastIndexOf("> ") + 2);
		
		return line.split(", ");
	}

}
