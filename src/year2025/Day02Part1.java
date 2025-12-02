package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part1 extends Challenge {
    
    private long solve(List<long[]> ranges) {
        long sum = 0L;
        for (long[] range : ranges) {
            for (long i = range[0]; i <= range[1]; i++) {
                if (isRepeated(Long.toString(i))) {
                    sum += i;
                }
            }
        }
        
        return sum;
    }
    
    private boolean isRepeated(String num) {
        int n = num.length();
        if (n % 2 == 1) {
            return false;
        }
        
        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) != num.charAt(n / 2 + i)) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public long solve() {
        List<long[]> ranges = new ArrayList<>();
        
        Scanner reader = getInputFile();
        for (String range : reader.nextLine().split(",")) {
            String[] rangeParts = range.split("-");
            ranges.add(new long[] { Long.parseLong(rangeParts[0]), Long.parseLong(rangeParts[1]) });
        }
        reader.close();
        
        return solve(ranges);
    }

}
