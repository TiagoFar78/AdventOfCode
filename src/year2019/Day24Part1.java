package year2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day24Part1 extends Challenge {
    
    private int solve(List<String> gridList) {
        int grid = 0;
        int n = gridList.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (gridList.get(i).charAt(j) == '#') {
                    grid |= 1 << (i * n + j);
                }
            }
        }
        
        Set<Integer> seen = new HashSet<>();
        while (!seen.contains(grid)) {
            seen.add(grid);
            grid = updateGrid(grid, n);
        }
        
        return grid;
    }
    
    private int updateGrid(int grid, int n) {
        int newGrid = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cell = i * n + j;
                int adjacentBugs = 0;
                if (j + 1 < n && (grid & (1 << cell + 1)) != 0) {
                    adjacentBugs++;
                }
                
                if (j - 1 >= 0 && (grid & (1 << cell - 1)) != 0) {
                    adjacentBugs++;
                }
                
                if (i + 1 < n && (grid & (1 << cell + n)) != 0) {
                    adjacentBugs++;
                }

                if (i - 1 >= 0 && (grid & (1 << cell - n)) != 0) {
                    adjacentBugs++;
                }
                
                boolean hasBug = (grid & (1 << cell)) != 0;
                if ((hasBug && adjacentBugs == 1) || (!hasBug && (adjacentBugs == 1 || adjacentBugs == 2))) {
                    newGrid |= 1 << cell;
                }
            }
        }
        
        return newGrid;
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
