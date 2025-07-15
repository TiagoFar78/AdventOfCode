package year2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day17Part1 extends Challenge {
    
    private long solve(Map<Long, Long> nums) {
        List<String> grid = new ArrayList<>();
        grid.add("");
        
        int relativeBase = 0;
        for (long i = 0; ; i++) {
            Long num = nums.getOrDefault(i, 0L);
            if (num == 99) {
                break;
            }
            
            int instruction = (int) (num % 10);
            if (instruction == 1) {
                nums.put(getAddress(nums, num, i, 3, relativeBase), getParameter(nums, num, i, 1, relativeBase) + getParameter(nums, num, i, 2, relativeBase));
                i += 3;
            }
            else if (instruction == 2) {
                nums.put(getAddress(nums, num, i, 3, relativeBase), getParameter(nums, num, i, 1, relativeBase) * getParameter(nums, num, i, 2, relativeBase));
                i += 3;
            }
            else if (instruction == 3) {
                // TODO I am supposing there are no inputs for this problem.
                i += 1;
            }
            else if (instruction == 4) {
                long output = getParameter(nums, num, i, 1, relativeBase);
                if (output == 10) {
                    grid.add("");
                }
                else {
                    grid.set(grid.size() - 1, grid.get(grid.size() - 1) + ((char) output));
                }
                i += 1;
            }
            else if (instruction == 5) {
                if (getParameter(nums, num, i, 1, relativeBase) != 0) {
                    i = getParameter(nums, num, i, 2, relativeBase) - 1;
                }
                else {
                    i += 2;
                }
            }
            else if (instruction == 6) {
                if (getParameter(nums, num, i, 1, relativeBase) == 0) {
                    i = getParameter(nums, num, i, 2, relativeBase) - 1;
                }
                else {
                    i += 2;
                }
            }
            else if (instruction == 7) {
                nums.put(getAddress(nums, num, i, 3, relativeBase), getParameter(nums, num, i, 1, relativeBase) < getParameter(nums, num, i, 2, relativeBase) ? 1L : 0L);
                i += 3;
            }
            else if (instruction == 8) {
                nums.put(getAddress(nums, num, i, 3, relativeBase), getParameter(nums, num, i, 1, relativeBase) == getParameter(nums, num, i, 2, relativeBase) ? 1L : 0L);
                i += 3;
            }
            else if (instruction == 9) {
                relativeBase += getParameter(nums, num, i, 1, relativeBase);
                i += 1;
            }
        }
        
        grid.remove(grid.size() - 1);
        grid.remove(grid.size() - 1);
        
        int sum = 0;
        for (int i = 1; i < grid.size() - 1; i++) {
            for (int j = 1; j < grid.get(i).length() - 1; j++) {
                if (isIntersection(grid, i, j)) {
                    sum += i * j;
                }
            }
        }
        
        return sum;
    }
    
    private boolean isIntersection(List<String> grid, int row, int col) {        
        return grid.get(row).charAt(col) == '#' && 
                grid.get(row + 1).charAt(col) == '#' && grid.get(row - 1).charAt(col) == '#' &&
                grid.get(row).charAt(col + 1) == '#' && grid.get(row).charAt(col - 1) == '#';
    }
    
    private long getParameter(Map<Long, Long> nums, long instruction, long i, int index, long relativeBase) {
        String s = Long.toString(instruction);
        int mode = index > s.length() - 2 ? 0 : s.charAt(s.length() - 2 - index) - '0';
        return mode == 1 ? nums.getOrDefault(i + index, 0L) : mode == 0 ? nums.getOrDefault(nums.getOrDefault(i + index, 0L), 0L) : nums.getOrDefault(relativeBase + nums.getOrDefault(i + index, 0L), 0L);
    }
    
    private long getAddress(Map<Long, Long> nums, long instruction, long i, int index, long relativeBase) {
        String s = Long.toString(instruction);
        int mode = index > s.length() - 2 ? 0 : s.charAt(s.length() - 2 - index) - '0';
        return mode == 0 ? nums.getOrDefault(i + index, 0L) : relativeBase + nums.getOrDefault(i + index, 0L);
    }

    @Override
    public long solve() {
        Map<Long, Long> nums = new HashMap<>();
        
        Scanner reader = getInputFile();
        String[] sopcodes = reader.nextLine().split(",");
        reader.close();
        
        long i = 0;
        for (String sopcode : sopcodes) {
            nums.put(i, Long.parseLong(sopcode));
            i++;
        }
        
        return solve(nums);
    }

}
