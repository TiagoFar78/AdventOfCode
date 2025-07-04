package year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private final static int AMPLIFIERS = 5;
    
    @SuppressWarnings("unchecked")
    private int solve(List<Integer> nums) {
        int max = 0;
        for (int[] combination : combinations()) {
            List<Integer>[] programs = new ArrayList[AMPLIFIERS];
            Queue<Integer>[] ioSystem = new LinkedList[AMPLIFIERS];
            int[] instructionPointers = new int[AMPLIFIERS];
            for (int i = 0; i < AMPLIFIERS; i++) {
                programs[i] = new ArrayList<>(nums);
                ioSystem[i] = new LinkedList<>();
                ioSystem[i].add(combination[i]);
                instructionPointers[i] = 0;
            }
            
            ioSystem[0].add(0);
            
            int lastOutput = 0;
            while (lastOutput == 0) {
                for (int i = 0; i < AMPLIFIERS; i++) {
                    lastOutput = amplifierResult(programs[i], ioSystem, i, instructionPointers);
                }
            }
            
            while (!ioSystem[0].isEmpty()) {
                lastOutput = ioSystem[0].poll();
            }
            
            max = Math.max(lastOutput, max);
        }
        
        return max;
    }
    
    private List<int[]> combinations() {
        List<int[]> result = new ArrayList<>();
        List<Integer> base = Arrays.asList(5, 6, 7, 8, 9);
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
    
    private int amplifierResult(List<Integer> nums, Queue<Integer>[] ioSystem, int amplifierId, int[] instructionPointers) {
        for (int i = instructionPointers[amplifierId]; i < nums.size(); i++) {
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
                if (ioSystem[amplifierId].isEmpty()) {
                    instructionPointers[amplifierId] = i;
                    return 0;
                }
                
                nums.set(nums.get(i + 1), ioSystem[amplifierId].poll());
                i += 1;
            }
            else if (instruction == 4) {
                ioSystem[(amplifierId + 1) % AMPLIFIERS].add(getParameter(nums, num, i, 1));
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
        
        return 1;
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
