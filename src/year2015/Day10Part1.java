package year2015;

import java.util.Scanner;

import main.Challenge;

public class Day10Part1 extends Challenge {
    
    private final static int OPERATIONS = 40;
    
    private int solve(String input) {
        for (int i = 0; i < OPERATIONS; i++) {
            input = lookAndSay(input);
        }
        
        return input.length();
    }
    
    private String lookAndSay(String input) {
        StringBuilder sb = new StringBuilder();
        
        char prev = input.charAt(0);
        int count = 1;
        for (int i = 1; i < input.length(); i++) {
            if (prev != input.charAt(i)) {
                sb.append(count);
                sb.append(prev);
                count = 0;
            }
            
            prev = input.charAt(i);
            count++;
        }
        sb.append(count);
        sb.append(prev);
        
        return sb.toString();
    }

    @Override
    public long solve() {        
        Scanner reader = getInputFile();
        String input = reader.nextLine();
        reader.close();
        
        return solve(input);
    }

}
