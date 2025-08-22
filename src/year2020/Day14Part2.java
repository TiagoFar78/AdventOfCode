package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day14Part2 extends Challenge {
    
    private long solve(List<String[]> instructions) {
        String mask = "";
        Map<Long, Integer> mem = new HashMap<>();
        
        for (String[] instruction : instructions) {
            if (instruction[0].equals("-1")) {
                mask = instruction[1];
            }
            else {
                for (long address : getAddresses(Integer.parseInt(instruction[0]), mask)) {
                    mem.put(address, Integer.parseInt(instruction[1]));
                }
            }
        }
        
        long sum = 0;
        for (int value : mem.values()) {
            sum += value;
        }
        
        return sum;
    }
    
    private List<Long> getAddresses(int n, String mask) {
        int maskSize = mask.length();
        boolean[] nBits = new boolean[maskSize];
        int index = maskSize - 1;
        while (n > 0) {
            nBits[index] = (n & 1) == 1;
            n >>= 1;
            index--;
        }
        
        return getAddresses(maskSize - 1, mask, nBits, 0L);
    }
    
    private List<Long> getAddresses(int i, String mask, boolean[] bits, long curr) {
        List<Long> l = new ArrayList<>();
        if (i == -1) {
            l.add(curr);
            return l;
        }
        
        if (mask.charAt(i) == 'X') {
            l.addAll(getAddresses(i - 1, mask, bits, (long) (curr + Math.pow(2, i))));
            l.addAll(getAddresses(i - 1, mask, bits, curr));
            return l;
        }
        
        if (mask.charAt(i) == '1' || bits[i]) {
            curr += Math.pow(2, i);
        }
        
        return getAddresses(i - 1, mask, bits, curr);
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
