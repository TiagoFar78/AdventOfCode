package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day08Part2 extends Challenge {
    
    private int solve(List<String> program) {
                
        for (int i = 0; i < program.size(); i++) {
            String[] instruction = program.get(i).split(" ");
            String operation = instruction[0];
            String argument = instruction[1];
            if (operation.equals("nop")) {
                List<String> newProgram = new ArrayList<>(program);
                newProgram.set(i, "jmp " + argument);
                int[] res = runProgram(newProgram);
                if (res[0] != 0) {
                    return res[1];
                }
            }
            else if (operation.equals("jmp")) {
                List<String> newProgram = new ArrayList<>(program);
                newProgram.set(i, "nop " + argument);
                int[] res = runProgram(newProgram);
                if (res[0] != 0) {
                    return res[1];
                }
            }
        }
        
        throw new IllegalAccessError("No solution");
    }
    
    private int[] runProgram(List<String> program) {
        Set<Integer> seen = new HashSet<>();
        int accumulator = 0;
        
        for (int i = 0; i < program.size(); i++) {
            if (seen.contains(i)) {
                return new int[] { 0, -1 };
            }
            seen.add(i);
            
            String[] instruction = program.get(i).split(" ");
            String operation = instruction[0];
            int argument = Integer.parseInt(instruction[1]);
            if (operation.equals("acc")) {
                accumulator += argument;
            }
            else if (operation.equals("jmp")) {
                i += argument - 1;
            }
        }
        
        return new int[] { 1, accumulator };        
    }
    
    @Override
    public long solve() {
        List<String> program = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            program.add(reader.nextLine());
        }
        reader.close();
        
        return solve(program);
    }

}
