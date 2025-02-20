package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day17Part2 extends Challenge {
    
    private final static int LITTERS = 150;
    
    private int solve(List<Integer> containers) {
        int minContainersNeeded =  getMinContainersPossibble(containers, 0, LITTERS);
        
        return getPossibilities(containers, 0, LITTERS, minContainersNeeded);
    }
    
    private int getPossibilities(List<Integer> containers, int i, int target, int usedContainers) {
        if (target == 0) {
            return usedContainers == 0 ? 1 : 0;
        }
        
        if (target < 0 || i == containers.size()) {
            return 0;
        }
        
        return getPossibilities(containers, i + 1, target - containers.get(i), usedContainers - 1) + getPossibilities(containers, i + 1, target, usedContainers);
    }
    
    private int getMinContainersPossibble(List<Integer> containers, int i, int target) {
        if (target == 0) {
            return 0;
        }
        
        if (target < 0 || i == containers.size()) {
            return containers.size() + 1;
        }
        
        return Math.min(1 + getMinContainersPossibble(containers, i + 1, target - containers.get(i)), getMinContainersPossibble(containers, i + 1, target));
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
