package year2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day20Part2 extends Challenge {
    
    private static final int[][] DIRS = {
            { 1, 0 },
            { 0, 1 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private class Maze {
        
        private Map<String, int[]> grid = new HashMap<>();
        private int[] start = null;
        private int[] target = null;
        
        public Maze(List<String> grid) {
            int n = grid.size();
            int m = grid.get(0).length();
            Map<String, int[][]> portals = new HashMap<>();
            
            for (int i = 0; i < m; i++) {
                storePortal(portals, grid, 0, i, 1, i, 2, i, false, -1);
            }
            
            for (int i = 0; i < n; i++) {
                storePortal(portals, grid, i, 0, i, 1, i, 2, false, -1);
            }
            
            for (int i = 0; i < m; i++) {
                storePortal(portals, grid, n - 2, i, n - 1, i, n - 3, i, true, -1);
            }
            
            for (int i = 0; i < n; i++) {
                storePortal(portals, grid, i, m - 2, i, m - 1, i, m - 3, true, -1);
            }
            
            int[][] innerLimits = innerLimits(grid);
            int minRow = innerLimits[0][0];
            int maxRow = innerLimits[0][1];
            int minCol = innerLimits[1][0];
            int maxCol = innerLimits[1][1];
            for (int i = minCol; i <= maxCol; i++) {
                storePortal(portals, grid, minRow, i, minRow + 1, i, minRow - 1, i, true, 1);
            }
            
            for (int i = minRow; i <= maxRow; i++) {
                storePortal(portals, grid, i, minCol, i, minCol + 1, i, minCol - 1, true, 1);
            }

            for (int i = minCol; i <= maxCol; i++) {
                storePortal(portals, grid, maxRow - 1, i, maxRow, i, maxRow + 1, i, false, 1);
            }
            
            for (int i = minRow; i <= maxRow; i++) {
                storePortal(portals, grid, i, maxCol - 1, i, maxCol, i, maxCol + 1, false, 1);
            }
            
            start = portals.get("AA")[0];
            target = portals.get("ZZ")[1];
            
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (grid.get(i).charAt(j) == '.') {
                        this.grid.put(i + " " + j, new int[] { i, j, 0 });
                    }
                }
            }
        }
        
        private void storePortal(Map<String, int[][]> portals, List<String> grid, int row, int col, int nextRow, int nextCol, int exitRow, int exitCol, boolean isKeyOnFirstLetter, int level) {
            char c = grid.get(row).charAt(col);
            if (c >= 'A' && c <= 'Z') {
                String key = grid.get(row).charAt(col) + "" + grid.get(nextRow).charAt(nextCol);
                if (portals.containsKey(key)) {
                    int[][] portal = portals.get(key);
                    if (!isKeyOnFirstLetter) {
                        this.grid.put(portal[0][0] + " " + portal[0][1], new int[] { exitRow, exitCol, portal[0][2] });
                        this.grid.put(nextRow + " " + nextCol, new int[] { portal[1][0], portal[1][1], level });
                    }
                    else {
                        this.grid.put(portal[0][0] + " " + portal[0][1], new int[] { exitRow, exitCol, portal[0][2] });
                        this.grid.put(row + " " + col, new int[] { portal[1][0], portal[1][1], level });
                    }
                }
                else {
                    if (!isKeyOnFirstLetter) {
                        portals.put(key, new int[][] { { nextRow, nextCol, level }, { exitRow, exitCol } });
                    }
                    else {
                        portals.put(key, new int[][] { { row, col, level }, { exitRow, exitCol } });
                    }
                }
            }
            
        }
        
        private int[][] innerLimits(List<String> grid) {
            int minRow = Integer.MAX_VALUE;
            int maxRow = 0;
            int minCol = Integer.MAX_VALUE;
            int maxCol = 0;
            
            for (int i = 2; i < grid.size() - 2; i++) {
                for (int j = 2; j < grid.get(i).length() - 2; j++) {
                    if (grid.get(i).charAt(j) == ' ') {
                        minRow = Math.min(minRow, i);
                        maxRow = Math.max(maxRow, i);
                        minCol = Math.min(minCol, j);
                        maxCol = Math.max(maxCol, j);
                    }
                }
            }
            
            return new int[][] { { minRow, maxRow }, { minCol, maxCol } };
        }
        
        private int[] startLoc() {
            return start;
        }

        private int[] targetLoc() {
            return target;
        }
        
        private int[] locOf(int row, int col) {
            return grid.get(row + " " + col);
        }
    }
    
    private int solve(List<String> grid) {
        Maze maze = new Maze(grid);
        int[] target = maze.targetLoc();
        
        Set<String> seen = new HashSet<>();
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
        int[] start = maze.startLoc();
        pq.add(new int[] { start[0], start[1], 0, 0 });
        
        while (true) {
            int[] curr = pq.poll();
            if (curr[0] == target[0] && curr[1] == target[1] && curr[3] == 0) {
                return curr[2] - 1;
            }
            
            for (int[] dir : DIRS) {
                int newRow = curr[0] + dir[0];
                int newCol = curr[1] + dir[1];
                String key = newRow + " " + newCol + " " + curr[3];
                int[] locOf = maze.locOf(newRow, newCol);
                if (locOf == null || seen.contains(key) || curr[3] + locOf[2] < 0) {
                    continue;
                }
                seen.add(key);

                pq.add(new int[] { locOf[0], locOf[1], curr[2] + 1, curr[3] + locOf[2] });
            }
        }
    }
    
    @Override
    public long solve() {
        List<String> grid = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            grid.add(reader.nextLine());
        }
        reader.close();
        
        return solve(grid);
    }

}
