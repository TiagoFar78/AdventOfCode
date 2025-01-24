package year2015;

import java.util.Scanner;

import main.Challenge;

public class Day01Part2 extends Challenge {
    
    private int solve(String s) {
        int floor = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                floor++;
            }
            else {
                floor--;
            }
            
            if (floor == -1) {
                return i + 1;
            }
        }
        
        return -1;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        return solve(line);
    }

}
