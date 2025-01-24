package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day15Part1 extends Challenge {
    
    private static final int EMPTY = 0;
    private static final int BOX = 1;
    private static final int WALL = 2;
    private static final int ROBOT = 3;
    private static final int[][] DIRECTIONS = {
        { -1, 0 }, // ^
        { 0, 1 },  // >
        { 1, 0 },  // v
        { 0, -1 }  // <
    };
    
    private long solve(List<String> warehouseString, String movesString) {
        int[][] warehouse = convertWarehouse(warehouseString);
        int[] moves = convertMoves(movesString);
        
        int[] startingPos = findStartingPosition(warehouse);
        int currentRow = startingPos[0];
        int currentCol = startingPos[1];
        
        for (int move : moves) {
            int[] dir = DIRECTIONS[move];
            
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];
            while (warehouse[newRow][newCol] == BOX) {
                newRow += dir[0];
                newCol += dir[1];
            }
            
            if (warehouse[newRow][newCol] == EMPTY) {
                if (warehouse[currentRow + dir[0]][currentCol + dir[1]] == BOX) {
                    warehouse[newRow][newCol] = BOX;
                }
                warehouse[currentRow][currentCol] = EMPTY;
                currentRow += dir[0];
                currentCol += dir[1];
                warehouse[currentRow][currentCol] = ROBOT;
            }
        }
        
        return getGPSLocationsSum(warehouse);
    }
    
    private int[][] convertWarehouse(List<String> warehouseString) {
        int n = warehouseString.size();
        int m = warehouseString.get(0).length();
        int[][] warehouse = new int[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = warehouseString.get(i).charAt(j);
                if (c == 'O') {
                    warehouse[i][j] = BOX;
                }
                else if (c == '#') {
                    warehouse[i][j] = WALL;
                }
                else if (c == '@') {
                    warehouse[i][j] = ROBOT;
                }
            }
        }
        
        return warehouse;
    }
    
    private int[] convertMoves(String movesString) {
        int n = movesString.length();
        int[] moves = new int[n];
        
        for (int i = 0; i < n; i++) {
            moves[i] = convertMove(movesString.charAt(i));
        }
        
        return moves;
    }
    
    private int convertMove(char c) {
        switch (c) {
            case '^':
                return 0;
            case '>':
                return 1;
            case 'v':
                return 2;
            case '<':
                return 3;
        }
        
        return 0;
    }
    
    private int[] findStartingPosition(int[][] warehouse) {
        int n = warehouse.length;
        int m = warehouse[0].length;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (warehouse[i][j] == ROBOT) {
                    return new int[] { i, j };
                }
            }
        }
        
        return null;
    }
    
    private long getGPSLocationsSum(int[][] warehouse) {
        int n = warehouse.length;
        int m = warehouse[0].length;
        
        long sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (warehouse[i][j] == BOX) {
                    sum += i * 100 + j;
                }
            }
        }
        
        return sum;
    }
    
    @Override
    public long solve() {
        List<String> warehouse = new ArrayList<>();
        String moves = "";
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.length() == 0) {
                break;
            }
            warehouse.add(line);
        }
        
        while (reader.hasNextLine()) {
            moves += reader.nextLine();
        }
        reader.close();
        
        return solve(warehouse, moves);
    }
}
