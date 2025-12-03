package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day03Part1 extends Challenge {
    
    private long solve(List<List<Integer>> banks) {
        int sum = 0;
        for (List<Integer> bank : banks) {
            sum += maxJoltage(bank);
        }
        
        return sum;
    }
    
    private int maxJoltage(List<Integer> bank) {
        int tens = 0;
        int ones = 0;
        for (int i = 0; i < bank.size() - 1; i++) {
            if (bank.get(i) > tens) {
                tens = bank.get(i);
                ones = 0;
            }
            else if (bank.get(i) > ones) {
                ones = bank.get(i);
            }
        }
        ones = Math.max(ones, bank.get(bank.size() - 1));
        
        return tens * 10 + ones;
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
