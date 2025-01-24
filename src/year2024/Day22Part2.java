package year2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day22Part2 extends Challenge {
    
    private final static int EXPANSIONS = 2000;
    private final static int SEQUENCE_SIZE = 4;
    
    private long solve(List<Integer> secretNumbers) {
        Map<String, Integer> sequencesValue = new HashMap<>();
        for (int secretNumber : secretNumbers) {
            populate(sequencesValue, secretNumber);
        }
        
        int max = 0;
        for (int value : sequencesValue.values()) {
            max = Math.max(max, value);
        }
        
        return max;
    }
    
    private void populate(Map<String, Integer> sequencesValue, int secretNumber) {
        Set<String> found = new HashSet<>();
        
        int lastDigit = secretNumber % 10;
        int[] sequence = new int[SEQUENCE_SIZE];
        for (int i = 0; i < SEQUENCE_SIZE; i++) {
            secretNumber = evolve(secretNumber);
            int currentDigit = secretNumber % 10;
            sequence[i] = currentDigit - lastDigit;
            lastDigit = currentDigit;
        }
        addSequence(sequencesValue, sequence, lastDigit, 0, found);
        
        for (int i = SEQUENCE_SIZE; i < EXPANSIONS; i++) {
            secretNumber = evolve(secretNumber);
            int currentDigit = secretNumber % 10;
            sequence[i % SEQUENCE_SIZE] = currentDigit - lastDigit;
            lastDigit = currentDigit;
            addSequence(sequencesValue, sequence, currentDigit, (i + 1) % SEQUENCE_SIZE, found);
        }
    }
    
    private int evolve(long secretNumber) {
        secretNumber ^= secretNumber * 64;
        secretNumber = secretNumber % 16777216;
        secretNumber ^= secretNumber / 32;
        secretNumber = secretNumber % 16777216;
        secretNumber ^= secretNumber * 2048;
        secretNumber = secretNumber % 16777216;

        return (int) secretNumber;
    }
    
    private void addSequence(Map<String, Integer> sequencesValue, int[] sequence, int value, int s, Set<String> found) {
        String key = sequence[s] + " "  + sequence[(s + 1) % SEQUENCE_SIZE] + " " + sequence[(s + 2) % SEQUENCE_SIZE] + " " + sequence[(s + 3) % SEQUENCE_SIZE];
        if (!found.contains(key)) {
            sequencesValue.put(key, sequencesValue.getOrDefault(key, 0) + value);
            found.add(key);
        }
    }

    @Override
    public long solve() {
        List<Integer> secretNumbers = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            secretNumbers.add(Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(secretNumbers);
    }

}
