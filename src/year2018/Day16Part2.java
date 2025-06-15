package year2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day16Part2 extends Challenge {
    
    private int solve(List<int[][]> samples, List<int[]> program) {
        Map<Integer, Set<Integer>> opCodes = new HashMap<>();
        int[] set = new int[16];
        Arrays.fill(set, -1);
        
        for (int[][] sample : samples) {
            Set<Integer> matchingOpCodes = opcodeBehaviors(sample);
            
            int[] instruction = sample[1];
            if (!opCodes.containsKey(instruction[0])) {
                opCodes.put(instruction[0], matchingOpCodes);
                continue;
            }
            
            Set<Integer> found = opCodes.get(instruction[0]);
            found.retainAll(matchingOpCodes);
            for (int i = 0; i < 16; i++) {
                if (set[i] != -1 && set[i] != instruction[0]) {
                    found.remove(i);
                }
            }
            
            if (found.size() == 1) {
                set[found.iterator().next()] = instruction[0];
            }
        }
        
        int[] registers = new int[4];
        for (int[] instruction : program) {
            switch (opCodes.get(instruction[0]).iterator().next()) {
                case 0:
                    addr(registers, instruction);
                    break;
                case 1:
                    addi(registers, instruction);
                    break;
                case 2:
                    mulr(registers, instruction);
                    break;
                case 3:
                    muli(registers, instruction);
                    break;
                case 4:
                    banr(registers, instruction);
                    break;
                case 5:
                    bani(registers, instruction);
                    break;
                case 6:
                    borr(registers, instruction);
                    break;
                case 7:
                    bori(registers, instruction);
                    break;
                case 8:
                    setr(registers, instruction);
                    break;
                case 9:
                    seti(registers, instruction);
                    break;
                case 10:
                    gtir(registers, instruction);
                    break;
                case 11:
                    gtri(registers, instruction);
                    break;
                case 12:
                    gtrr(registers, instruction);
                    break;
                case 13:
                    eqir(registers, instruction);
                    break;
                case 14:
                    eqri(registers, instruction);
                    break;
                case 15:
                    eqrr(registers, instruction);
                    break;
            }
        }
        
        return registers[0];
    }
    
    private Set<Integer> opcodeBehaviors(int[][] sample) {
        int[] before = sample[0];
        int[] instruction = sample[1];
        int[] target = sample[2];
        
        Set<Integer> opcodes = new HashSet<>();
        
        int[] copy = Arrays.copyOf(before, before.length);
        if (match(target, addr(copy, instruction))) {
            opcodes.add(0);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, addi(copy, instruction))) {
            opcodes.add(1);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, mulr(copy, instruction))) {
            opcodes.add(2);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, muli(copy, instruction))) {
            opcodes.add(3);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, banr(copy, instruction))) {
            opcodes.add(4);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, bani(copy, instruction))) {
            opcodes.add(5);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, borr(copy, instruction))) {
            opcodes.add(6);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, bori(copy, instruction))) {
            opcodes.add(7);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, setr(copy, instruction))) {
            opcodes.add(8);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, seti(copy, instruction))) {
            opcodes.add(9);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, gtir(copy, instruction))) {
            opcodes.add(10);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, gtri(copy, instruction))) {
            opcodes.add(11);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, gtrr(copy, instruction))) {
            opcodes.add(12);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, eqir(copy, instruction))) {
            opcodes.add(13);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, eqri(copy, instruction))) {
            opcodes.add(14);
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, eqrr(copy, instruction))) {
            opcodes.add(15);
        }
        
        return opcodes;
    }
    
    private boolean match(int[] a1, int[] a2) {
        for (int i = 0; i < 4; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    private int[] addr(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] + registers[instruction[2]];
        return registers;
    }
    
    private int[] addi(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] + instruction[2];
        return registers;
    }
    
    private int[] mulr(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] * registers[instruction[2]];
        return registers;
    }
    
    private int[] muli(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] * instruction[2];
        return registers;
    }
    
    private int[] banr(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] & registers[instruction[2]];
        return registers;
    }
    
    private int[] bani(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] & instruction[2];
        return registers;
    }
    
    private int[] borr(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] | registers[instruction[2]];
        return registers;
    }
    
    private int[] bori(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] | instruction[2];
        return registers;
    }
    
    private int[] setr(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]];
        return registers;
    }
    
    private int[] seti(int[] registers, int[] instruction) {
        registers[instruction[3]] = instruction[1];
        return registers;
    }
    
    private int[] gtir(int[] registers, int[] instruction) {
        registers[instruction[3]] = instruction[1] > registers[instruction[2]] ? 1 : 0;
        return registers;
    }
    
    private int[] gtri(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] > instruction[2] ? 1 : 0;
        return registers;
    }
    
    private int[] gtrr(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] > registers[instruction[2]] ? 1 : 0;
        return registers;
    }
    
    private int[] eqir(int[] registers, int[] instruction) {
        registers[instruction[3]] = instruction[1] == registers[instruction[2]] ? 1 : 0;
        return registers;
    }
    
    private int[] eqri(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] == instruction[2] ? 1 : 0;
        return registers;
    }
    
    private int[] eqrr(int[] registers, int[] instruction) {
        registers[instruction[3]] = registers[instruction[1]] == registers[instruction[2]] ? 1 : 0;
        return registers;
    }
    
    @Override
    public long solve() {
        List<int[][]> samples = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.isEmpty()) {
                break;
            }
            
            int[] before = lineDigits(pattern, line);
            int[] instruction = lineDigits(pattern, reader.nextLine());
            int[] after = lineDigits(pattern, reader.nextLine());
            samples.add(new int[][] {before, instruction, after});
            
            reader.nextLine();
        }
        
        reader.nextLine();
        reader.nextLine();
        
        List<int[]> program = new ArrayList<>();
        while(reader.hasNextLine()) {
            program.add(lineDigits(pattern, reader.nextLine()));
        }
        reader.close();
        
        return solve(samples, program);
    }
    
    private int[] lineDigits(Pattern p, String line) {
        int[] digits = new int[4];
        
        Matcher matcher = p.matcher(line);
        for (int i = 0; i < 4; i++) {
            matcher.find();
            digits[i] = Integer.parseInt(matcher.group());
        }
        
        return digits;
    }
    
}
