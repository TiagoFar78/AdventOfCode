package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
    private static final char[][] KEYPAD = {
            { '0', '0', '1', '0', '0' },
            { '0', '2', '3', '4', '0' },
            { '5', '6', '7', '8', '9' },
            { '0', 'A', 'B', 'C', '0' },
            { '0', '0', 'D', '0', '0' }
    };
    
    private String solve(List<String> buttonMoves) {
        String code = "";
        int row = 2;
        int col = 0;
        
        for (String moves : buttonMoves) {
            for (char move : moves.toCharArray()) {
                switch (move) {
                    case 'U':
                        row = Math.max(row - 1, Math.abs(col - 2));
                        break;
                    case 'R':
                        col = Math.min(col + 1, 4 - Math.abs(row - 2));
                        break;
                    case 'D':
                        row = Math.min(row + 1, 4 - Math.abs(col - 2));
                        break;
                    case 'L':
                        col = Math.max(col - 1, Math.abs(row - 2));
                        break;
                }
            }
            
            code += KEYPAD[row][col];
        }
        
        return code;
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
