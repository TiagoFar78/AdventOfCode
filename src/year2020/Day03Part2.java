package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day03Part2 extends Challenge {
    
    private long solve(List<String> park) {
        long mult = 1;
        
        int[][] slopes = new int[][] {
            { 1, 1 },
            { 1, 3 },
            { 1, 5 },
            { 1, 7 },
            { 2, 1 }
        };
        
        for (int[] slope : slopes) {
            mult *= treesFound(park, slope[0], slope[1]);
        }
        
        return mult;
    }
    
    private int treesFound(List<String> park, int rowMove, int colMove) {
        int n = park.get(0).length();
        
        int row = rowMove;
        int col = colMove;
        
        int trees = 0;
        while (row < park.size()) {
            if (park.get(row).charAt(col) == '#') {
                trees++;
            }
            
            row += rowMove;
            col = (col + colMove) % n;
        }
        
        return trees;
    }

    @Override
    public long solve() {
        List<String> park = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            park.add(reader.nextLine());        
        }
        reader.close();
        
        return solve(park);
    }

}
