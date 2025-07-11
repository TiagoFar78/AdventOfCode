package year2019;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day13Part2 extends Challenge {
    
    private static final int BALL = 4;
    private static final int PADDLE = 3;
    
    private long solve(Map<Long, Long> nums) {
        nums.put(0L, 2L);
        
        long score = 0;
        long x = 0;
        long y = 0;
        long ballX = 0;
        long paddleX = 0;
        int outputIndex = 0;
        
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
                long input = ballX == paddleX ? 0 : ballX > paddleX ? 1L : -1L;
                nums.put(getAddress(nums, num, i, 1, relativeBase), input);
                i += 1;
            }
            else if (instruction == 4) {
                if (outputIndex == 0) {
                    x = getParameter(nums, num, i, 1, relativeBase);
                }
                else if (outputIndex == 1) {
                    y = getParameter(nums, num, i, 1, relativeBase);
                }
                else {
                    long output = getParameter(nums, num, i, 1, relativeBase);
                    if (x == -1 && y == 0) {
                        score = output;
                    }
                    else if (output == BALL) {
                        ballX = x;
                    }
                    else if (output == PADDLE) {
                        paddleX = x;
                    }
                }
                outputIndex = (outputIndex + 1) % 3;
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
        
        return score;
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
