package year2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day10Part1 extends Challenge {
    
    private final static int[][] DIRECTIONS = {
            { 1, 0 }, 
            { 0, 1 },
            { -1, 0 },
            { 0, -1 }
    };
    
    private long solve(List<List<Integer>> map) {
        int n = map.size();
        int m = map.get(0).size();
        int totalScore = 0;
        
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (map.get(row).get(col) == 0) {
                    totalScore += getScore(map, row, col, n, m);
                }
            }
        }        
        
        return totalScore;
    }
    
    private int getScore(List<List<Integer>> map, int row, int col, int n, int m) {
        Set<String> ninesFound = new HashSet<>();
        getScore(map, row, col, n, m, ninesFound);
        return ninesFound.size();
    }
    
    private void getScore(List<List<Integer>> map, int row, int col, int n, int m, Set<String> ninesFound) {
        if (map.get(row).get(col) == 9) {
            ninesFound.add(row + " " + col);
            return;
        }
        
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValid(newRow, newCol, n, m) && map.get(row).get(col) + 1 == map.get(newRow).get(newCol)) {
                getScore(map, newRow, newCol, n, m, ninesFound);
            }
        }
    }
    
    private boolean isValid(int row, int col, int n, int m) {
        return row >= 0 && col >= 0 && row < n && col < m;
    }
    
    @Override
    public long solve() {
        List<List<Integer>> map = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            List<Integer> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c - '0');
            }
            map.add(row);
        }
        reader.close();
        
        return solve(map);
    }

}
