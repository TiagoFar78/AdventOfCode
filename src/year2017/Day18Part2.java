package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import main.Challenge;

public class Day18Part2 extends Challenge {
    
    private int solve(List<String[]> instructions) {
        int i0 = 0;
        Map<String, Long> registers0 = new HashMap<>();
        registers0.put("p", 0L);
        Queue<Long> queue0 = new LinkedList<>();
        
        int i1 = 0;
        Map<String, Long> registers1 = new HashMap<>();
        registers1.put("p", 1L);
        Queue<Long> queue1 = new LinkedList<>();
        
        int program1Sends = 0;
        while (true) {
            int prevSize = queue0.size();
            i1 = run(instructions, i1, registers1, queue1, queue0);
            program1Sends += queue0.size() - prevSize;
            i0 = run(instructions, i0, registers0, queue0, queue1);
            
            if ((i0 >= instructions.size() && i1 >= instructions.size()) || (queue0.isEmpty() && queue1.isEmpty())) {
                break;
            }
         }
        
        return program1Sends;
    }
    
    private int run(List<String[]> instructions, int i, Map<String, Long> registers, Queue<Long> queueReceive, Queue<Long> queueSend) {
        for (; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i);
            switch (instruction[0]) {
                case "snd":
                    queueSend.add(getValue(registers, instruction[1]));
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
                    if (queueReceive.isEmpty()) {
                        return i;
                    }
                    registers.put(instruction[1], queueReceive.poll());
                    break;
                case "jgz":
                    if (getValue(registers, instruction[1]) > 0) {
                        i += getValue(registers, instruction[2]) - 1;
                    }
                    break;
            }
        }
        
        return i;
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
