package year2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day07Part1 extends Challenge {
    
    private long solve(int starting, List<String> tachyon) {
        int n = tachyon.size();
        int m = tachyon.get(0).length();
        Set<String> splits = new HashSet<>();
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        pq.add(new int[] { 0, starting });
        
        while (!pq.isEmpty()) {
            int[] beam = pq.poll();
            int row = beam[0];
            int col = beam[1];
            
            while (row < n && !splits.contains(row + " " + col)) {
                if (tachyon.get(row).charAt(col) == '^') {
                    if (col - 1 >= 0) {
                        pq.add(new int[] { row, col - 1 });
                    }
                    
                    if (col + 1 < m) {
                        pq.add(new int[] { row, col + 1 });
                    }
                    
                    splits.add(row + " " + col);
                    break;
                }
                
                row++;
            }
        }
        
        return splits.size();
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
