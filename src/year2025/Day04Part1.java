package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day04Part1 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
            { -1, 0 },
            { -1, 1 },
            { 0, 1 },
            { 1, 1 },
            { 1, 0 },
            { 1, -1 },
            { 0, -1 },
            { -1, -1 },
    };
    
    private long solve(List<String> diagram) {
        int n = diagram.size();
        int m = diagram.get(0).length();
        int accessible = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (diagram.get(i).charAt(j) == '@' && isAccessible(diagram, i, j, n, m)) {
                    accessible++;
                }
            }
        }
        
        return accessible;
    }
    
    private boolean isAccessible(List<String> diagram, int i, int j, int n, int m) {
        int arround = 0;
        for (int[] dir : DIRECTIONS) {
            int row = i + dir[0];
            int col = j + dir[1];
            if (row < 0 || row >= n || col < 0 || col >= m) {
                continue;
            }
            
            if (diagram.get(row).charAt(col) == '@') {
                arround++;
            }
        }
        
        int max = 4;
        return arround < max;
    }
    
    @Override
    public long solve() {
        List<String> diagram = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            diagram.add(reader.nextLine());
        }
        reader.close();
        
        return solve(diagram);
    }

}
