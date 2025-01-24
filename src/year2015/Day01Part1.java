package year2015;

import java.util.Scanner;

import main.Challenge;

public class Day01Part1 extends Challenge {
    
    private int solve(String s) {
        int floor = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                floor++;
            }
            else {
                floor--;
            }
        }
        
        return floor;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        return solve(line);
    }

}
