package year2019;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day06Part2 extends Challenge {
    
    private int solve(Map<String, String> orbits) {
        Map<String, Integer> youOrbits = new HashMap<>();
        int steps = 0;
        String current = orbits.get("YOU");
        while (orbits.containsKey(current)) {
            youOrbits.put(current, steps);
            steps++;
            current = orbits.get(current);
        }
        
        current = orbits.get("SAN");
        steps = 0;
        while (!youOrbits.containsKey(current)) {
            steps++;
            current = orbits.get(current);
        }
        
        return steps + youOrbits.get(current);
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
