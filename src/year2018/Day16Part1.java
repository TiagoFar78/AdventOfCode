package year2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day16Part1 extends Challenge {
    
    private int solve(List<int[][]> samples) {
        int count = 0;
        for (int[][] sample : samples) {
            if (opcodeBehaviors(sample) >= 3) {
                count++;
            }
        }
        
        return count;
    }
    
    private int opcodeBehaviors(int[][] sample) {
        int[] before = sample[0];
        int[] instruction = sample[1];
        int[] target = sample[2];
        
        int count = 0;
        
        int[] copy = Arrays.copyOf(before, before.length);
        if (match(target, addr(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, addi(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, mulr(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, muli(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, banr(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, bani(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, borr(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, bori(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, setr(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, seti(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, gtir(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, gtri(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, gtrr(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, eqir(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, eqri(copy, instruction))) {
            count++;
        }
        
        copy = Arrays.copyOf(before, before.length);
        if (match(target, eqrr(copy, instruction))) {
            count++;
        }
        
        return count;
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
        reader.close();
        
        return solve(samples);
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
