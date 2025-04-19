package year2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day12Part2 extends Challenge {
    
    private int solve(Map<Integer, int[]> pipes, int max) {
        Set<Integer> seen = new HashSet<>();
        int groups = 0;
        
        for (int i = 0; i <= max; i++) {
            if (!seen.contains(i)) {
                groups++;
                seen.addAll(findGroup(pipes, i));
            }
        }
        
        return groups;
    }
    
    private Set<Integer> findGroup(Map<Integer, int[]> pipes, int starting) {
        Set<Integer> seen = new HashSet<>();
        
        Queue<Integer> queue = new LinkedList<>();
        queue.add(starting);
        while (!queue.isEmpty()) {
            int element = queue.poll();
            if (seen.contains(element)) {
                continue;
            }
            
            seen.add(element);
            
            for (int pipe : pipes.get(element)) {
                queue.add(pipe);
            }
        }
        
        return seen;
    }
    
    @Override
    public long solve() {
        Map<Integer, int[]> pipes = new HashMap<>();
        int max = 0;
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] lineParts = reader.nextLine().split(" <-> ");
            
            int key = Integer.parseInt(lineParts[0]);
            String[] valueString = lineParts[1].split(", ");
            int[] value = new int[valueString.length];
            for (int i = 0; i < valueString.length; i++) {
                value[i] = Integer.parseInt(valueString[i]);
            }
            pipes.put(key, value);
            
            max = Math.max(max, key);
        }
        reader.close();
        
        return solve(pipes, max);
    }

}
