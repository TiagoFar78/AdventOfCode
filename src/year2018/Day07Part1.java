package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day07Part1 extends Challenge {
    
    private String solve(Map<Character, List<Character>> unlocks, Map<Character, Integer> dependencySize) {
        StringBuilder sb = new StringBuilder();
        Queue<Character> pq = new PriorityQueue<>();
        
        Set<Character> firstUnlocked = new HashSet<>(unlocks.keySet());
        firstUnlocked.removeAll(dependencySize.keySet());
        pq.addAll(firstUnlocked);
        
        while (!pq.isEmpty()) {
            char c = pq.poll();
            
            List<Character> unlock = unlocks.get(c);
            if (unlock == null) {
                unlock = new ArrayList<>();
            }
            
            for (Character letter : unlock) {
                dependencySize.put(letter, dependencySize.get(letter) - 1);
                if (dependencySize.get(letter) == 0) {
                    pq.add(letter);
                }
            }
            sb.append(c);
        }
        
        return sb.toString();
    }
    
    @Override
    public String solveString() {
        Map<Character, List<Character>> unlocks = new HashMap<>();
        Map<Character, Integer> dependencySize = new HashMap<>();
        
        Pattern pattern = Pattern.compile("Step (.) must be finished before step (.) can begin.");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            char key = matcher.group(1).charAt(0);
            char value = matcher.group(2).charAt(0);
            
            if (!unlocks.containsKey(key)) {
                unlocks.put(key, new ArrayList<>());
            }
            unlocks.get(key).add(value);
            
            dependencySize.put(value, dependencySize.getOrDefault(value, 0) + 1);
        }
        reader.close();
        
        return solve(unlocks, dependencySize);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
