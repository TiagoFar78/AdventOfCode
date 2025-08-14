package year2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day10Part1 extends Challenge {
    
    private int solve(List<Integer> nums) {
        nums.add(0);
        Collections.sort(nums);
        nums.add(nums.get(nums.size() - 1) + 3);
        
        int oneDiff = 0;
        int threeDiff = 0;
        for (int i = 1; i < nums.size(); i++) {
            int diff = nums.get(i) - nums.get(i - 1);
            if (diff == 1) {
                oneDiff++;
            }
            else if (diff == 3) {
                threeDiff++;
            }
        }
        
        return oneDiff * threeDiff;
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
