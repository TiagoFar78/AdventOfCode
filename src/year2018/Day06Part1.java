package year2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day06Part1 extends Challenge {
    
    private int solve(List<int[]> coordinates) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        
        for (int[] coordinate : coordinates) {
            minX = Math.min(minX, coordinate[0]);
            minY = Math.min(minY, coordinate[1]);
            maxX = Math.max(maxX, coordinate[0]);
            maxY = Math.max(maxY, coordinate[1]);
        }
        
        Set<Integer> onMapLimit = new HashSet<>();
        int[] areas = new int[coordinates.size()];
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                int closerIndex = closerIndex(coordinates, x, y);
                if (closerIndex == -1) {
                    continue;
                }
                
                areas[closerIndex]++;
                if (x == minX || x == maxX || y == minY || y == maxY) {
                    onMapLimit.add(closerIndex);
                }
            }
        }
        
        int maxArea = 0;
        for (int i = 0; i < coordinates.size(); i++) {
            if (!onMapLimit.contains(i)) {
                maxArea = Math.max(maxArea, areas[i]);
            }
        }
        
        return maxArea;
    }
    
    private int closerIndex(List<int[]> coordinates, int x, int y) {
        int closerIndex = -1;
        int distance = Integer.MAX_VALUE;
        
        for (int i = 0; i < coordinates.size(); i++) {
            int newDistance = Math.abs(coordinates.get(i)[0] - x) + Math.abs(coordinates.get(i)[1] - y);
            if (newDistance < distance) {
                closerIndex = i;
                distance = newDistance;
            }
            else if (newDistance == distance) {
                closerIndex = -1;
            }
        }
        
        return closerIndex;
    }

    @Override
    public long solve() {
        List<int[]> coordinates = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(", ");
            int[] coordinate = new int[2];
            coordinate[0] = Integer.parseInt(line[0]);
            coordinate[1] = Integer.parseInt(line[1]);
            coordinates.add(coordinate);
        }
        reader.close();
        
        return solve(coordinates);
    }

}
