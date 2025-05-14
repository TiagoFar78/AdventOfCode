package year2018;

import java.util.Scanner;
import java.util.Stack;

import main.Challenge;

public class Day05Part2 extends Challenge {
    
    private final static int OPPOSITE_POLARITY = Math.abs('a' - 'A');
    private final static int ALPHABET = 26;
    
    private int solve(String polymer) {
        int minReaction = Integer.MAX_VALUE;
        for (int i = 0; i < ALPHABET; i++) {
            minReaction = Math.min(minReaction, react(polymer, 'a' + i, 'A' + i));
        }
        
        return minReaction;
    }
    
    private int react(String polymer, int unit, int UNIT) {
        Stack<Character> remaining = new Stack<>();
        for (char c : polymer.toCharArray()) {
            if (c == unit || c == UNIT) {
                continue;
            }
            
            if (remaining.size() == 0) {
                remaining.add(c);
            }
            else if (Math.abs(remaining.peek() - c) == OPPOSITE_POLARITY) {
                remaining.pop();
            }
            else {
                remaining.add(c);
            }
        }
        
        return remaining.size();
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String polymer = reader.nextLine();
        reader.close();
        
        return solve(polymer);
    }

}
