package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day18Part1 extends Challenge {
    
    private long solve(List<String[]> instructions) {
        long freq = -1;
        Map<String, Long> registers = new HashMap<>();
        
        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i);
            switch (instruction[0]) {
                case "snd":
                    freq = getValue(registers, instruction[1]);
                    break;
                case "set":
                    registers.put(instruction[1], getValue(registers, instruction[2]));
                    break;
                case "add":
                    registers.put(instruction[1], registers.getOrDefault(instruction[1], 0L) + getValue(registers, instruction[2]));
                    break;
                case "mul":
                    registers.put(instruction[1], registers.getOrDefault(instruction[1], 0L) * getValue(registers, instruction[2]));
                    break;
                case "mod":
                    registers.put(instruction[1], registers.getOrDefault(instruction[1], 0L) % getValue(registers, instruction[2]));
                    break;
                case "rcv":
                    if (getValue(registers, instruction[1]) != 0) {
                        return freq;
                    }
                    break;
                case "jgz":
                    if (getValue(registers, instruction[1]) > 0) {
                        i += getValue(registers, instruction[2]) - 1;
                    }
                    break;
            }
        }
        
        return freq;
    }
    
    private long getValue(Map<String, Long> registers, String symbol) {
        if (symbol.length() == 1 && symbol.charAt(0) >= 'a' && symbol.charAt(0) <= 'z') {
            return registers.getOrDefault(symbol, 0L);
        }
        
        return Integer.parseInt(symbol);
    }
    
    @Override
    public long solve() {
        List<String[]> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            instructions.add(reader.nextLine().split(" "));
        }
        reader.close();
        
        return solve(instructions);
    }

}
