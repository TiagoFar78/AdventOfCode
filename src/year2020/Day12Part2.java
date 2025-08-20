package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day12Part2 extends Challenge {
    
    private final static int[][] DIRS = {
            { -1, 0 },
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
    };
    
    private int solve(List<Instruction> instructions) {
        char[] dirs = { 'N', 'E', 'S', 'W' };
        
        int shipRow = 0;
        int shipCol = 0;
        int wpRow = -1;
        int wpCol = 10;
        for (Instruction instruction : instructions) {
            if (instruction.c == 'F') {
                shipRow += wpRow * instruction.value;
                shipCol += wpCol * instruction.value;
            }
            else if (instruction.c == 'R' || instruction.c == 'L') {
                int rotation = instruction.value;
                if (instruction.c == 'R') {
                    rotation = -rotation + 360;
                }
                
                rotation %= 360;
                if (rotation == 90) {
                    int temp = wpCol;
                    wpCol = wpRow;
                    wpRow = -temp;
                }
                else if (rotation == 180) {
                    wpRow = -wpRow;
                    wpCol = -wpCol;
                }
                else if (rotation == 270) {
                    int temp = wpCol;
                    wpCol = -wpRow;
                    wpRow = temp;
                }
            }
            else {
                int index = 0;
                for (int i = 1; i < 4; i++) {
                    if (instruction.c == dirs[i]) {
                        index = i;
                        break;
                    }
                }
                
                wpRow += DIRS[index][0] * instruction.value;
                wpCol += DIRS[index][1] * instruction.value;
            }
            
            System.out.println("ShipRow = " + shipRow + ", ShipCol = " + shipCol + ", wpRow = " + wpRow + ", wpCol = " + wpCol);
        }
        
        return Math.abs(shipRow) + Math.abs(shipCol);
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
