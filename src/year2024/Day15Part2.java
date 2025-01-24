package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day15Part2 extends Challenge {
    
    private static final int EMPTY = 0;
    private static final int BOX_LEFT = 1;
    private static final int BOX_RIGHT = 4;
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
            
            if (move % 2 == 0) {
                if (!isMovePossible(warehouse, dir, currentRow, currentCol)) {
                    continue;
                }
                
                update(warehouse, dir, currentRow, currentCol);
                currentRow += dir[0];
                currentCol += dir[1];
                continue;
            }
            
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];
            while (warehouse[newRow][newCol] == BOX_LEFT || warehouse[newRow][newCol] == BOX_RIGHT) {
                newRow += dir[0];
                newCol += dir[1];
            }
            
            if (warehouse[newRow][newCol] == EMPTY) {
                while (warehouse[newRow][newCol] != ROBOT) {
                    warehouse[newRow][newCol] = warehouse[newRow - dir[0]][newCol - dir[1]];
                    newRow -= dir[0];
                    newCol -= dir[1];
                }
                warehouse[currentRow][currentCol] = EMPTY;
                currentRow += dir[0];
                currentCol += dir[1];
            }
        }
        
        return getGPSLocationsSum(warehouse);
    }
    
    private boolean isMovePossible(int[][] warehouse, int[] dir, int currentRow, int currentCol) {
        int newRow = currentRow + dir[0];
        int newCol = currentCol + dir[1];
        
        if (warehouse[newRow][newCol] == EMPTY) {
            return true;
        }
        
        if (warehouse[newRow][newCol] == WALL) {
            return false;
        }
        
        if (warehouse[newRow][newCol] == BOX_LEFT) {
            return isMovePossible(warehouse, dir, newRow, newCol) && isMovePossible(warehouse, dir, newRow, newCol + 1);
        }
        
        if (warehouse[newRow][newCol] == BOX_RIGHT) {
            return isMovePossible(warehouse, dir, newRow, newCol - 1) && isMovePossible(warehouse, dir, newRow, newCol);
        }
        
        return false;
    }
    
    private void update(int[][] warehouse, int[] dir, int currentRow, int currentCol) {
        int newRow = currentRow + dir[0];
        int newCol = currentCol + dir[1];
        if (warehouse[newRow][newCol] == BOX_LEFT) {
            update(warehouse, dir, newRow, newCol);
            update(warehouse, dir, newRow, newCol + 1);
        }
        else if (warehouse[newRow][newCol] == BOX_RIGHT) {
            update(warehouse, dir, newRow, newCol - 1);
            update(warehouse, dir, newRow, newCol);
        }
        
        warehouse[newRow][newCol] = warehouse[currentRow][currentCol];
        warehouse[currentRow][currentCol] = EMPTY;
    }
    
    private int[][] convertWarehouse(List<String> warehouseString) {
        int n = warehouseString.size();
        int m = warehouseString.get(0).length();
        int[][] warehouse = new int[n][m * 2];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = warehouseString.get(i).charAt(j);
                if (c == 'O') {
                    warehouse[i][j * 2] = BOX_LEFT;
                    warehouse[i][j * 2 + 1] = BOX_RIGHT;
                }
                else if (c == '#') {
                    warehouse[i][j * 2] = WALL;
                    warehouse[i][j * 2 + 1] = WALL;
                }
                else if (c == '@') {
                    warehouse[i][j * 2] = ROBOT;
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
                if (warehouse[i][j] == BOX_LEFT) {
                    sum += i * 100 + j;
                    j++;
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
