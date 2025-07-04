package year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day07Part1 extends Challenge {
    
    private int solve(List<Integer> nums) {
        int max = 0;
        for (int[] combination : combinations()) {
            int lastOutput = 0;
            for (int i = 0; i < 5; i++) {
                lastOutput = amplifierResult(new ArrayList<>(nums), combination[i], lastOutput);
            }
            max = Math.max(lastOutput, max);
        }
        
        return max;
    }
    
    private List<int[]> combinations() {
        List<int[]> result = new ArrayList<>();
        List<Integer> base = Arrays.asList(0, 1, 2, 3, 4);
        permute(base, 0, result);
        return result;
    }
    
    private void permute(List<Integer> list, int start, List<int[]> result) {
        if (start == list.size()) {
            result.add(list.stream().mapToInt(i -> i).toArray());
            return;
        }
        for (int i = start; i < list.size(); i++) {
            Collections.swap(list, i, start);
            permute(list, start + 1, result);
            Collections.swap(list, i, start);
        }
    }
    
    private int amplifierResult(List<Integer> nums, int input1, int input2) {
        boolean isFirstInput = true;
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
                if (isFirstInput) {
                    nums.set(nums.get(i + 1), input1);
                    isFirstInput = false;
                }
                else {
                    nums.set(nums.get(i + 1), input2);
                }
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
