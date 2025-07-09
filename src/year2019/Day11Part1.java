package year2019;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day11Part1 extends Challenge {
    
    private static final int[][] DIR = {
            { -1, 0 },
            { 0, -1 },
            { 1, 0 },
            { 0, 1 },
    };
    
    private long solve(Map<Long, Long> nums) {
        Map<String, Boolean> grid = new HashMap<>();
        int row = 0;
        int col = 0;
        int dir = 0;
        boolean isColor = true;
        
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
                nums.put(getAddress(nums, num, i, 1, relativeBase), grid.getOrDefault(row + " " + col, false) ? 1L : 0L);
                i += 1;
            }
            else if (instruction == 4) {
                if (isColor) {
                    grid.put(row + " " + col, getParameter(nums, num, i, 1, relativeBase) == 1);
                    isColor = !isColor;
                }
                else {
                    if (getParameter(nums, num, i, 1, relativeBase) == 0) {
                        dir = (dir + 1) % DIR.length;
                    }
                    else {
                        dir = (dir - 1 + DIR.length) % DIR.length;
                    }
                    row += DIR[dir][0];
                    col += DIR[dir][1];
                    isColor = !isColor;
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
        
        return grid.keySet().size();
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
