package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private int solve(Map<String, List<String>> structure, Map<String, Integer> weights) {
        Set<String> total = new HashSet<>(structure.keySet());
        
        for (String disc : structure.keySet()) {
            for (String element : structure.get(disc)) {
                total.remove(element);
            }
        }
        
        String root = total.iterator().next();
        return -getFixedValue(root, structure, weights);
    }
    
    private int getFixedValue(String root, Map<String, List<String>> structure, Map<String, Integer> weights) {
        int sum = 0;
        
        int[][] valuesFound = new int[2][3];
        
        for (String element : structure.get(root)) {
            int elementSum = getFixedValue(element, structure, weights);
            if (elementSum < 0) {
                return elementSum;
            }
            
            elementSum += weights.get(element);
            sum += elementSum;
            if (valuesFound[0][0] == 0 || valuesFound[0][0] == elementSum) {
                valuesFound[0][0] = elementSum;
                valuesFound[0][1]++;
                valuesFound[0][2] = elementSum - weights.get(element);
            }
            else {
                valuesFound[1][0] = elementSum;
                valuesFound[1][1]++;
                valuesFound[1][2] = elementSum - weights.get(element);
            }
        }
        
        if (valuesFound[0][1] == 1) {
            return valuesFound[0][2] - valuesFound[1][0];
        }
        else if (valuesFound[1][1] == 1) {
            return valuesFound[1][2] - valuesFound[0][0];
        }
        
        return sum;
    }

    @Override
    public long solve() {
        Map<String, List<String>> structure = new HashMap<>();
        Map<String, Integer> weights = new HashMap<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" -> ");
            List<String> discsAbove = new ArrayList<>();
            if (line.length == 2) {
                String[] aboves = line[1].split(", ");
                for (String above : aboves) {
                    discsAbove.add(above);
                }
            }
            
            String name = line[0].split(" ")[0];
            String value = line[0].split(" ")[1].substring(1, line[0].split(" ")[1].length() - 1);
            structure.put(name, discsAbove);
            weights.put(name, Integer.parseInt(value));
        }
        reader.close();
        
        return solve(structure, weights);
    }

}
