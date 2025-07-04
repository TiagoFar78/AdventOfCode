package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day01Part2 extends Challenge {
    
    private int solve(List<Integer> nums) {
        int sum = 0;
        for (int num : nums) {
            while (num > 0) {
                num = Math.max(num / 3 - 2, 0);
                sum += num;
            }
        }
        
        return sum;
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
