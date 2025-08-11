package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day08Part1 extends Challenge {
    
    private int solve(List<String> program) {
        Set<Integer> seen = new HashSet<>();
        int accumulator = 0;
        
        for (int i = 0; !seen.contains(i); i++) {
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
        
        return accumulator;        
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
