package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part1 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
            { -1, 0 },
            { 0, 1 },
            { 1, 0 },
            { 0, -1 }
    };
    
    private static final int[][] KEYPAD = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 9 }
    };
    
    private String solve(List<String> buttonMoves) {
        String code = "";
        int row = 1;
        int col = 1;
        
        for (String moves : buttonMoves) {
            for (char move : moves.toCharArray()) {
                int[] direction = getDirection(move);
                
                row += direction[0];
                col += direction[1];
                
                row = Math.min(2, Math.max(0, row));
                col = Math.min(2, Math.max(0, col));
            }
            
            code += KEYPAD[row][col];
        }
        
        return code;
    }
    
    private int[] getDirection(char c) {
        switch (c) {
            case 'U':
                return DIRECTIONS[0];
            case 'R':
                return DIRECTIONS[1];
            case 'D':
                return DIRECTIONS[2];
            case 'L':
                return DIRECTIONS[3];
        }
        
        throw new IllegalArgumentException(c + " is not valid");
    }

    @Override
    public String solveString() {
        List<String> buttonMoves = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            buttonMoves.add(reader.nextLine());
        }
        reader.close();
        
        return solve(buttonMoves);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
