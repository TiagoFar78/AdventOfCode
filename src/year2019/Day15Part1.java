package year2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day15Part1 extends Challenge {
    
    private class Execution {
        
        private int steps;
        private int row;
        private int col;
        private Map<Long, Long> nums;
        private long i;
        private int relativeBase;
        
        public Execution(int steps, int row, int col, Map<Long, Long> nums, long i, int relativeBase) {
            this.steps = steps;
            this.row = row;
            this.col = col;
            this.nums = nums;
            this.i = i;
            this.relativeBase = relativeBase;
        }
        
        public Execution clone() {
            return new Execution(steps, row, col, new HashMap<>(nums), i, relativeBase);
        }
        
    }
    
    private static final int[][] DIR = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    
    private int solve(Map<Long, Long> nums) {
        Set<String> seen = new HashSet<>();
        int row = 0;
        int col = 0;
        seen.add("0 0");
        Queue<Execution> pq = new PriorityQueue<>((a, b) -> a.steps - b.steps);
        pq.add(new Execution(0, row, col, nums, 0, 0));
        while (!pq.isEmpty()) {
            Execution e = pq.poll();
            for (int i = 1; i <= 4; i++) {
                Execution clone = e.clone();
                clone.steps++;
                int output = execute(clone, i);
                
                if (seen.contains(clone.row + " " + clone.col)) {
                    continue;
                }
                seen.add(clone.row + " " + clone.col);
                
                if (output == 2) {
                    return clone.steps;
                }
                else if (output == 1) {
                    pq.add(clone);
                }
            }
        }
        
        return 0;
    }
    
    private int execute(Execution e, int input) {
        Map<Long, Long> nums = e.nums;
        long i = e.i;
        int relativeBase = e.relativeBase;
        
        for (; ; i++) {
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
                nums.put(getAddress(nums, num, i, 1, relativeBase), (long) input);
                e.row += DIR[input - 1][0];
                e.col += DIR[input - 1][1];
                i += 1;
            }
            else if (instruction == 4) {
                int output = (int) getParameter(nums, num, i, 1, relativeBase);
                i += 1;
                e.i = i + 1;
                e.relativeBase = relativeBase;
                return output;
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
        
        return -1;
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
