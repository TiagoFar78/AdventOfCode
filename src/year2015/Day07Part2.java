package year2015;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private final static int MAX = 65535;
    
    private int solve(Map<String, String[]> circuit) {
        Map<String, Integer> seen = new HashMap<>();
        int a = valueOf("a", circuit, seen);
        
        seen.clear();
        seen.put("b", a);
        return valueOf("a", circuit, seen);
    }
    
    private int valueOf(String wire, Map<String, String[]> circuit, Map<String, Integer> seen) {
        if (seen.containsKey(wire)) {
            return seen.get(wire);
        }
        
        String[] wireCalculation = circuit.get(wire);
        
        if (wireCalculation == null) {
            return Integer.parseInt(wire);
        }
        
        if (wireCalculation[0].equals("NOT")) {
            return MAX ^ valueOf(wireCalculation[1], circuit, seen);
        }
        else if (wireCalculation.length == 1) {
            try {
                return Integer.parseInt(wireCalculation[0]);
            }
            catch (NumberFormatException e) {
                return valueOf(wireCalculation[0], circuit, seen);
            }
        }
        
        int result = 0;
        switch (wireCalculation[1]) {
            case "AND":
                result = valueOf(wireCalculation[0], circuit, seen) & valueOf(wireCalculation[2], circuit, seen);
                break;
            case "OR":
                result = valueOf(wireCalculation[0], circuit, seen) | valueOf(wireCalculation[2], circuit, seen);
                break;
            case "LSHIFT":
                result = (valueOf(wireCalculation[0], circuit, seen) << Integer.parseInt(wireCalculation[2])) & MAX;
                break;
            case "RSHIFT":
                result = valueOf(wireCalculation[0], circuit, seen) >> Integer.parseInt(wireCalculation[2]);
                break;
        }
        
        seen.put(wire, result);
        return result;
    }

    @Override
    public long solve() {
        Map<String, String[]> circuit = new HashMap<>();
        
        Pattern pattern = Pattern.compile("(.*) -> (.*)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            circuit.put(matcher.group(2), matcher.group(1).split(" "));
        }
        reader.close();
        
        return solve(circuit);
    }

}
