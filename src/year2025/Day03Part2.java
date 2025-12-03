package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day03Part2 extends Challenge {
    
    private long solve(List<List<Integer>> banks) {
        long sum = 0;
        for (List<Integer> bank : banks) {
            sum += maxJoltage(bank);
        }
        
        return sum;
    }
    
    private long maxJoltage(List<Integer> bank) {
        int size = 12;
        int[] digits = new int[size];
        for (int i = 0; i < bank.size(); i++) {
            for (int j = size - 1; j >= 0; j--) {
                if (i + j < bank.size() && bank.get(i) > digits[j]) {
                    digits[j] = bank.get(i);
                    for (j--; j >= 0; j--) {
                        digits[j] = 0;
                    }
                    break;
                }
            }
        }
        
        long number = 0;
        for (int i = size - 1; i >= 0; i--) {
            number += digits[i] * Math.pow(10, i);
        }
        
        return number;
    }
    
    @Override
    public long solve() {
        List<List<Integer>> banks = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            List<Integer> bank = new ArrayList<>();
            String batteries = reader.nextLine();
            for (int i = 0; i < batteries.length(); i++) {
                bank.add(batteries.charAt(i) - '0');
            }
            banks.add(bank);
        }
        reader.close();
        
        return solve(banks);
    }

}
