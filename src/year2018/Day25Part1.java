package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day25Part1 extends Challenge {
    
    private int solve(List<int[]> points) {
        List<List<int[]>> constellations = new ArrayList<>();
        for (int[] point : points) {
            List<Integer> belonging = closeConstellations(constellations, point);
            if (belonging.size() == 0) {
                List<int[]> newConst = new ArrayList<>();
                newConst.add(point);
                constellations.add(newConst);
            }
            else {
                int i = belonging.size() - 1;
                while (i > 0) {
                    constellations.get(belonging.get(0)).addAll(constellations.get(belonging.get(i)));
                    constellations.remove((int) belonging.get(i));
                    i--;
                }
                
                constellations.get(belonging.get(0)).add(point);
            }
        }
        
        return constellations.size();
    }
    
    private List<Integer> closeConstellations(List<List<int[]>> constellations, int[] point) {
        List<Integer> closeConstellations = new ArrayList<>();
        
        for (int i = 0; i < constellations.size(); i++) {
            for (int[] coordinates : constellations.get(i)) {
                if (distance(coordinates, point) <= 3) {
                    closeConstellations.add(i);
                    break;
                }
            }
        }
        
        return closeConstellations;
    }
    
    private int distance(int[] point1, int[] point2) {
        int distance = 0;
        for (int i = 0; i < point1.length; i++) {
            distance += Math.abs(point1[i] - point2[i]);
        }
        
        return distance;
    }
    
    @Override
    public long solve() {
        List<int[]> points = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] coordinates = reader.nextLine().split(",");
            int[] point = new int[4];
            for (int i = 0; i < 4; i++) {
                point[i] = Integer.parseInt(coordinates[i]);
            }
            points.add(point);
        }
        reader.close();
        
        return solve(points);
    }
    
}
