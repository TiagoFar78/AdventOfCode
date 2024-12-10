package Dec2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day10Part2 extends Challenge {
    
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
        if (map.get(row).get(col) == 9) {
            return 1;
        }
        
        int score = 0;
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValid(newRow, newCol, n, m) && map.get(row).get(col) + 1 == map.get(newRow).get(newCol)) {
                score += getScore(map, newRow, newCol, n, m);
            }
        }
        
        return score;
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
