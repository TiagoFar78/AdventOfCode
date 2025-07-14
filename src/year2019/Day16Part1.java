package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day16Part1 extends Challenge {
    
    private static final int[] BASE_PATTERN = { 0, 1, 0, -1 };

    private String solve(String inputString) {
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < inputString.length(); i++) {
            input.add(inputString.charAt(i) - '0');
        }
        
        int phases = 100;
        for (int i = 0; i < phases; i++) {
            input = fft(input);
        }
        
        String res = "";
        for (int i = 0; i < 8; i++) {
            res += input.get(i);
        }
        
        return res;
    }
    
    private List<Integer> fft(List<Integer> input) {
        List<Integer> output = new ArrayList<>();
        
        for (int i = 0; i < input.size(); i++) {
            output.add(calculateValueAt(input, i));
        }
        
        return output;
    }
    
    private int calculateValueAt(List<Integer> input, int index) {
        int value = 0;
        for (int i = 0; i < input.size(); i++) {
            value += input.get(i) * patternValue(index, i);
        }
        
        return Math.abs(value % 10);
    }
    
    private int patternValue(int index, int i) {
        if (i >= index + (index + 1) * 3) {
            i -= index + (index + 1) * 3;
            i %= (index + 1) * 4;
            return BASE_PATTERN[i / (index + 1)];
        }
        
        if (i < index) {
            return BASE_PATTERN[0];
        }
        
        i -= index;
        return BASE_PATTERN[i / (index + 1) + 1];
    }
    
    @Override
    public String solveString() {
        Scanner reader = getInputFile();
        String input = reader.nextLine(); 
        reader.close();
        
        return solve(input);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
