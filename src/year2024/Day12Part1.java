package year2024;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import main.Challenge;

public class Day12Part1 extends Challenge {
    
    private final static int[][] DIRECTIONS = {
            { 1, 0 }, 
            { 0, 1 },
            { -1, 0 },
            { 0, -1 }
    };
    
    private long solve(List<String> garden) {
        int n = garden.size();
        int m = garden.get(0).length();
        int totalCost = 0;
        
        boolean[][] isCellExplored = new boolean[n][m]; 
        Queue<int[]> newRegionQueue = new LinkedList<>();
        Queue<int[]> currentQueue =  new LinkedList<>();
        
        newRegionQueue.add(new int[] { 0, 0 });
        while (!newRegionQueue.isEmpty()) {
            currentQueue.add(newRegionQueue.poll());
            int area = 0;
            int perimeter = 0;
            while (!currentQueue.isEmpty()) {
                int[] cell = currentQueue.poll();
                int row = cell[0];
                int col = cell[1];
                
                if (isCellExplored[row][col]) {
                    continue;
                }
                isCellExplored[row][col] = true;
                
                area++;
                for (int[] direction : DIRECTIONS) {
                    int newRow = row + direction[0];
                    int newCol = col + direction[1];
                    
                    if (!isValid(newRow, newCol, n, m)) {
                        perimeter++;
                        continue;
                    }
                    
                    int[] newCell = new int[] { newRow, newCol };
                    if (garden.get(row).charAt(col) == garden.get(newRow).charAt(newCol)) {
                        currentQueue.add(newCell);
                    }
                    else {
                        perimeter++;
                        newRegionQueue.add(newCell);
                    }
                }
            }
            
            totalCost += area * perimeter;
        }
        
        return totalCost;
    }
    
    private boolean isValid(int row, int col, int n, int m) {
        return row >= 0 && col >= 0 && row < n && col < m;
    }
    
    @Override
    public long solve() {
        List<String> garden = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            garden.add(reader.nextLine());
        }
        reader.close();
        
        return solve(garden);
    }

}
