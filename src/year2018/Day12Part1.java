package year2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day12Part1 extends Challenge {
    
    private int solve(String initialState, Map<String, Boolean> plantSpreadNotesString, int generations) {
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
        
        for (; generations > 0; generations--) {
            plantPositions = calculateNextGeneration(plantPositions, plantSpreadNotes);
        }
        
        int sum = 0;
        for (int plantPosition : plantPositions) {
            sum += plantPosition;
        }
        
        return sum;
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
        
        int generations = 20;
        return solve(initialState, plantSpreadNotes, generations);
    }
    
}
