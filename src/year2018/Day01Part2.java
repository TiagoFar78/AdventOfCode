package year2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day01Part2 extends Challenge {
    
    private int solve(List<Integer> nums) {
        Set<Integer> seen = new HashSet<>();
        seen.add(0);
        int sum = 0;
        while (true) {
            for (int num : nums) {
                sum += num;
                if (seen.contains(sum)) {
                    return sum;
                }
                seen.add(sum);
            }
        }
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
