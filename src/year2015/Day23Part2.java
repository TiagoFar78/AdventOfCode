package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day23Part2 extends Challenge {
    
    private static final String[] INSTRUCTIONS = { "hlf", "tpl", "inc", "jmp", "jie", "jio" };
    
    private int solve(List<int[]> instructions) {
        int[] registers = { 1, 0 };
        
        for (int i = 0; i < instructions.size(); i++) {
            int[] instruction = instructions.get(i);
            switch (instruction[0]) {
                case 0:
                    registers[instruction[1]] /= 2;
                    break;
                case 1:
                    registers[instruction[1]] *= 3;
                    break;
                case 2:
                    registers[instruction[1]]++;
                    break;
                case 3:
                    i += instruction[1] - 1;
                    break;
                case 4:
                    if (registers[instruction[1]] % 2 == 0) {
                        i += instruction[2] - 1;
                    }
                    break;
                case 5:
                    if (registers[instruction[1]] == 1) {
                        i += instruction[2] - 1;
                    }
                    break;
            }
        }
        
        return registers[1];
    }
    
    @Override
    public long solve() {
        List<int[]> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] parts = reader.nextLine().split(" |, ");
            
            int[] instruction = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                instruction[i] = findCode(parts[i]);
            }
            
            instructions.add(instruction);
        }
        reader.close();
        
        return solve(instructions);
    }
    
    private int findCode(String s) {
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            // Nothing
        }
        
        if (s.length() == 1) {
            return s.charAt(0) == 'a' ? 0 : 1;
        }
        
        for (int i = 0; i < INSTRUCTIONS.length; i++) {
            if (INSTRUCTIONS[i].equals(s)) {
                return i;
            }
        }
        
        return -1;
    }

}
