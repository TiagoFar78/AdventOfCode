package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day05Part2 extends Challenge {
    
    private long solve(List<long[]> ranges) {
        ranges.sort((a, b) -> a[0] == b[0] ? compare(a[1], b[1]) : compare(a[0], b[0]));
        
        long count = 0;
        long maxCovered = 0;
        for (long[] range : ranges) {
            if (range[1] <= maxCovered) {
                continue;
            }
            
            long l = Math.max(maxCovered + 1, range[0]);
            count += range[1] - l + 1;
            maxCovered = range[1];
        }
        
        return count;
    }
    
    private int compare(long num1, long num2) {
        long diff = num1 - num2;
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }
    
    @Override
    public long solve() {
        List<long[]> ranges = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (true) {
            String line = reader.nextLine();
            if (line.isEmpty()) {
                break;
            }
            
            String[] parts = line.split("-");
            ranges.add(new long[] { Long.parseLong(parts[0]), Long.parseLong(parts[1]) });
        }
        reader.close();
        
        return solve(ranges);
    }

}
