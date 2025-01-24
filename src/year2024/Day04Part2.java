package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day04Part2 extends Challenge {

    private int solve(List<String> grid) {
        int n = grid.size();
        int m = grid.get(0).length();
        
        int count = 0;
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (grid.get(i).charAt(j) == 'A' && hasXMAS(grid, i, j)) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    private boolean hasXMAS(List<String> grid, int i, int j) {
        char c1 = grid.get(i - 1).charAt(j - 1);
        char c2 = grid.get(i - 1).charAt(j + 1);
        char c3 = grid.get(i + 1).charAt(j + 1);
        char c4 = grid.get(i + 1).charAt(j - 1);
        
        return ((c1 == 'M' && c3 == 'S') || (c1 == 'S' && c3 == 'M')) && ((c2 == 'M' && c4 == 'S') || (c2 == 'S' && c4 == 'M'));
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
