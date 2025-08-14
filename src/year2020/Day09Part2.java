package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day09Part2 extends Challenge {
    
    private long solve(List<Long> nums) {
        int preamble = 25;
        long invalid = 0;
        for (int i = preamble; i < nums.size(); i++) {
            if (!hasSum(nums, i - preamble, i - 1, nums.get(i))) {
                invalid = nums.get(i);
                break;
            }
        }
        
        int l = 0;
        int r = 0;
        long sum = 0;
        while (sum != invalid) {
            sum += nums.get(r);
            r++;
            
            while (sum > invalid) {
                sum -= nums.get(l);
                l++;
            }
        }
        r--;
        
        long smallest = Long.MAX_VALUE;
        long largest = 0;
        for (int i = l; i <= r; i++) {
            smallest = Math.min(smallest, nums.get(i));
            largest = Math.max(largest, nums.get(i));
        }
        
        return smallest + largest;
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
