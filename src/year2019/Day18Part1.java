package year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import main.Challenge;

public class Day18Part1 extends Challenge {
    
    private static final int[][] DIRS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private static final int CASE_DIFF = 'A' - 'a';
    
    private class Path {
        
        public int row;
        public int col;
        public boolean[] hasKeys;
        public int steps;
        public int keys;
        public int prevRow;
        public int prevCol;
        
        public Path(int row, int col) {
            this(row, col, new boolean[26], 0, 0, row, col);
        }
        
        private Path(int row, int col, boolean[] hasKeys, int steps, int keys, int prevRow, int prevCol) {
            this.row = row;
            this.col = col;
            this.hasKeys = hasKeys;
            this.steps = steps;
            this.keys = keys;
            this.prevRow = prevRow;
            this.prevCol = prevCol;
        }
        
        public Path clone() {
            return new Path(row, col, Arrays.copyOf(hasKeys, hasKeys.length), steps, keys, prevRow, prevCol); 
        }
        
        public String toKey() {
            String keysString = "";
            for (int i = 0; i < 26; i++) {
                keysString += hasKeys[i] ? "T" : "F";
            }
            
            return row + " " + col + " " + keysString;
        }
        
    }

    private int solve(List<String> map) {
        int rows = map.size();
        int cols = map.get(0).length();
        int row = 0;
        int col = 0;
        
        int keys = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = map.get(i).charAt(j);
                if (c >= 'a' && c <= 'z') {
                    keys++;
                }
                else if (c == '@') {
                    row = i;
                    col = j;
                }
            }
        }
        
        Map<String, Integer> seen = new HashMap<>();
        Queue<Path> pq = new PriorityQueue<>((a,b) -> a.steps - b.steps);
        pq.add(new Path(row, col));
        while (!pq.isEmpty()) {
            Path p = pq.poll();
            for (int[] dir : DIRS) {
                int newRow = p.row + dir[0];
                int newCol = p.col + dir[1];
                
                char c = map.get(newRow).charAt(newCol);
                if (c == '#' || (newRow == p.prevRow && newCol == p.prevCol)) {
                    continue;
                }
                
                if (c >= 'A' && c <= 'Z' && !p.hasKeys[c - CASE_DIFF - 'a']) {
                    continue;
                }

                Path newP = p.clone();
                newP.row = newRow;
                newP.col = newCol;
                newP.prevRow = p.row;
                newP.prevCol = p.col;
                newP.steps++;
                if (c >= 'a' && c <= 'z') {
                    if (!newP.hasKeys[c - 'a']) {
                        newP.prevRow = newRow;
                        newP.prevCol = newCol;
                        newP.keys++;
                        if (newP.keys == keys) {
                            return newP.steps;
                        }
                    }
                    newP.hasKeys[c - 'a'] = true;
                }
                else if (c >= 'A' && c <= 'Z' && p.hasKeys[c - CASE_DIFF - 'a']) {
                    newP.row = newRow;
                    newP.col = newCol;
                    newP.prevRow = newRow;
                    newP.prevCol = newCol;
                }
                
                String key = newP.toKey();
                if (!seen.containsKey(key) || seen.get(key) > newP.steps) {
                    seen.put(key, newP.steps);
                    pq.add(newP);                
                }
            }
        }
        
        throw new IllegalAccessError("Whoops");
    }
    
    @Override
    public long solve() {
        List<String> map = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            map.add(reader.nextLine());
        }
        reader.close();
        
        return solve(map);
    }

}
