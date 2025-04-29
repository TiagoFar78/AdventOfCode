package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day24Part1 extends Challenge {
    
    private int solve(List<int[]> componentsList) {
        Map<Integer, List<Integer>> components = new HashMap<>();
        for (int[] component : componentsList) {
            if (!components.containsKey(component[0])) {
                components.put(component[0], new ArrayList<>());
            }
            components.get(component[0]).add(component[1]);
            
            if (!components.containsKey(component[1])) {
                components.put(component[1], new ArrayList<>());
            }
            components.get(component[1]).add(component[0]);
        }
        
        return findMaxBridge(components, new HashSet<>(), 0, 0);
    }
    
    private int findMaxBridge(Map<Integer, List<Integer>> components, Set<String> seen, int lastEnd, int current) {
        int maxBridge = current;
        
        for (int usableComponent : components.get(lastEnd)) {
            String key = Math.min(lastEnd, usableComponent) + "/" + Math.max(lastEnd, usableComponent);
            if (seen.contains(key)) {
                continue;
            }
            
            Set<String> newSeen = new HashSet<>(seen);
            newSeen.add(key);
            maxBridge = Math.max(maxBridge, findMaxBridge(components, newSeen, usableComponent, current + lastEnd + usableComponent));
        }
        
        return maxBridge;
    }
    
    @Override
    public long solve() {
        List<int[]> components = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split("/");
            int[] component = new int[2];
            component[0] = Integer.parseInt(line[0]);
            component[1] = Integer.parseInt(line[1]);
            
            components.add(component);
        }
        reader.close();
        
        return solve(components);
    }
    
}
