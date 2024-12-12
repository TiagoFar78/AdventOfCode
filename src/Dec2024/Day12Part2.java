package Dec2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day12Part2 extends Challenge {
    
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
        Set<String> addedSides = new HashSet<>();
        
        newRegionQueue.add(new int[] { 0, 0 });
        while (!newRegionQueue.isEmpty()) {
            currentQueue.add(newRegionQueue.poll());
            addedSides.clear();
            
            int area = 0;
            int sides = 0;
            while (!currentQueue.isEmpty()) {
                int[] cell = currentQueue.poll();
                int row = cell[0];
                int col = cell[1];
                
                if (isCellExplored[row][col]) {
                    continue;
                }
                isCellExplored[row][col] = true;
                
                area++;
                for (int directionIndex = 0; directionIndex < 4; directionIndex++) {
                    int[] direction = DIRECTIONS[directionIndex];
                    int newRow = row + direction[0];
                    int newCol = col + direction[1];
                    
                    if (!isValid(newRow, newCol, n, m)) {
                        sides += getSidesToAdd(addedSides, row, col, directionIndex);
                        addedSides.add(directionIndex + " " + row + " " + col);
                        continue;
                    }
                    
                    int[] newCell = new int[] { newRow, newCol };
                    if (garden.get(row).charAt(col) == garden.get(newRow).charAt(newCol)) {
                        currentQueue.add(newCell);
                    }
                    else {
                        sides += getSidesToAdd(addedSides, row, col, directionIndex);
                        addedSides.add(directionIndex + " " + row + " " + col);
                        newRegionQueue.add(newCell);
                    }
                }
            }
            
            totalCost += area * sides;
        }
        
        return totalCost;
    }
    
    private boolean isValid(int row, int col, int n, int m) {
        return row >= 0 && col >= 0 && row < n && col < m;
    }
    
    private int getSidesToAdd(Set<String> addedSides, int row, int col, int directionIndex) {
        int rowCellBefore = row + DIRECTIONS[getCircularIndex(directionIndex - 1)][0];
        int colCellBefore = col + DIRECTIONS[getCircularIndex(directionIndex - 1)][1];
        
        int rowCellAfter = row + DIRECTIONS[getCircularIndex(directionIndex + 1)][0];
        int colCellAfter = col + DIRECTIONS[getCircularIndex(directionIndex + 1)][1];
        
        boolean isBorderBefore = addedSides.contains(directionIndex + " " + rowCellBefore + " " + colCellBefore);
        boolean isBorderAfter = addedSides.contains(directionIndex + " " + rowCellAfter + " " + colCellAfter);
        if (isBorderBefore) {
            if (isBorderAfter) {
                return -1;
            }
            
            return 0;
        }
        
        return isBorderAfter ? 0 : 1;
    }
    
    private int getCircularIndex(int index) {
        return (index + 4) % 4;
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
