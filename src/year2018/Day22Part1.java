
package year2018;

import java.util.Scanner;

import main.Challenge;

public class Day22Part1 extends Challenge {

    private static final int COL_BORDER = 48271;
    private static final int ROW_BORDER = 16807;
    private static final int MOD = 20183;
    
    private int solve(int depth, int targetRow, int targetCol) {
        int riskLevel = 0;
        
        int[][] cave = new int[targetRow + 1][targetCol + 1];
        for (int row = 0; row <= targetRow; row++) {
            for (int col = 0; col <= targetCol; col++) {
                int geologicIndex = geologicIndex(row, col, targetRow, targetCol, cave);
                int erosionLevel = erosionLevel(geologicIndex, depth);
                cave[row][col] = erosionLevel;
                riskLevel += erosionLevel % 3;
            }
        }
        
        return riskLevel;
    }
    
    private int geologicIndex(int row, int col, int targetRow, int targetCol, int[][] cave) {
        if ((row == 0 && col == 0) || (row == targetRow && col == targetCol)) {
            return 0;
        }
        
        if (row == 0) {
            return col * ROW_BORDER;
        }
        
        if (col == 0) {
            return row * COL_BORDER;
        }
        
        return cave[row - 1][col] * cave[row][col - 1];
    }
    
    private int erosionLevel(int geologicIndex, int depth) {
        return (geologicIndex + depth) % MOD;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int depth = Integer.parseInt(reader.nextLine().substring("depth: ".length()));
        String[] target = reader.nextLine().substring("target: ".length()).split(",");
        reader.close();
        
        return solve(depth, Integer.parseInt(target[1]), Integer.parseInt(target[0]));
    }
    
}
