package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
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
        for (int i = 2; i <= num.length(); i++) {
            if (isRepeated(num, i)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isRepeated(String num, int times) {
        int n = num.length();
        if (n % times != 0) {
            return false;
        }
        
        for (int i = 0; i < n / times; i++) {
            for (int t = 1; t < times; t++) {
                if (num.charAt(i) != num.charAt(t * n / times + i)) {
                    return false;
                }
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
