package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day12Part1 extends Challenge {
    
    private final static int[][] DIRS = {
            { -1, 0 },
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
    };
    
    private int solve(List<Instruction> instructions) {
        char[] dirs = { 'N', 'E', 'S', 'W' };
        
        int row = 0;
        int col = 0;
        int dir = 1;
        for (Instruction instruction : instructions) {
            if (instruction.c == 'F') {
                row += DIRS[dir][0] * instruction.value;
                col += DIRS[dir][1] * instruction.value;
            }
            else if (instruction.c == 'R') {
                int rotation = instruction.value / 90;
                dir = (dir + rotation) % 4;
            }
            else if (instruction.c == 'L') {
                int rotation = instruction.value / 90;
                dir = ((dir - rotation) % 4 + 4) % 4;
            }
            else {
                int index = 0;
                for (int i = 1; i < 4; i++) {
                    if (instruction.c == dirs[i]) {
                        index = i;
                        break;
                    }
                }
                
                row += DIRS[index][0] * instruction.value;
                col += DIRS[index][1] * instruction.value;
            }
        }
        
        return Math.abs(row) + Math.abs(col);
    }
    
    @Override
    public long solve() {
        List<Instruction> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            instructions.add(new Instruction(line.charAt(0), Integer.parseInt(line.substring(1))));
        }
        reader.close();
        
        return solve(instructions);
    }
    
    private class Instruction {
        
        public char c;
        public int value;
        
        public Instruction(char c, int value) {
            this.c = c;
            this.value = value;
        }
        
    }

}
