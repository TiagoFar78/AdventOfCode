package Dec2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day04Part1 extends Challenge {
    
    private static final int[][] DIRECTIONS = { 
        { 1, 0 },
        { -1, 0 },
        { 0, 1 },
        { 0, -1 },
        { 1, 1 },
        { 1, -1 },
        { -1, 1 },
        { -1, -1 }
    };
    
    private static final char[] XMAS = { 'X', 'M', 'A', 'S'};

    private int solve(List<String> grid) {
        int n = grid.size();
        int m = grid.get(0).length();
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid.get(i).charAt(j) == 'X') {
                    for (int[] direction : DIRECTIONS) {
                        if (hasXMAS(grid, i, j, direction, n, m)) {
                            count++;
                        }
                    }
                }
            }
        }
        
        return count;
    }
    
    private boolean hasXMAS(List<String> grid, int i, int j, int[] direction, int n, int m) {
        for (int k = 1; k < 4; k++) {
            int row = i + direction[1] * k;
            int col = j + direction[0] * k;
            if (row < 0 || row >= n || col < 0 || col >= m || grid.get(row).charAt(col) != XMAS[k]) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public long solve() {
        List<String> grid = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            grid.add(reader.nextLine());
        }
        reader.close();
        
        return solve(grid);
    }

}
