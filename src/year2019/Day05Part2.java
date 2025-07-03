package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day05Part2 extends Challenge {
    
    private int solve(List<Integer> nums) {
        int input = 5;
        int output = 0;
        
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            if (num == 99) {
                break;
            }
            
            int instruction = num % 10;
            if (instruction == 1) {
                nums.set(nums.get(i + 3), getParameter(nums, num, i, 1) + getParameter(nums, num, i, 2));
                i += 3;
            }
            else if (instruction == 2) {
                nums.set(nums.get(i + 3), getParameter(nums, num, i, 1) * getParameter(nums, num, i, 2));
                i += 3;
            }
            else if (instruction == 3) {
                nums.set(nums.get(i + 1), input);
                i += 1;
            }
            else if (instruction == 4) {
                output = getParameter(nums, num, i, 1);
                i += 1;
            }
            else if (instruction == 5) {
                if (getParameter(nums, num, i, 1) != 0) {
                    i = getParameter(nums, num, i, 2) - 1;
                }
                else {
                    i += 2;
                }
            }
            else if (instruction == 6) {
                if (getParameter(nums, num, i, 1) == 0) {
                    i = getParameter(nums, num, i, 2) - 1;
                }
                else {
                    i += 2;
                }
            }
            else if (instruction == 7) {
                nums.set(nums.get(i + 3), getParameter(nums, num, i, 1) < getParameter(nums, num, i, 2) ? 1 : 0);
                i += 3;
            }
            else if (instruction == 8) {
                nums.set(nums.get(i + 3), getParameter(nums, num, i, 1) == getParameter(nums, num, i, 2) ? 1 : 0);
                i += 3;
            }
        }
        
        return output;
    }
    
    private int getParameter(List<Integer> nums, int instruction, int i, int index) {
        String s = Integer.toString(instruction);
        int mode = index > s.length() - 2 ? 0 : s.charAt(s.length() - 2 - index) - '0';
        return mode == 1 ? nums.get(i + index) : nums.get(nums.get(i + index));
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
