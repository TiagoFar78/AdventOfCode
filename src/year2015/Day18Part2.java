package year2015;

import java.util.Scanner;

import main.Challenge;

public class Day18Part2 extends Challenge {
    
    private final static int SIDE_SIZE = 100;
    private final static int STEPS = 100;
    
    private int solve(int[][] grid) {
        grid[0][0] = 1;
        grid[0][SIDE_SIZE - 1] = 1;
        grid[SIDE_SIZE - 1][0] = 1;
        grid[SIDE_SIZE - 1][SIDE_SIZE - 1] = 1;
        
        for (int i = 0; i < STEPS; i++) {
            grid = animate(grid);
            grid[0][0] = 1;
            grid[0][SIDE_SIZE - 1] = 1;
            grid[SIDE_SIZE - 1][0] = 1;
            grid[SIDE_SIZE - 1][SIDE_SIZE - 1] = 1;
        }
        
        int ligthsOn = 0;
        for (int i = 0; i < SIDE_SIZE; i++) {
            for (int j = 0; j < SIDE_SIZE; j++) {
                ligthsOn += grid[i][j];
            }
        }
        
        return ligthsOn;
    }
    
    private int[][] animate(int[][] grid) {
        int[][] newGrid = new int[SIDE_SIZE][SIDE_SIZE];
        
        for (int i = 0; i < SIDE_SIZE; i++) {
            for (int j = 0; j < SIDE_SIZE; j++) {
                int neighborsOn = neighborsOn(grid, i, j);
                if (grid[i][j] == 1) {
                    newGrid[i][j] = neighborsOn == 2 || neighborsOn == 3 ? 1 : 0;
                }
                else {
                    newGrid[i][j] = neighborsOn == 3 ? 1 : 0;
                }
            }
        }
        
        return newGrid;
    }
    
    private int neighborsOn(int[][] grid, int row, int col) {
        int[][] directions = {
                { -1, -1 },
                { 0, -1 },
                { 1, -1 },
                { 1, 0 },
                { 1, 1 },
                { 0, 1 },
                { -1, 1 },
                { -1, 0 }
        };
        
        int neighborsOn = 0;
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            
            if (newRow < 0 || newRow >= SIDE_SIZE || newCol < 0 || newCol >= SIDE_SIZE) {
                continue;
            }
            
            neighborsOn += grid[newRow][newCol];
        }
        
        return neighborsOn;
    }
    
    @Override
    public long solve() {
        int[][] grid = new int[SIDE_SIZE][SIDE_SIZE];
        
        Scanner reader = getInputFile();
        int row = 0;
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '#') {
                    grid[row][col] = 1;
                }
            }
            row++;
        }
        reader.close();
        
        return solve(grid);
    }
    
}
