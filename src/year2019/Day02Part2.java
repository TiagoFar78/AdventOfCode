package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
    private int solve(List<Integer> nums) {
        int output = 19690720;
        int min = 0;
        int max = 99;
        
        for (int noun = min; noun < max; noun++) {
            for (int verb = min; verb < max; verb++) {
                if (solveWithInput(new ArrayList<>(nums), noun, verb) == output) {
                    return noun * 100 + verb;
                }
            }
        }
        
        throw new IllegalAccessError("Solution not found");
    }
    
    private int solveWithInput(List<Integer> nums, int i1, int i2) {
        nums.set(1, i1);
        nums.set(2, i2);
        
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) == 99) {
                break;
            }
            else if (nums.get(i) == 1) {
                nums.set(nums.get(i + 3), nums.get(nums.get(i + 1)) + nums.get(nums.get(i + 2)));
            }
            else if (nums.get(i) == 2) {
                nums.set(nums.get(i + 3), nums.get(nums.get(i + 1)) * nums.get(nums.get(i + 2)));
            }
            
            i += 3;
        }
        
        return nums.get(0);
    }

    @Override
    public long solve() {
        List<Integer> nums = new ArrayList<>();
        
        Scanner reader = getInputFile();
        String[] sopcodes = reader.nextLine().split(",");
        reader.close();
        
        for (String sopcode : sopcodes) {
            nums.add(Integer.parseInt(sopcode));
        }
        
        return solve(nums);
    }

}
