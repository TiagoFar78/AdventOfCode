package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day13Part1 extends Challenge {
    
    private int solve(List<int[]> layers) {
        int severity = 0;
        
        for (int[] layer : layers) {
            if (isInScanner(layer[0], layer[1])) {
                severity += layer[0] * layer[1];
            }
        }
        
        return severity;
    }
    
    private boolean isInScanner(int depth, int range) {
        return depth % ((range - 1) * 2) == 0;
    }
    
    @Override
    public long solve() {
        List<int[]> layers = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] parts = reader.nextLine().split(": ");
            int[] layer = new int[2];
            layer[0] = Integer.parseInt(parts[0]);
            layer[1] = Integer.parseInt(parts[1]);
            layers.add(layer);
        }
        reader.close();
        
        return solve(layers);
    }

}
