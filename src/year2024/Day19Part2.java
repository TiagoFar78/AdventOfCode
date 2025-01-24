package year2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day19Part2 extends Challenge {
    
    private static final int ALPHABET_COUNT = 26;
    
    private class Trie {
        
        public String id;
        public boolean isLast;
        public Trie[] branches = new Trie[ALPHABET_COUNT];
        
        private Trie() {
            this("");
        }
        
        private Trie(String id) {
            this.id = id;
        }
        
        public void add(String s) {
            add(s.toCharArray(), 0);
        }
        
        private void add(char[] s, int index) {
            if (index == s.length) {
                isLast = true;
                return;
            }
            
            if (branches[s[index] - 'a'] == null) {
                branches[s[index] - 'a'] = new Trie(id + s[index]);
            }
            branches[s[index] - 'a'].add(s, index + 1);
        }
        
    }
    
    private long solve(List<String> availableStripes, List<String> targetStripes) {
        long possibilities = 0;
        
        Trie root = new Trie();
        for (String availableStripe : availableStripes) {
            root.add(availableStripe);
        }
        
        Map<String, Long> seen = new HashMap<>();
        for (String target : targetStripes) {
            possibilities += countPossibilities(seen, root, target);
        }
        
        return possibilities;
    }
    
    private long countPossibilities(Map<String, Long> seen, Trie root, String target) {
        return countPossibilities(seen, root, root, target);
    }
    
    private long countPossibilities(Map<String, Long> seen, Trie root, Trie current, String target) {
        if (target.length() == 0) {
            return current.isLast ? 1 : 0;
        }
        
        String key = current.id + " " + target;
        if (seen.containsKey(key)) {
            return seen.get(key);
        }
        
        long possibilities = 
                (current.branches[target.charAt(0) - 'a'] != null ? countPossibilities(seen, root, current.branches[target.charAt(0) - 'a'], target.substring(1)) : 0) +
                (current.isLast ? countPossibilities(seen, root, root, target) : 0);
        
        seen.put(key, possibilities);
        return possibilities;
    }
    
    @Override
    public long solve() {
        List<String> availableStripes = new ArrayList<>();
        List<String> targetStripes = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("[a-z]+");
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        while (matcher.find()) {
            availableStripes.add(matcher.group());
        }
        
        reader.nextLine();
        while (reader.hasNextLine()) {
            targetStripes.add(reader.nextLine());
        }
        reader.close();
        
        return solve(availableStripes, targetStripes);
    }
}