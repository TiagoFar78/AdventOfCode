package year2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day04Part2 extends Challenge {
    
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
        Set<String> removed = new HashSet<>();
        while (true) {
            int roundRemoved = removeAccessible(diagram, n, m, removed);
            if (roundRemoved == 0) {
                break;
            }
            
            accessible += roundRemoved;
        }
        
        return accessible;
    }
    
    private int removeAccessible(List<String> diagram, int n, int m, Set<String> removed) {
        Set<String> newRemoved = new HashSet<>();
        int accessible = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (diagram.get(i).charAt(j) == '@' && !removed.contains(i + " " + j) && isAccessible(diagram, i, j, n, m, removed)) {
                    accessible++;
                    newRemoved.add(i + " " + j);
                }
            }
        }
        
        removed.addAll(newRemoved);
        return accessible;
    }
    
    private boolean isAccessible(List<String> diagram, int i, int j, int n, int m, Set<String> removed) {
        int arround = 0;
        for (int[] dir : DIRECTIONS) {
            int row = i + dir[0];
            int col = j + dir[1];
            if (row < 0 || row >= n || col < 0 || col >= m || removed.contains(row + " " + col)) {
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
