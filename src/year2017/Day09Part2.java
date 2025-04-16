package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day09Part2 extends Challenge {
    
    private int solve(String input) {
        int inTrashChars = 0;
        
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
                else {
                    inTrashChars++;
                }
                continue;
            }
            
            if (c == '<') {
                isInTrash = true;
            }
        }
        
        return inTrashChars;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String input = reader.nextLine();
        reader.close();
        
        return solve(input);
    }

}
