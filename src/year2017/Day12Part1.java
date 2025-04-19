package year2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day12Part1 extends Challenge {
    
    private int solve(Map<Integer, int[]> pipes) {
        Set<Integer> seen = new HashSet<>();
        
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
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
        
        return seen.size();
    }
    
    @Override
    public long solve() {
        Map<Integer, int[]> pipes = new HashMap<>();
        
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
        }
        reader.close();
        
        return solve(pipes);
    }

}
