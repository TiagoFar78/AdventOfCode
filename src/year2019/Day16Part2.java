package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day16Part2 extends Challenge {
    
    private String solve(String inputString) {
        int offset = Integer.parseInt(inputString.substring(0, 7));
        
        List<Integer> input = new ArrayList<>();
        for (int repetitions = 0; repetitions < 10_000; repetitions++) {
            for (int i = 0; i < inputString.length(); i++) {
                input.add(inputString.charAt(i) - '0');
            }
        }
        
        int phases = 100;
        input = fft(input, offset);
        for (int i = 0; i < phases - 1; i++) {
            input = fft(input, 0);
        }
        
        String res = "";
        for (int i = 0; i < 8; i++) {
            res += input.get(i);
        }
        
        return res;
    }
    
    private List<Integer> fft(List<Integer> input, int index) {
        List<Integer> output = new ArrayList<>();
        
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            sum += input.get(i);
        }
        
        for (int i = index; i < input.size(); i++) {
            output.add(Math.abs(sum % 10));
            sum -= input.get(i);
        }
        
        return output;
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
