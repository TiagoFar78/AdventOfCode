
package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day19Part1 extends Challenge {
    
    private int solve(int instructionPointer, List<int[]> program) {
        int[] registers = new int[6];
        while (registers[instructionPointer] < program.size()) {
            executeInstruction(registers, program.get(registers[instructionPointer]));
            registers[instructionPointer]++;
        }
        
        return registers[0];
    }
    
    private int[] executeInstruction(int[] registers, int[] instruction) {
        switch (instruction[0]) {
            case 0:
                return addr(registers, instruction);
            case 1:
                return addi(registers, instruction);
            case 2:
                return mulr(registers, instruction);
            case 3:
                return muli(registers, instruction);
            case 4:
                return banr(registers, instruction);
            case 5:
                return bani(registers, instruction);
            case 6:
                return borr(registers, instruction);
            case 7:
                return bori(registers, instruction);
            case 8:
                return setr(registers, instruction);
            case 9:
                return seti(registers, instruction);
            case 10:
                return gtir(registers, instruction);
            case 11:
                return gtri(registers, instruction);
            case 12:
                return gtrr(registers, instruction);
            case 13:
                return eqir(registers, instruction);
            case 14:
                return eqri(registers, instruction);
            case 15:
                return eqrr(registers, instruction);
        }
        
        return null;
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
        List<int[]> program = new ArrayList<>();        

        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        matcher.find();
        int instructionPointer = Integer.parseInt(matcher.group());
        
        while (reader.hasNextLine()) {
            program.add(lineDigits(pattern, reader.nextLine()));
        }
        reader.close();
        
        return solve(instructionPointer, program);
    }
    
    private static final Map<String, Integer> OPCODES = opcodes();
    
    private static final Map<String, Integer> opcodes() {
        Map<String, Integer> opcodes = new HashMap<>();
        
        String[] instructions = { "addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori", "setr", "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr" };
        for (int i = 0; i < instructions.length; i++) {
            opcodes.put(instructions[i], i);
        }
        
        return opcodes;
    }
    
    private int[] lineDigits(Pattern p, String line) {
        int[] digits = new int[4];
        digits[0] = OPCODES.get(line.split(" ")[0]);
        
        Matcher matcher = p.matcher(line);
        for (int i = 1; i < 4; i++) {
            matcher.find();
            digits[i] = Integer.parseInt(matcher.group());
        }
        
        return digits;
    }
    
}
