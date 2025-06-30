package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part1 extends Challenge {
    
    private int solve(List<Integer> nums) {
        nums.set(1, 12);
        nums.set(2, 2);
        
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
