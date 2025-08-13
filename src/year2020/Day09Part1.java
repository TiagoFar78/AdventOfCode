package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day09Part1 extends Challenge {
    
    private long solve(List<Long> nums) {
        int preamble = 25;
        for (int i = preamble; i < nums.size(); i++) {
            if (!hasSum(nums, i - preamble, i - 1, nums.get(i))) {
                return nums.get(i);
            }
        }
        
        throw new IllegalAccessError("No solution");
    }
    
    private boolean hasSum(List<Long> nums, int s, int e, long target) {
        Set<Long> opposite = new HashSet<>();
        for (int i = s; i <= e; i++) {
            if (opposite.contains(nums.get(i))) {
                return true;
            }
            opposite.add(target - nums.get(i));
        }
        
        return false;
    }
    
    @Override
    public long solve() {
        List<Long> nums = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            nums.add(Long.parseLong(reader.nextLine()));
        }
        reader.close();
        
        return solve(nums);
    }

}
