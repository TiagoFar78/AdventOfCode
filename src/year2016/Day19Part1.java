package year2016;

import java.util.Scanner;

import main.Challenge;

public class Day19Part1 extends Challenge {
    
    private int solve(int elves) {
        int lastElf = 1;
        int elfsDistance = 1;
        while (elves > 1) {
            elfsDistance *= 2;
            lastElf += elves % 2 == 1 ? elfsDistance : 0;
            elves /= 2;
        }
        
        return lastElf;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int elves = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(elves);
    }

}
