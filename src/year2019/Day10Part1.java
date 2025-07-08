package year2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day10Part1 extends Challenge {

    private int solve(List<String> map) {
        Set<Integer> asteroids = new HashSet<>();
        int maxRow = 0;
        int maxCol = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).length(); j++) {
                if (map.get(i).charAt(j) == '#') {
                    asteroids.add(getKey(i, j));
                    maxRow = Math.max(maxRow, i);
                    maxCol = Math.max(maxCol, j);
                }
            }
        }
        
        int max = 0;
        for (int asteroid : asteroids) {
            max = Math.max(max, countVisibleAsteroids(asteroids, toPos(asteroid), maxRow, maxCol));
        }
        
        return max;
    }
    
    private int countVisibleAsteroids(Set<Integer> asteroids, int[] current, int maxRow, int maxCol) {
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> distance(a, current) - distance(b, current));
        for (int asteroid : asteroids) {
            if (asteroid != getKey(current[0], current[1])) {
                pq.add(toPos(asteroid));
            }
        }
        
        int visible = 0;
        Set<Integer> blockedView = new HashSet<>();
        while (!pq.isEmpty()) {
            int[] asteroid = pq.poll();
            if (blockedView.contains(getKey(asteroid[0], asteroid[1]))) {
                continue;
            }
            
            visible++;
            int row = asteroid[0];
            int col = asteroid[1];
            int gdc = gdc(Math.abs(asteroid[0] - current[0]), Math.abs(asteroid[1] - current[1]));
            int rowMove = (asteroid[0] - current[0]) / gdc;
            int colMove = (asteroid[1] - current[1]) / gdc;
            while (row >= 0 && row <= maxRow && col >= 0 && col <= maxCol) {
                blockedView.add(getKey(row, col));
                row += rowMove;
                col += colMove;
            }
        }
        
        return visible;
    }
    
    private int distance(int[] a1, int[] a2) {
        return Math.abs(a1[0] - a2[0]) + Math.abs(a1[1] - a2[1]);
    }
    
    private int getKey(int row, int col) {
        return row * 100 + col;
    }
    
    private int[] toPos(int key) {
        return new int[] { key / 100, key % 100};
    }
    
    private int gdc(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gdc(n2, n1 % n2);
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
