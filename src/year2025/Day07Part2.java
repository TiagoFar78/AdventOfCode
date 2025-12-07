package year2025;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private long solve(int starting, List<String> tachyon) {
        int n = tachyon.size();
        int m = tachyon.get(0).length();
        return countSplits(0, starting, tachyon, n, m, new HashMap<>());
    }
    
    private long countSplits(int row, int col, List<String> tachyon, int n, int m, Map<String, Long> seen) {
        if (row == n) {
            return 1;
        }
        
        if (seen.containsKey(row + " " + col)) {
            return seen.get(row + " " + col);
        }
        
        long count = 0;
        if (tachyon.get(row).charAt(col) == '^') {
            if (col - 1 >= 0) {
                count += countSplits(row + 1, col - 1, tachyon, n, m, seen);
            }

            if (col + 1 < m) {
                count += countSplits(row + 1, col + 1, tachyon, n, m, seen);
            }
        }
        else {
            count = countSplits(row + 1, col, tachyon, n, m, seen);
        }
        
        seen.put(row + " " + col, count);        
        return count;
    }
    
    @Override
    public long solve() {
        List<String> tachyon = new ArrayList<>();
        
        Scanner reader = getInputFile();
        int starting = reader.nextLine().indexOf("S");
        while (reader.hasNextLine()) {            
            tachyon.add(reader.nextLine());
        }
        reader.close();
        
        return solve(starting, tachyon);
    }

}
