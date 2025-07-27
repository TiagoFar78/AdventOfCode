package year2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day24Part2 extends Challenge {
    
    private int solve(List<String> gridList) {
        Map<Integer, boolean[][]> grid = new HashMap<>();
        int n = gridList.size();
        grid.put(0, new boolean[n][n]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid.get(0)[i][j] = gridList.get(i).charAt(j) == '#';
            }
        }
        
        int minutes = 200;
        for (int i = 0; i < minutes; i++) {
            grid = updateGrid(grid, n);
        }
        
        int bugs = 0;
        for (boolean[][] value : grid.values()) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (value[i][j]) {
                        bugs++;
                    }
                }
            }
        }
        
        return bugs;
    }
    
    private Map<Integer, boolean[][]> updateGrid(Map<Integer, boolean[][]> grids, int n) {
        int minDepth = Collections.min(grids.keySet()) - 1;
        int maxDepth = Collections.max(grids.keySet()) + 1;
        
        Map<Integer, boolean[][]> newGrids = new HashMap<>();
        for (int depth = minDepth; depth <= maxDepth; depth++) {
            boolean[][] newGrid = new boolean[n][n]; 
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 2 && j == 2) {
                        continue;
                    }
                    
                    int adjacentBugs = 0;
                    for (int[] adj : getAdjacents(grids, n, i, j, depth)) {
                        boolean[][] adjLevel = grids.getOrDefault(adj[2], new boolean[n][n]);
                        if (adjLevel[adj[0]][adj[1]]) {
                            adjacentBugs++;
                        }
                    }
                    
                    boolean hasBug = grids.getOrDefault(depth, new boolean[n][n])[i][j];
                    if ((hasBug && adjacentBugs == 1) || (!hasBug && (adjacentBugs == 1 || adjacentBugs == 2))) {
                        newGrid[i][j] = true;
                    }
                }
            }
            
            if (!isEmpty(newGrid, n)) {
                newGrids.put(depth, newGrid);
            }
        }
        
        return newGrids;
    }
    
    private List<int[]> getAdjacents(Map<Integer, boolean[][]> grids, int n, int i, int j, int depth) {
        List<int[]> adj = new ArrayList<>();

        // Up
        if (j == 0) {
            adj.add(new int[] { 2, 1, depth - 1});
        } 
        else if (i == 2 && j == 3) {
            for (int k = 0; k < n; k++) {
                adj.add(new int[] { k, 4, depth + 1 });
            }
        } 
        else {
            adj.add(new int[] { i, j - 1, depth });
        }

        // Down
        if (j == 4) {
            adj.add(new int[] { 2, 3, depth - 1 });
        }
        else if (i == 2 && j == 1) {
            for (int k = 0; k < n; k++) {
                adj.add(new int[] { k, 0, depth + 1 });
            }
        }
        else {
            adj.add(new int[] { i, j + 1, depth });
        }

        // Left
        if (i == 0) {
            adj.add(new int[] { 1, 2, depth - 1 });
        }
        else if (i == 3 && j == 2) {
            for (int k = 0; k < n; k++) {
                adj.add(new int[] { 4, k, depth + 1 });
            }
        }
        else {
            adj.add(new int[] { i - 1, j, depth });
        }

        // Right
        if (i == 4) {
            adj.add(new int[] { 3, 2, depth - 1 });
        }
        else if (i == 1 && j == 2) {
            for (int k = 0; k < n; k++) {
                adj.add(new int[] { 0, k, depth + 1 });
            }
        }
        else {
            adj.add(new int[] { i + 1, j, depth });
        }

        return adj;
    }
    
    private boolean isEmpty(boolean[][] grid, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j]) {
                    return false;
                }
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
