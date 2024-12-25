package Dec2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day24Part2 extends Challenge {
    
    private static final String FOUND_TAG = "found";
    
    private long solve(Map<String, String[]> gates, int zMax) {
        List<String> wrongGates = new ArrayList<>();
        
        String[] z00Gate = gates.get("z00");
        if (!matchesSumX(z00Gate, 0)) {
            wrongGates.add("z00");
        }
        
        String[] z01Gate = gates.get("z01");
        if (wrongGates.contains("z01")) {
            // Nothing
        }
        else if (!z01Gate[1].equals("XOR")) {
            wrongGates.add("z01");
        }
        else if (matchesCarryXSum(gates.get(z01Gate[0]), 0)) {
            if (!matchesSumX(gates.get(z01Gate[2]), 1)) {
                wrongGates.add(z01Gate[2]);
            }
        }
        else if (matchesSumX(gates.get(z01Gate[0]), 1)) {
            if (!matchesCarryXSum(gates.get(z01Gate[2]), 0)) {
                wrongGates.add(z01Gate[2]);
            }
        }
        else if (matchesSumX(gates.get(z01Gate[2]), 1) || matchesCarryXSum(gates.get(z01Gate[2]), 0)) {
            wrongGates.add(z01Gate[0]);
        }
        else {
            wrongGates.add("z01");
        }
        
        for (int x = 2; x < zMax; x++) {
            String key = "z" + String.format("%02d", x);
            String[] zxGate = gates.get(key);
            if (!zxGate[1].equals("XOR")) {
                wrongGates.add(key);
                continue;
            }
            
            String gate0Mistake = findMistakeInCarryX(wrongGates, gates, zxGate[0], x - 1);
            String gate2Mistake = findMistakeInCarryX(wrongGates, gates, zxGate[2], x - 1);
            if (wrongGates.contains(zxGate[0]) || wrongGates.contains(zxGate[2])) {
                // Nothing
            }
            else if (gate0Mistake == null || gate0Mistake.equals(FOUND_TAG)) {
                if (!matchesSumX(gates.get(zxGate[2]), x)) {
                    wrongGates.add(zxGate[2]);
                }
            }
            else if (matchesSumX(gates.get(zxGate[0]), x)) {
                
                if (gate2Mistake != null && !gate2Mistake.equals(FOUND_TAG)) {
                    wrongGates.add(gate2Mistake);
                }
            }
            else if (matchesSumX(gates.get(zxGate[2]), x) || gate2Mistake == null || gate2Mistake.equals(FOUND_TAG)) {
                wrongGates.add(gate0Mistake);
            }
            else {
                wrongGates.add(key);
            }
        }
        
        String zMaxMistake = findMistakeInCarryX(wrongGates, gates, "z" + String.format("%02d", zMax), zMax - 1);
        if (zMaxMistake != null && !zMaxMistake.equals(FOUND_TAG)) {
            wrongGates.add(zMaxMistake);
        }
        
        Collections.sort(wrongGates);
        System.out.println(String.join(",", wrongGates.stream().toArray(String[]::new)));
        return 0;
    }
    
    private boolean matchesSumX(String[] gate, int x) {
        String sx = String.format("%02d", x);
        return gate != null && gate[1].equals("XOR") && 
                (gate[0].equals("x" + sx) || gate[0].equals("y" + sx)) && 
                (gate[2].equals("x" + sx) || gate[2].equals("y" + sx));
    }
    
    private boolean matchesCarryXSum(String[] gate, int x) {
        String sx = String.format("%02d", x);
        return gate[1].equals("AND") && 
                (gate[0].equals("x" + sx) || gate[0].equals("y" + sx)) && 
                (gate[2].equals("x" + sx) || gate[2].equals("y" + sx));
    }
    
    private String findMistakeInCarryXCarry(List<String> wrongGates, Map<String, String[]> gates, String key, int x) {      
        if (wrongGates.contains(key)) {
            return FOUND_TAG;
        }
        
        String[] gate = gates.get(key);
        if (gate == null || !gate[1].equals("AND")) {
            return wrongGates.contains(key) ? FOUND_TAG : key;
        }
        
        String gate0Mistake = findMistakeInCarryX(wrongGates, gates, gate[0], x - 1);
        String gate2Mistake = findMistakeInCarryX(wrongGates, gates, gate[2], x - 1);
        if (gate0Mistake == null) {
            return matchesSumX(gates.get(gate[2]), x) ? null : wrongGates.contains(gate[2]) ? FOUND_TAG : gate[2];
        }
        else if (matchesSumX(gates.get(gate[0]), x)) {
            return gate2Mistake == null ? null : wrongGates.contains(gate[2]) ? FOUND_TAG : gate2Mistake;
        }
        else if (gate2Mistake == null || gate2Mistake.equals(FOUND_TAG) || matchesSumX(gates.get(gate[2]), x) || wrongGates.contains(gate[2])) {
            return wrongGates.contains(gate[0]) ? FOUND_TAG : gate0Mistake;
        }
        
        return wrongGates.contains(key) ? FOUND_TAG : key;
    }
    
    private String findMistakeInCarryX(List<String> wrongGates, Map<String, String[]> gates, String key, int x) {
        if (x == 0) {
            return matchesCarryXSum(gates.get(key), x) ? null : key;
        }
        
        String[] gate = gates.get(key);
        if (gate == null || !gate[1].equals("OR")) {
            return wrongGates.contains(key) ? FOUND_TAG : key;
        }
        
        if (matchesCarryXSum(gates.get(gate[0]), x)) {
            String gate2Mistake = findMistakeInCarryXCarry(wrongGates, gates, gate[2], x);
            return gate2Mistake == null ? null : wrongGates.contains(gate[2]) ? FOUND_TAG : gate2Mistake;
        }
        String gate0Mistake = findMistakeInCarryXCarry(wrongGates, gates, gate[0], x);
        String gate2Mistake = findMistakeInCarryXCarry(wrongGates, gates, gate[2], x);
        if (gate0Mistake == null) {
            return matchesCarryXSum(gates.get(gate[2]), x) ? null : wrongGates.contains(gate[2]) ? FOUND_TAG : gate[2];
        }
        if (gate2Mistake == null || gate2Mistake.equals(FOUND_TAG) || matchesCarryXSum(gates.get(gate[2]), x) || wrongGates.contains(gate[2])) {
            return wrongGates.contains(gate0Mistake) ? FOUND_TAG : gate0Mistake;
        }
        return key;
    }

    @Override
    public long solve() {
        Map<String, String[]> gates = new HashMap<>();
        int maxZ = 0;
        
        Pattern pattern = Pattern.compile("[a-z0-9]{3}|AND|OR|XOR");
        
        Scanner reader = getInputFile();
        while (true) {
            String line = reader.nextLine();
            if (line.length() == 0) {
                break;
            }
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
        
        return solve(gates, maxZ);
    }
}
