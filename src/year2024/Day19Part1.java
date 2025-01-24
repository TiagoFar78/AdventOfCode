package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day19Part1 extends Challenge {
    
    private static final int ALPHABET_COUNT = 26;
    
    private class Trie {
        
        public boolean isLast;
        public Trie[] branches = new Trie[ALPHABET_COUNT];
        
        public void add(String s) {
            add(s.toCharArray(), 0);
        }
        
        private void add(char[] s, int index) {
            if (index == s.length) {
                isLast = true;
                return;
            }
            
            if (branches[s[index] - 'a'] == null) {
                branches[s[index] - 'a'] = new Trie();
            }
            branches[s[index] - 'a'].add(s, index + 1);
        }
        
    }
    
    private int solve(List<String> availableStripes, List<String> targetStripes) {
        int possible = 0;
        
        Trie root = new Trie();
        for (String availableStripe : availableStripes) {
            root.add(availableStripe);
        }
        
        for (String target : targetStripes) {
            possible += isPossible(root, target) ? 1 : 0;
        }
        
        return possible;
    }
    
    private boolean isPossible(Trie root, String target) {
        return isPossible(root, root, target.toCharArray(), 0);
    }
    
    private boolean isPossible(Trie root, Trie current, char[] target, int i) {
        if (target.length == i && !current.isLast) {
            return false;
        }
        
        return (target.length == i && current.isLast) || 
                (current.branches[target[i] - 'a'] != null && isPossible(root, current.branches[target[i] - 'a'], target, i + 1)) ||
                (current.isLast && isPossible(root, root, target, i));
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