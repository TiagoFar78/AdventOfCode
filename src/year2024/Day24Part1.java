package year2024;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day24Part1 extends Challenge {
    
    private long solve(Map<String, Integer> inputs, Map<String, String[]> gates, int maxZ) {
        for (String key : gates.keySet()) {
            inputs.put(key, getValue(inputs, gates, key));
        }
        
        long result = 0;
        int currentZ = maxZ;
        while (currentZ >= 0) {
            String key = "z" + String.format("%02d", currentZ);
            result <<= 1;
            result |= inputs.get(key);
            currentZ--;
        }
        
        return result;
    }
    
    private int getValue(Map<String, Integer> inputs, Map<String, String[]> gates, String key) {
        if (inputs.containsKey(key)) {
            return inputs.get(key);
        }
        
        String[] gate = gates.get(key);
        int input1 = getValue(inputs, gates, gate[0]);
        int input2 = getValue(inputs, gates, gate[2]);
        
        switch (gate[1]) {
            case "AND":
                return input1 & input2;
            case "OR":
                return input1 | input2;
            case "XOR":
                return input1 ^ input2;
        }
        
        return -1;
    }

    @Override
    public long solve() {
        Map<String, Integer> inputs = new HashMap<>();
        Map<String, String[]> gates = new HashMap<>();
        int maxZ = 0;
        
        Pattern pattern = Pattern.compile("[a-z0-9]{3}|AND|OR|XOR|\\d");
        
        Scanner reader = getInputFile();
        while (true) {
            String line = reader.nextLine();
            if (line.length() == 0) {
                break;
            }
            
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            String key = matcher.group();
            matcher.find();
            inputs.put(key, Integer.parseInt(matcher.group()));
        }
        
        while (reader.hasNextLine()) {
            String[] value = new String[3];
            
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.find();
            value[0] = matcher.group();
            matcher.find();
            value[1] = matcher.group();
            matcher.find();
            value[2] = matcher.group();
            matcher.find();
            String key = matcher.group();
            gates.put(key, value);
            if (key.charAt(0) == 'z') {
                maxZ = Math.max(maxZ, Integer.parseInt(key.substring(1)));
            }
        }
        reader.close();
        
        return solve(inputs, gates, maxZ);
    }
}
