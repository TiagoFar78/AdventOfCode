
package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day18Part2 extends Challenge {
    
    private static final int[][] DIR = {
            { -1, -1 },
            { -1, 0 },
            { -1, 1 },
            { 0, 1 },
            { 1, 1 },
            { 1, 0 },
            { 1, -1 },
            { 0, -1 }
    };
    
    private int solve(List<String> gridString) {
        int n = gridString.size();
        int m = gridString.get(0).length();
        
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = gridString.get(i).charAt(j);
                grid[i][j] = c == '.' ? 0 : c == '|' ? 1 : 2;
            }
        }

        int minutes = 1_000_000_000;
        int minute = 0;
        Map<String, Integer> seen = new HashMap<>();
        while (minute < minutes) {
            grid = step(grid, n, m);
            minute++;
            String key = toString(grid, n, m);
            if (seen.containsKey(key)) {
                break;
            }
            seen.put(key, minute);
        }
        
        if (minute < minutes) {
            int minutesLeft = minutes - minute;
            minutesLeft %= minute - seen.get(toString(grid, n, m));
            while (minutesLeft > 0) {
                minutesLeft--;
                grid = step(grid, n, m);
            }
        }
        
        int woodedAcres = 0;
        int lumberyards = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    woodedAcres++;
                }
                else if (grid[i][j] == 2) {
                    lumberyards++;
                }
            }
        }
        
        return woodedAcres * lumberyards;
    }
    
    private String toString(int[][] grid, int n, int m) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(grid[i][j]);
            }
        }
        
        return sb.toString();
    }
    
    private int[][] step(int[][] grid, int n, int m) {
        int[][] newGrid = new int[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0 || grid[i][j] == 1) {
                    int target = grid[i][j] + 1;
                    
                    if (countTarget(grid, i, j, n, m, target) >= 3) {
                        newGrid[i][j] = grid[i][j] + 1;
                    }
                    else {
                        newGrid[i][j] = grid[i][j];
                    }
                }
                else {
                    if (countTarget(grid, i, j, n, m, 1) >= 1 && countTarget(grid, i, j, n, m, 2) >= 1) {
                        newGrid[i][j] = grid[i][j];
                    }
                    else {
                        newGrid[i][j] = 0;
                    }
                }
            }
        }
        
        return newGrid;
    }
    
    private int countTarget(int[][] grid, int i, int j, int n, int m, int target) {
        int count = 0;
        for (int[] dir : DIR) {
            int row = i + dir[0];
            int col = j + dir[1];
            if (row < 0 || row >= n || col < 0 || col >= m) {
                continue;
            }
            
            if (grid[row][col] == target) {
                count++;
            }
        }
        
        return count;
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
