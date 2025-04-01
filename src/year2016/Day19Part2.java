package year2016;

import java.util.Scanner;

import main.Challenge;

public class Day19Part2 extends Challenge {
    
    private int solve(int elves) {
        int startingElf = 1;
        while (startingElf < elves) {
            startingElf *= 3;
        }
        startingElf /= 3;
        
        elves -= startingElf;
        
        return elves <= startingElf ? elves : (elves - startingElf) * 2 + startingElf;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int elves = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(elves);
    }

}
