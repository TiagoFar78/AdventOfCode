package year2025;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day11Part2 extends Challenge {
    
    private long solve(Map<String, List<String>> connections, String starting, String target) {
        return solve(connections, starting, target, new HashMap<>(), false, false);
    }
    
    private long solve(Map<String, List<String>> connections, String starting, String target, Map<String, Long> seen, boolean passedDac, boolean passedFft) {
        if (starting.equals(target)) {
            return passedDac && passedFft ? 1 : 0;
        }
        
        String key = starting + " " + (passedDac ? "1" : "0") + " " + (passedFft ? "1" : "0");
        if (seen.containsKey(key)) {
            return seen.get(key);
        }
        
        long ways = 0;
        for (String connection : connections.get(starting)) {
            ways += solve(connections, connection, target, seen, passedDac || connection.equals("dac"), passedFft || connection.equals("fft"));
        }
        
        seen.put(key, ways);
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
        
        String start = "svr";
        String target = "out";
        return solve(connections, start, target);        
    }

}
