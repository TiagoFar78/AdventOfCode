package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day25Part1 extends Challenge {
    
    private static final int MAX_HEIGHT = 5;
    
    private long solve(List<List<String>> schematics) {
        List<List<Integer>> doorLocks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();
        
        for (List<String> schematic : schematics) {
            List<List<Integer>> l = schematic.get(0).charAt(0) == '.' ? doorLocks : keys;
            l.add(toHeights(schematic));
        }
        
        int successfulMatches = 0;
        for (List<Integer> doorLock : doorLocks) {
            for (List<Integer> key : keys) {
                if (fit(doorLock, key)) {
                    successfulMatches++;
                }
            }
        }
        
        return successfulMatches;
    }
    
    private List<Integer> toHeights(List<String> schematic) {
        List<Integer> heights = new ArrayList<>();
        for (int col = 0; col < schematic.get(0).length(); col++) {
            int filled = 0;
            for (int row = 0; row < schematic.size(); row++) {
                if (schematic.get(row).charAt(col) == '#') {
                    filled++;
                }
            }
            
            heights.add(filled - 1);
        }
        
        return heights;
    }
    
    private boolean fit(List<Integer> doorLock, List<Integer> key) {
        for (int i = 0; i < doorLock.size(); i++) {
            if (doorLock.get(i) + key.get(i) > MAX_HEIGHT) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public long solve() {
        List<List<String>> schematics = new ArrayList<>();
        List<String> schematic = new ArrayList<>();
        
        Scanner reader = getInputFile();        
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.length() == 0) {
                schematics.add(schematic);
                schematic = new ArrayList<>();
                continue;
            }
            
            schematic.add(line);
        }
        schematics.add(schematic);
        reader.close();
        
        return solve(schematics);
    }
}
