package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day07Part1 extends Challenge {
    
    private String solve(Map<String, List<String>> structure) {
        Set<String> total = new HashSet<>(structure.keySet());
        
        for (String disc : structure.keySet()) {
            for (String element : structure.get(disc)) {
                total.remove(element);
            }
        }
        
        return total.iterator().next();
    }

    @Override
    public String solveString() {
        Map<String, List<String>> structure = new HashMap<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" -> ");
            List<String> discsAbove = new ArrayList<>();
            if (line.length == 2) {
                String[] aboves = line[1].split(", ");
                for (String above : aboves) {
                    discsAbove.add(above);
                }
            }
            
            structure.put(line[0].split(" ")[0], discsAbove);
        }
        reader.close();
        
        return solve(structure);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
