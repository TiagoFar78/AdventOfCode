
package year2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import main.Challenge;

public class Day20Part1 extends Challenge {
    
    private static final int[][] DIR = {
            { -1, 0 },
            { 0, -1 },
            { 1, 0 },
            { 0, 1 }
    };
    
    private class Cell {
        
        int row;
        int col;
        boolean[] availableDirs;
        
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.availableDirs = new boolean[4];
        }
        
        public Cell(int x, int y, int availableDirection) {
            this(x, y);
            availableDirs[availableDirection] = true;
        }
        
        public boolean allowMove(int dir) {
            return availableDirs[dir];
        }
        
        private void justAddDir(int availableDirection) {
            availableDirs[availableDirection] = true;
        }
        
        private Cell addDir(int dirIndex, Map<String, Cell> cells) {
            availableDirs[dirIndex] = true;
            
            int newRow = row + DIR[dirIndex][0];
            int newCol = col + DIR[dirIndex][1];
            String key = newRow + " " + newCol;
            if (cells.containsKey(key)) {
                Cell newCell = cells.get(key);
                newCell.justAddDir((dirIndex + 2) % 4);
                return newCell;
            }
            
            Cell newCell = new Cell(newRow, newCol, (dirIndex + 2) % 4);
            cells.put(key, newCell);
            
            return newCell;
        }
        
        @Override
        public String toString() {
            return row + " " + col;
        }
        
    }
    
    private int solve(String directions) {
        Map<Character, Integer> toDir = new HashMap<>();
        toDir.put('N', 0);
        toDir.put('W', 1);
        toDir.put('S', 2);
        toDir.put('E', 3);
        
        Map<String, Cell> cells = new HashMap<>();
        Cell curr = new Cell(0, 0);
        cells.put(curr.toString(), curr);
        
        Stack<Cell> checkpoint = new Stack<>();
        for (int i = 0; i < directions.length(); i++) {
            if (directions.charAt(i) == '(') {
                checkpoint.add(curr);
                continue;
            }
            else if (directions.charAt(i) == '|') {
                curr = checkpoint.peek();
                continue;
            }
            else if (directions.charAt(i) == ')') {
                checkpoint.pop();
                continue;
            }
            
            curr = curr.addDir(toDir.get(directions.charAt(i)), cells);
        }
        
        Queue<int[]> pq = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        int maxDistance = 0;
        
        curr = cells.get(0 + " " + 0);
        seen.add(curr.toString());
        pq.add(new int[] { 0, 0, 0 });
        
        while (!pq.isEmpty()) {
            int[] element = pq.poll();
            int row = element[0];
            int col = element[1];
            int distance = element[2];
            maxDistance = Math.max(maxDistance, distance);
            Cell cell = cells.get(row + " " + col);
            
            for (int dirIndex = 0; dirIndex < 4; dirIndex++) {
                int[] dir = DIR[dirIndex];
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (cell.allowMove(dirIndex) && !seen.contains(newRow + " " + newCol)) {
                    pq.add(new int[] {newRow, newCol, distance + 1});
                    seen.add(newRow + " " + newCol);
                }
            }
        }
        
        return maxDistance;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String directions = reader.nextLine();
        reader.close();
        
        return solve(directions.substring(1, directions.length() - 1));
    }
    
}
