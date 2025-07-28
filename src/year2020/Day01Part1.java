package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day01Part1 extends Challenge {
    
    private int solve(List<Integer> nums) {
        int target = 2020;
        Set<Integer> needed = new HashSet<>();
        for (int num : nums) {
            if (needed.contains(num)) {
                return num * (target - num);
            }
            needed.add(target - num);
        }
        
        throw new IllegalAccessError("No sum");
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
