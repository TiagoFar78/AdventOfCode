package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day05Part1 extends Challenge {
    
    private long solve(List<long[]> ranges, List<Long> ids) {
        int fresh = 0;
        for (long id : ids) {
            if (isInRange(ranges, id)) {
                fresh++;
            }
        }
        
        return fresh;
    }
    
    private boolean isInRange(List<long[]> ranges, long id) {
        for (long[] range : ranges) {
            if (range[0] <= id && id <= range[1]) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public long solve() {
        List<long[]> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (true) {
            String line = reader.nextLine();
            if (line.isEmpty()) {
                break;
            }
            
            String[] parts = line.split("-");
            ranges.add(new long[] { Long.parseLong(parts[0]), Long.parseLong(parts[1]) });
        }
        
        while (reader.hasNextLine()) {
            ids.add(Long.parseLong(reader.nextLine()));
        }
        reader.close();
        
        return solve(ranges, ids);
    }

}
