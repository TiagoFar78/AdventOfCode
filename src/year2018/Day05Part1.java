package year2018;

import java.util.Scanner;
import java.util.Stack;

import main.Challenge;

public class Day05Part1 extends Challenge {
    
    private final static int OPPOSITE_POLARITY = Math.abs('a' - 'A');
    
    private int solve(String polymer) {
        Stack<Character> remaining = new Stack<>();
        for (char c : polymer.toCharArray()) {
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
