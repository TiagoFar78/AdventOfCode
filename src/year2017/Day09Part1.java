package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day09Part1 extends Challenge {
    
    private int solve(String input) {
        int sum = 0;
        
        int currentNestIndex = 0;
        boolean isInTrash = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '!') {
                i++;
                continue;
            }
            
            if (isInTrash) {
                if(c == '>') {
                    isInTrash = false;
                }
                continue;
            }
            
            if (c == '{') {
                currentNestIndex++;
            }
            else if (c == '}') {
                sum += currentNestIndex;
                currentNestIndex--;
            }
            else if (c == '<') {
                isInTrash = true;
            }
        }
        
        return sum;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String input = reader.nextLine();
        reader.close();
        
        return solve(input);
    }

}
