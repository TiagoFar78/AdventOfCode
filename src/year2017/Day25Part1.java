package year2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day25Part1 extends Challenge {
    
    private class BoolIntStringTuple {
        
        public boolean b;
        public int i;
        public String s;
        
        public BoolIntStringTuple(boolean b, int i, String s) {
            this.b = b;
            this.i = i;
            this.s = s;
        }
        
    }
    
    private int solve(String currentState, int steps, Map<String, BoolIntStringTuple> states) {
        Set<Integer> tape = new HashSet<>();
        int i = 0;
        
        while (steps > 0) {
            steps--;
            int tapeValue = tape.contains(i) ? 1 : 0;
            
            BoolIntStringTuple behavior = states.get(currentState + Integer.toString(tapeValue));
            if (behavior.b) {
                tape.add(i);
            }
            else {
                tape.remove(i);
            }
            i += behavior.i;
            currentState = behavior.s;
        }
        
        return tape.size();
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        
        Matcher startingStateMatcher = Pattern.compile("Begin in state (.*)\\.").matcher(reader.nextLine());
        startingStateMatcher.matches();
        String startingState = startingStateMatcher.group(1);
        
        Matcher stepsMatcher = Pattern.compile("Perform a diagnostic checksum after (\\d+) steps\\.").matcher(reader.nextLine());
        stepsMatcher.matches();
        int steps = Integer.parseInt(stepsMatcher.group(1));
        
        Map<String, BoolIntStringTuple> states = new HashMap<>();
        while (reader.hasNextLine()) {
            reader.nextLine();
            
            Matcher matcher = Pattern.compile("In state (.*):").matcher(reader.nextLine());
            matcher.matches();
            String stateName = matcher.group(1);
            
            reader.nextLine();
            
            matcher = Pattern.compile("    - Write the value (.)\\.").matcher(reader.nextLine());
            matcher.matches();
            boolean isWriting = matcher.group(1).equals("1");
            
            matcher = Pattern.compile("    - Move one slot to the (.*)\\.").matcher(reader.nextLine());
            matcher.matches();
            int direction = matcher.group(1).equals("left") ? -1 : 1;
            
            matcher = Pattern.compile("    - Continue with state (.)\\.").matcher(reader.nextLine());
            matcher.matches();
            String nextState = matcher.group(1);
            
            states.put(stateName + "0", new BoolIntStringTuple(isWriting, direction, nextState));
            
            reader.nextLine();
            
            matcher = Pattern.compile("    - Write the value (.)\\.").matcher(reader.nextLine());
            matcher.matches();
            isWriting = matcher.group(1).equals("1");
            
            matcher = Pattern.compile("    - Move one slot to the (.*)\\.").matcher(reader.nextLine());
            matcher.matches();
            direction = matcher.group(1).equals("left") ? -1 : 1;
            
            matcher = Pattern.compile("    - Continue with state (.)\\.").matcher(reader.nextLine());
            matcher.matches();
            nextState = matcher.group(1);
            
            states.put(stateName + "1", new BoolIntStringTuple(isWriting, direction, nextState));
        }
        reader.close();
        
        return solve(startingState, steps, states);
    }
    
}
