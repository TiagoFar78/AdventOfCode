package year2024;

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

public class Day16Part2 extends Challenge {    

    private static final int[][] DIRECTIONS = {
        { -1, 0 },
        { 0, 1 },
        { 1, 0 },
        { 0, -1 }
    };
    
    private int solve(List<String> map) {
        int[] startingPos = findCharPos(map, 'S');
        
        List<Set<String>> paths = new ArrayList<>();
        Map<String, Integer> explored = new HashMap<>();
        Map<String, List<Integer>> pathSpread = new HashMap<>();
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[3] - b[3]);
        pq.add(new int[] { startingPos[0], startingPos[1], 1, 0, 0 });
        paths.add(new HashSet<>());
        while (true) {
            int[] cell = pq.poll();
            int row = cell[0];
            int col = cell[1];
            int dir = cell[2];
            int score = cell[3];
            explored.put(row + " " + col, score);
            paths.get(cell[4]).add(row + " " + col);
            
            if (map.get(row).charAt(col) == 'E') {
                return paths.get(cell[4]).size();
            }
            
            int newRow = row + DIRECTIONS[dir][0];
            int newCol = col + DIRECTIONS[dir][1];
            if (map.get(newRow).charAt(newCol) != '#') {
                if (!explored.containsKey(newRow + " " + newCol)) {
                    pq.add(new int[] { newRow, newCol, dir, score + 1, cell[4] });    
                }
                else if (explored.get(newRow + " " + newCol) == score - 1000 + 1) {
                    for (int newPath : pathSpread.get(newRow + " " + newCol)) {
                        paths.get(newPath).addAll(paths.get(cell[4]));   
                    }
                }
                
            }
            
            List<Integer> newPaths = new ArrayList<>();
            
            newRow = row + DIRECTIONS[(dir + 1) % 4][0];
            newCol = col + DIRECTIONS[(dir + 1) % 4][1];
            if (map.get(newRow).charAt(newCol) != '#' && !explored.containsKey(newRow + " " + newCol)) {
                pq.add(new int[] { newRow, newCol, (dir + 1) % 4, 1000 + score + 1, paths.size() });
                newPaths.add(paths.size());
                paths.add(new HashSet<>(paths.get(cell[4])));
            }
            
            newRow = row + DIRECTIONS[(dir - 1 + 4) % 4][0];
            newCol = col + DIRECTIONS[(dir - 1 + 4) % 4][1];
            if (map.get(newRow).charAt(newCol) != '#' && !explored.containsKey(newRow + " " + newCol)) {
                pq.add(new int[] { newRow, newCol, (dir - 1 + 4) % 4, 1000 + score + 1, paths.size() });
                newPaths.add(paths.size());
                paths.add(new HashSet<>(paths.get(cell[4])));
            }
            
            pathSpread.put(row + " " + col, newPaths);
        }
    }
    
    private int[] findCharPos(List<String> map, char c) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).length(); j++) {
                if (map.get(i).charAt(j) == c) {
                    return new int[] { i, j };
                }
            }
        }
        
        return null;
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
