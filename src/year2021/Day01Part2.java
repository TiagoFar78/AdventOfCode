package year2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day01Part2 extends Challenge {
    
    private int solve(List<Integer> nums) {
        int count = 0;
        for (int i = 3; i < nums.size(); i++) {
            if (nums.get(i) > nums.get(i - 3)) {
                count++;
            }
        }
        
        return count;
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
