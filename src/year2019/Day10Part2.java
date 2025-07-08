package year2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day10Part2 extends Challenge {

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
        int maxAsteroid = 0;
        for (int asteroid : asteroids) {
            int count = countVisibleAsteroids(asteroids, toPos(asteroid), maxRow, maxCol);
            if (count > max) {
                max = count;
                maxAsteroid = asteroid;
            }
        }
        
        int target = 200;
        int[] targetAsteroid = orderAsteroidsDestruction(asteroids, maxAsteroid, maxRow, maxCol).get(target - 1);
        return targetAsteroid[1] * 100 + targetAsteroid[0];
    }
    
    private List<int[]> orderAsteroidsDestruction(Set<Integer> asteroids, int maxAsteroid, int maxRow, int maxCol) {
        List<int[]> orderedAsteroids = new ArrayList<>();
        int[] current = toPos(maxAsteroid);
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> distance(a, current) - distance(b, current));
        for (int asteroid : asteroids) {
            if (asteroid != getKey(current[0], current[1])) {
                pq.add(toPos(asteroid));
            }
        }
        
        Set<Integer> blockedView = new HashSet<>();
        while (!pq.isEmpty()) {
            int[] asteroid = pq.poll();
            if (blockedView.contains(getKey(asteroid[0], asteroid[1]))) {
                continue;
            }
            
            int row = asteroid[0];
            int col = asteroid[1];
            int gdc = gdc(Math.abs(asteroid[0] - current[0]), Math.abs(asteroid[1] - current[1]));
            int rowMove = (asteroid[0] - current[0]) / gdc;
            int colMove = (asteroid[1] - current[1]) / gdc;
            int loopCount = 1;
            while (row >= 0 && row <= maxRow && col >= 0 && col <= maxCol) {
                blockedView.add(getKey(row, col));
                if (asteroids.contains(getKey(row, col))) {
                    orderedAsteroids.add(new int[] { row, col, loopCount });
                    loopCount++;
                }
                row += rowMove;
                col += colMove;
            }
        }
        
        orderedAsteroids.sort((a,b) -> compare(a, b, current));
        return orderedAsteroids;
    }
    
    private int compare(int[] a, int[] b, int[] current) {
        if (a[2] != b[2]) {
            return a[2] - b[2];
        }
        
        return comparePointsClockwise(a, b, current);
    }
    
    private int comparePointsClockwise(int[] p1, int[] p2, int[] center) {
        double angle1 = computeAngle(center, p1);
        double angle2 = computeAngle(center, p2);

        return Double.compare(angle1, angle2);
    }

    private static double computeAngle(int[] from, int[] to) {
        double dy = to[0] - from[0];
        double dx = to[1] - from[1];
        double angle = Math.atan2(dx, -dy);
        return (angle + 2 * Math.PI) % (2 * Math.PI);
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
