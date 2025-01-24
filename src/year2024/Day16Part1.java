package year2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day16Part1 extends Challenge {    

    private static final int[][] DIRECTIONS = {
        { -1, 0 },
        { 0, 1 },
        { 1, 0 },
        { 0, -1 }
    };
    
    private int solve(List<String> map) {
        int[] startingPos = findStartPos(map);
        
        Set<String> explored = new HashSet<>();
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[3] - b[3]);
        pq.add(new int[] { startingPos[0], startingPos[1], 1, 0 });
        while (true) {
            int[] cell = pq.poll();
            int row = cell[0];
            int col = cell[1];
            int dir = cell[2];
            int score = cell[3];
            explored.add(row + " " + col);
            
            if (map.get(row).charAt(col) == 'E') {
                return score;
            }
            
            int newRow = row + DIRECTIONS[dir][0];
            int newCol = col + DIRECTIONS[dir][1];
            if (map.get(newRow).charAt(newCol) != '#' && !explored.contains(newRow + " " + newCol)) {
                pq.add(new int[] { newRow, newCol, dir, score + 1 });
            }
            
            newRow = row + DIRECTIONS[(dir + 1) % 4][0];
            newCol = col + DIRECTIONS[(dir + 1) % 4][1];
            if (map.get(newRow).charAt(newCol) != '#' && !explored.contains(newRow + " " + newCol)) {
                pq.add(new int[] { newRow, newCol, (dir + 1) % 4, 1000 + score + 1 });
            }
            
            newRow = row + DIRECTIONS[(dir - 1 + 4) % 4][0];
            newCol = col + DIRECTIONS[(dir - 1 + 4) % 4][1];
            if (map.get(newRow).charAt(newCol) != '#' && !explored.contains(newRow + " " + newCol)) {
                pq.add(new int[] { newRow, newCol, (dir - 1 + 4) % 4, 1000 + score + 1 });
            }
        }        
    }
    
    private int[] findStartPos(List<String> map) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).length(); j++) {
                if (map.get(i).charAt(j) == 'S') {
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
