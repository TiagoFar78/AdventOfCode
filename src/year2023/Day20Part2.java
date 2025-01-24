package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Day20Part2 {
	
	private static Day20Part2 day20 = new Day20Part2();
	
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
		long vdCount = countButtonClicksUntilTarget("vd", Pulse.HIGH);
		
		initializeModules();
		long nsCount = countButtonClicksUntilTarget("ns", Pulse.HIGH);

		initializeModules();
		long bhCount = countButtonClicksUntilTarget("bh", Pulse.HIGH);

		initializeModules();
		long dlCount = countButtonClicksUntilTarget("dl", Pulse.HIGH);
		
		long lcm1 = lcm(vdCount, nsCount);
		long lcm2 = lcm(lcm1, bhCount);
		long lcm3 = lcm(lcm2, dlCount);
		
		System.out.println(lcm3);
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
	
	private static long countButtonClicksUntilTarget(String targetName, Pulse targetPulse) {
 		long buttonClicks = 0;
		
		List<String> modulesQueue = new ArrayList<String>();
		List<Pulse> pulsesQueue = new ArrayList<Pulse>();
		
		boolean findTarget = false;
		
		while (!findTarget) {
			buttonClicks++;
			
			modulesQueue.add("button");
			pulsesQueue.add(Pulse.LOW);
			
			while (modulesQueue.size() > 0) {
				String currentModuleName = modulesQueue.get(0);
				Module currentModule = modules.get(currentModuleName);
				Pulse currentPulse = pulsesQueue.get(0);
				
				if (currentModule == null) {
					modulesQueue.remove(0);
					pulsesQueue.remove(0);
					continue;
				}
				
				Pulse nextPulse = currentModule.propagatePulse(currentPulse);
				List<String> nextModulesNames = currentModule.getDestinationModules();
				
				if (currentModuleName.equals(targetName) && nextPulse == targetPulse) {
					findTarget = true;
					break;
				}
				
				if (nextPulse != null) {
					for (String nextModuleName : nextModulesNames) {
						Module nextModel = modules.get(nextModuleName);
						
						if (nextModel instanceof Conjunction) {
							((Conjunction) nextModel).senderPropagatedPulse(currentModuleName, nextPulse);
						}
						
						modulesQueue.add(nextModuleName);
						pulsesQueue.add(nextPulse);
					}
				}
				
				modulesQueue.remove(0);
				pulsesQueue.remove(0);
			}
		}
		
		return buttonClicks;
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
