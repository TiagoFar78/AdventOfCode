package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day01Part2 extends Challenge {
    
    private long solve(List<Integer> nums) {
        int target = 2020;
        for (int i = 0; i < nums.size(); i++) {
            long complement = solve(nums, target - nums.get(i), i + 1);
            if (complement != -1) {
                return nums.get(i) * complement;
            }
        }
        
        throw new IllegalAccessError("No sum");
    }
    
    private long solve(List<Integer> nums, int target, int i) {
        Set<Integer> needed = new HashSet<>();
        for (; i < nums.size(); i++) {
            int num = nums.get(i);
            if (needed.contains(num)) {
                return num * (target - num);
            }
            needed.add(target - num);
        }
        
        return -1;
    }

    @Override
    public long solve() {
        List<Integer> nums = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            nums.add(Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(nums);
    }

}
