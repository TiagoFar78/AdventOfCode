package year2019;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day06Part1 extends Challenge {
    
    private int solve(Map<String, String> orbits) {
        int totalOrbits = 0;
        Map<String, Integer> orbitCount = new HashMap<>();
        orbitCount.put("COM", 0);
        
        for (String key : orbits.keySet()) {
            totalOrbits += countOrbits(orbits, orbitCount, key);
        }
        
        return totalOrbits;
    }
    
    private int countOrbits(Map<String, String> orbits, Map<String, Integer> orbitCount, String current) {
        if (orbitCount.containsKey(current)) {
            return orbitCount.get(current);
        }
        
        int count = 1 + countOrbits(orbits, orbitCount, orbits.get(current));
        orbitCount.put(current, count);
        return count;
    }

    @Override
    public long solve() {
        Map<String, String> orbits = new HashMap<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split("\\)");
            orbits.put(line[1], line[0]);
        }
        reader.close();
        
        return solve(orbits);
    }

}
