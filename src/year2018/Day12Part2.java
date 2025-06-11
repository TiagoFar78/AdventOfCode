package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day12Part2 extends Challenge {
    
    private long solve(String initialState, Map<String, Boolean> plantSpreadNotesString, long generations) {
        Set<Integer> plantSpreadNotes = new HashSet<>();
        for (String key : plantSpreadNotesString.keySet()) {
            if (plantSpreadNotesString.get(key)) {
                plantSpreadNotes.add(toDecimal(key));
            }
        }
        
        Set<Integer> plantPositions = new HashSet<>();
        for (int i = 0; i < initialState.length(); i++) {
            if (initialState.charAt(i) == '#') {
                plantPositions.add(i);
            }
        }

        Map<String, int[]> seen = new HashMap<>();
        int i = 0;
        for (; i < generations; i++) {
            String key = cumulativeDiff(plantPositions);
            if (seen.containsKey(key)) {
                break;
            }
            seen.put(key, new int[] { i, min(plantPositions) });
            
            plantPositions = calculateNextGeneration(plantPositions, plantSpreadNotes);
        }
        
        int[] prev = seen.get(cumulativeDiff(plantPositions));
        generations -= i;
        long shiftAmount = (long) ((generations / (i - prev[0])) * (min(plantPositions) - prev[1]));
        generations %= i - prev[0];

        for (; generations > 0; generations--) {
            plantPositions = calculateNextGeneration(plantPositions, plantSpreadNotes);
        }
        
        int sum = 0;
        for (int plantPosition : plantPositions) {
            sum += plantPosition;
        }
        
        return sum + shiftAmount * plantPositions.size();
    }
    
    private String cumulativeDiff(Set<Integer> plantPositions) {
        String[] cumulativeDiff = new String[plantPositions.size() - 1];
        List<Integer> plantPositionsList = new ArrayList<>(plantPositions);
        plantPositionsList.sort((a, b) -> a - b);
        
        for (int i = 0; i < plantPositionsList.size() - 1; i++) {
            cumulativeDiff[i] = Integer.toString(plantPositionsList.get(i + 1) - plantPositionsList.get(i));
        }
        
        return String.join(" ", cumulativeDiff);
    }
    
    private int min(Set<Integer> plantPositions) {
        int min = Integer.MAX_VALUE;
        for (int num : plantPositions) {
            min = Math.min(num, min);
        }
        
        return min;
    }
    
    private Set<Integer> calculateNextGeneration(Set<Integer> plantPositions, Set<Integer> plantSpreadNotes) {
        Set<Integer> newGeneration = new HashSet<>();
        
        for (int plantPosition : plantPositions) {
            for (int i = -2; i < 3; i++) {
                if (isPlantIn(plantPosition + i, plantPositions, plantSpreadNotes)) {
                    newGeneration.add(plantPosition + i);
                }
            }
        }
        
        return newGeneration;
    }
    
    private boolean isPlantIn(int position, Set<Integer> plantPositions, Set<Integer> plantSpreadNotes) {
        int decimal = 0;
        for (int i = -2; i < 3; i++) {
            if (plantPositions.contains(position + i)) {
                decimal += Math.pow(2,  i + 2);
            }
        }
        
        return plantSpreadNotes.contains(decimal);
    }
     
    private int toDecimal(String binary) {
        int decimal = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '#') {
                decimal += Math.pow(2, i);
            }
        }
        
        return decimal;
    }
    
    @Override
    public long solve() {
        Map<String, Boolean> plantSpreadNotes = new HashMap<>();
        
        Scanner reader = getInputFile();
        String initialState = reader.nextLine().substring("initial state: ".length());
        reader.nextLine();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" => ");
            plantSpreadNotes.put(line[0], line[1].equals("#"));
        }
        reader.close();
        
        long generations = 50000000000L;
        return solve(initialState, plantSpreadNotes, generations);
    }
    
}
