package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day14Part1 extends Challenge {
    
    private long solve(List<String[]> instructions) {
        String mask = "";
        Map<Integer, Long> mem = new HashMap<>();
        
        for (String[] instruction : instructions) {
            if (instruction[0].equals("-1")) {
                mask = instruction[1];
            }
            else {
                int key = Integer.parseInt(instruction[0]);
                long value = applyMask(Integer.parseInt(instruction[1]), mask);
                mem.put(key, value);
            }
        }
        
        long sum = 0;
        for (Long value : mem.values()) {
            sum += value;
        }
        
        return sum;
    }
    
    
    private long applyMask(int n, String mask) {
        int maskSize = mask.length();
        int[] bits = new int[maskSize];
        int index = maskSize - 1;
        while (n > 0) {
            bits[index] = (n & 1);
            n >>= 1;
            index--;
        }
        
        for (int i = 0; i < maskSize; i++) {
            if (mask.charAt(i) != 'X') {
                bits[i] = mask.charAt(i) - '0';
            }
        }
        
        long res = 0;
        for (int i = 0; i < maskSize; i++) {
            res += bits[maskSize - 1 - i] == 1 ? Math.pow(2, i) : 0;
        }
        
        return res;
    }
    
    @Override
    public long solve() {
        List<String[]> instructions = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("mem\\[(\\d+)\\]");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" = ");
            Matcher matcher = pattern.matcher(line[0]);
            String instruction = matcher.matches() ? matcher.group(1) : "-1";
            instructions.add(new String[] { instruction, line[1] });
        }
        reader.close();
        
        return solve(instructions);
    }

}
