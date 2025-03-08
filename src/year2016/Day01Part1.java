package year2016;

import java.util.Scanner;

import main.Challenge;

public class Day01Part1 extends Challenge {
    
    private int solve(String[] instructions) {
        int[][] directions = {
                { -1, 0 },
                { 0, 1 },
                { 1, 0 },
                { 0, -1 }
        };
        
        int dir = 0;
        int row = 0;
        int col = 0;
        
        for (String instruction : instructions) {
            if (instruction.charAt(0) == 'L') {
                dir = (dir + 1) % directions.length;
            }
            else {
                dir = (dir - 1 + directions.length) % directions.length;
            }
            
            int move = Integer.parseInt(instruction.substring(1));
            row += directions[dir][0] * move;
            col += directions[dir][1] * move;
        }
        
        return Math.abs(row) + Math.abs(col);
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        String[] instructions = line.split(", ");
        return solve(instructions);
    }

}
