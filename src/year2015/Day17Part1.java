package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day17Part1 extends Challenge {
    
    private final static int LITTERS = 150;
    
    private int solve(List<Integer> containers) {
        return getPossibilities(containers, 0, LITTERS);
    }
    
    private int getPossibilities(List<Integer> containers, int i, int target) {
        if (target == 0) {
            return 1;
        }
        
        if (target < 0 || i == containers.size()) {
            return 0;
        }
        
        return getPossibilities(containers, i + 1, target - containers.get(i)) + getPossibilities(containers, i + 1, target);
    }
    
    @Override
    public long solve() {
        List<Integer> containers = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            containers.add(Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(containers);
    }

}
