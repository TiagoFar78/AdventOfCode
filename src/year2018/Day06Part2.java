package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day06Part2 extends Challenge {
    
    private static final int MAX_DISTANCE = 10000;
    
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
        
        int area = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (distancesSum(coordinates, x, y) < MAX_DISTANCE) {
                    area++;
                }
            }
        }
        
        return area;
    }
    
    private int distancesSum(List<int[]> coordinates, int x, int y) {
        int sum = 0;
        for (int i = 0; i < coordinates.size(); i++) {
            sum += Math.abs(coordinates.get(i)[0] - x) + Math.abs(coordinates.get(i)[1] - y);
        }
        
        return sum;
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
