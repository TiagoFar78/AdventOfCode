package year2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day17Part2 extends Challenge {
    
    private static final int[][] DIRS = {
            { -1, 0 },
            { 0, 1 },
            { 1, 0 },
            { 0, -1 }
    };
    
    private long solve(Map<Long, Long> nums) {
        Map<Long, Long> initialNums = new HashMap<>(nums);
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
        
        int row = 0;
        int col = 0;
        int dir = 0;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {
                char c = grid.get(i).charAt(j);
                if (c != '.' && c != '#') {
                    row = i;
                    col = j;
                    dir = c == '^' ? 0 : c == '>' ? 1 : c == 'v' ? 2 : 3;
                }
            }
        }
        
        int maxRow = grid.size() - 1;
        int maxCol = grid.get(0).length() - 1;
        
        List<String> fullPath = new ArrayList<>();
        while (true) {
            int distance = 0;
            row += DIRS[dir][0];
            col += DIRS[dir][1];
            while (row >= 0 && row <= maxRow && col >= 0 && col <= maxCol && grid.get(row).charAt(col) == '#') {
                row += DIRS[dir][0];
                col += DIRS[dir][1];
                distance++;
            }
            
            row -= DIRS[dir][0];
            col -= DIRS[dir][1];
            if (distance > 0) {
                fullPath.add(Integer.toString(distance));
            }
            
            int newRow = row + DIRS[(dir + 1) % 4][0];
            int newCol = col + DIRS[(dir + 1) % 4][1];
            if (newRow >= 0 && newRow <= maxRow && newCol >= 0 && newCol <= maxCol && grid.get(newRow).charAt(newCol) == '#') {
                dir = (dir + 1) % 4;
                fullPath.add("R");
                continue;
            }
            
            newRow = row + DIRS[(dir - 1 + 4) % 4][0];
            newCol = col + DIRS[(dir - 1 + 4) % 4][1];
            if (newRow >= 0 && newRow <= maxRow && newCol >= 0 && newCol <= maxCol && grid.get(newRow).charAt(newCol) == '#') {
                dir = (dir - 1 + 4) % 4;
                fullPath.add("L");
                continue;
            }
            
            break;
        }
        
        List<List<String>> routines = getRoutines(fullPath);
        
        List<Integer> inputs = new ArrayList<>();
        for (List<String> routine : routines) {
            for (String element : routine) {
                for (char c : element.toCharArray()) {
                    inputs.add((int) c);
                }
                inputs.add(44);
            }
            inputs.remove(inputs.size() - 1);
            inputs.add(10);
        }
        
        inputs.add((int) 'n');
        inputs.add(10);
        
        nums = initialNums;
        nums.put(0L, 2L);
        int input = 0;
        relativeBase = 0;
        long output = 0;
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
                nums.put(getAddress(nums, num, i, 1, relativeBase), (long) inputs.get(input));
                input++;
                i += 1;
            }
            else if (instruction == 4) {
                output = getParameter(nums, num, i, 1, relativeBase);
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
        
        return output;
    }
    
    private List<List<String>> getRoutines(List<String> fullPath) {
        int maxRoutineLen = 20;

        for (int lenA = 1; lenA <= maxRoutineLen && lenA <= fullPath.size(); lenA++) {
            List<String> A = fullPath.subList(0, lenA);

            for (int startB = 0; startB < fullPath.size(); startB++) {
                for (int lenB = 1; lenB <= maxRoutineLen && startB + lenB <= fullPath.size(); lenB++) {
                    List<String> B = fullPath.subList(startB, startB + lenB);

                    for (int startC = 0; startC < fullPath.size(); startC++) {
                        for (int lenC = 1; lenC <= maxRoutineLen && startC + lenC <= fullPath.size(); lenC++) {
                            List<String> C = fullPath.subList(startC, startC + lenC);

                            List<String> main = matchMainRoutine(fullPath, A, B, C);
                            if (main != null) {
                                List<List<String>> routines = new ArrayList<>();
                                routines.add(main);
                                routines.add(new ArrayList<>(A));
                                routines.add(new ArrayList<>(B));
                                routines.add(new ArrayList<>(C));
                                return routines;
                            }
                        }
                    }
                }
            }
        }
        
        return new ArrayList<>();
    }

    private List<String> matchMainRoutine(List<String> path, List<String> A, List<String> B, List<String> C) {
        List<String> main = new ArrayList<>();
        int i = 0;
        while (i < path.size()) {
            if (matches(path, i, A)) {
                main.add("A");
                i += A.size();
            } else if (matches(path, i, B)) {
                main.add("B");
                i += B.size();
            } else if (matches(path, i, C)) {
                main.add("C");
                i += C.size();
            } else {
                return null;
            }
        }
        return main;
    }

    private boolean matches(List<String> path, int start, List<String> pattern) {
        if (start + pattern.size() > path.size()) return false;
        for (int i = 0; i < pattern.size(); i++) {
            if (!path.get(start + i).equals(pattern.get(i))) {
                return false;
            }
        }
        return true;
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
