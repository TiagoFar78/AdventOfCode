package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day22Part1 extends Challenge {
    
    private static final int DEAL_INTO_NEW_STACK = 0;
    private static final int CUT = 1;
    private static final int DEAL_WITH_INCREMENT = 2;
    
    private int solve(List<int[]> instructions) {
        int size = 10_007;
        int current = 2019;
        for (int[] instruction : instructions) {
            switch (instruction[0]) {
                case DEAL_INTO_NEW_STACK:
                    current = dealIntoNewStack(size, current);
                    break;
                case CUT:
                    current = cut(size, current, instruction[1]);
                    break;
                case DEAL_WITH_INCREMENT:
                    current = dealWithIncrement(size, current, instruction[1]);
                    break;
            }
        }
        
        return current;
    }
    
    private int dealIntoNewStack(int size, int target) {
        return size - 1 - target;
    }
    
    private int cut(int size, int target, int amount) {
        return (target - amount + size) % size;
    }
    
    private int dealWithIncrement(int size, int target, int amount) {
        return (target * amount) % size;
    }

    @Override
    public long solve() {
        String[] availableInstructions = { "deal into new stack", "cut", "deal with increment" };
        List<int[]> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String instruction = reader.nextLine();
            if (instruction.startsWith(availableInstructions[0])) {
                instructions.add(new int[] { DEAL_INTO_NEW_STACK });
            }
            else if (instruction.startsWith(availableInstructions[1])) {
                instructions.add(new int[] { CUT, Integer.parseInt(instruction.substring(availableInstructions[1].length() + 1)) });
            }
            else if (instruction.startsWith(availableInstructions[2])) {
                instructions.add(new int[] { DEAL_WITH_INCREMENT, Integer.parseInt(instruction.substring(availableInstructions[2].length() + 1)) });
            }
            
        }
        reader.close();
        
        return solve(instructions);
    }

}
