package year2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day03Part2 extends Challenge {
    
    private int solve(List<String> nums) {
        return rating(nums, 0, '1', '0') * rating(nums, 0, '0', '1');
    }
    
    private int rating(List<String> nums, int i, char target1, char target2) {
        int ones = 0;
        for (String num : nums) {
            if (num.charAt(i) == '1') {
                ones++;
            }
        }
        
        char target = ones * 2 >= nums.size() ? target1 : target2;
        List<String> newNums = new ArrayList<>();
        for (String num : nums) {
            if (num.charAt(i) == target) {
                newNums.add(num);
            }
        }
        
        if (newNums.size() == 1) {
            return toInt(newNums.get(0));
        }
        
        return rating(newNums, i + 1, target1, target2);
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

    @Override
    public long solve() {
        List<String> nums = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            nums.add(reader.nextLine());
        }
        reader.close();
        
        return solve(nums);
    }

}
