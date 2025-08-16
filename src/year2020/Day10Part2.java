package year2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day10Part2 extends Challenge {
    
    private long solve(List<Integer> nums) {
        nums.add(0);
        Collections.sort(nums);
        nums.add(nums.get(nums.size() - 1) + 3);
        return countWays(nums, 0, new HashMap<>());
    }
    
    private long countWays(List<Integer> nums, int i, Map<Integer, Long> seen) {
        if (i == nums.size() - 1) {
            return 1;
        }
        
        if (seen.containsKey(i)) {
            return seen.get(i);
        }
        
        long ways = 0;
        for (int j = i + 1; j < nums.size() && nums.get(j) - nums.get(i) <= 3; j++) {
            ways += countWays(nums, j, seen);
        }
        
        seen.put(i, ways);
        return ways;
    }
    
    @Override
    public long solve() {
        System.out.println();
        List<Integer> nums = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            nums.add(Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(nums);
    }

}
