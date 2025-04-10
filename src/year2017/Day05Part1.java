package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day05Part1 extends Challenge {
    
    private int solve(List<Integer> jumps) {
        int i = 0;
        int steps = 0;
        while (i >= 0 && i < jumps.size()) {
            steps++;
            jumps.set(i, jumps.get(i) + 1);
            i += jumps.get(i) - 1;
        }
        
        return steps;
    }

    @Override
    public long solve() {
        List<Integer> jumps = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            jumps.add(Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(jumps);
    }

}
