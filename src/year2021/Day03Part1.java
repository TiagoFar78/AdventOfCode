package year2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day03Part1 extends Challenge {
    
    private int solve(List<Integer> nums, int bits) {
        int[] occur = new int[bits];
        
        for (int num : nums) {
            int bit = 0;
            while (num > 0) {
                if (num % 2 == 1) {
                    occur[bit]++;
                }
                num >>= 1;
                bit++;
            }
        }
        
        int gamma = 0;
        int epsilon = 0;
        for (int i = 0; i < bits; i++) {
            if (occur[i] > nums.size() / 2) {
                gamma += Math.pow(2, i);
            }
            else {
                epsilon += Math.pow(2, i);
            }
        }
        
        return gamma * epsilon;
    }

    @Override
    public long solve() {
        List<Integer> nums = new ArrayList<>();
        int n = 0;
        
        Scanner reader = getInputFile();
        String first = reader.nextLine();
        n = first.length();
        nums.add(toInt(first));
        while (reader.hasNextLine()) {
            nums.add(toInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(nums, n);
    }
    
    private int toInt(String binary) {
        int num = 0;
        int n = binary.length();
        for (int i = 0; i < n; i++) {
            if (binary.charAt(n - 1 - i) == '1') {
                num += Math.pow(2, i);
            }
        }
        
        return num;
    }

}
