package year2025;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day11Part1 extends Challenge {
    
    private long solve(Map<String, List<String>> connections, String starting, String target) {
        return solve(connections, starting, target, new HashMap<>());
    }
    
    private long solve(Map<String, List<String>> connections, String starting, String target, Map<String, Long> seen) {
        if (starting.equals(target)) {
            return 1;
        }
        
        if (seen.containsKey(starting)) {
            return seen.get(starting);
        }
        
        long ways = 0;
        for (String connection : connections.get(starting)) {
            ways += solve(connections, connection, target, seen);
        }
        
        seen.put(starting, ways);
        return ways;
    }
    
    @Override
    public long solve() {
        Map<String, List<String>> connections = new HashMap<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(": ");
            connections.put(line[0], Arrays.asList(line[1].split(" ")));
        }
        reader.close();
        
        String start = "you";
        String target = "out";
        return solve(connections, start, target);        
    }

}
