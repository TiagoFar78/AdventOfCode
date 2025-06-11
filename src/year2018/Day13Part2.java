package year2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day13Part2 extends Challenge {
    
    private static final int[][] DIR = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };

    private static final int[] CURVE_DIR = { 1, -1, 1, -1 };
    private static final int[] INTERSECTION_DIR = { -1, 0, 1 };
    
    private String solve(List<String> grid) {
        Queue<int[]> activeQueue = new PriorityQueue<>((a, b) -> a[0] - b[0] == 0 ? a[1] - b[1] : a[0] - b[0]);
        Queue<int[]> unactiveQueue = new PriorityQueue<>((a, b) -> a[0] - b[0] == 0 ? a[1] - b[1] : a[0] - b[0]);
        Set<String> positions = new HashSet<>();
        
        List<int[]> robots = robotsCells(grid);
        activeQueue.addAll(robots);
        for (int[] robot : robots) {
            positions.add(robot[1] + " " + robot[0]);
        }
        
        while (true) {
            while (!activeQueue.isEmpty()) {
                int[] robot = activeQueue.poll();
                String key = robot[1] + " " + robot[0];
                if (!positions.contains(key)) {
                    continue;
                }
                positions.remove(key);
                
                char c = grid.get(robot[0]).charAt(robot[1]);
                if (c == '/') {
                    robot[2] = (robot[2] - CURVE_DIR[robot[2]] + 4) % 4;
                }
                else if (c == '\\') {
                    robot[2] = (robot[2] + CURVE_DIR[robot[2]] + 4) % 4;
                }
                else if (c == '+') {
                    robot[2] = (robot[2] + INTERSECTION_DIR[robot[3] % 3] + 4) % 4;
                    robot[3]++;
                }
                
                robot[0] += DIR[robot[2]][0];
                robot[1] += DIR[robot[2]][1];
                
                key = robot[1] + " " + robot[0];
                if (positions.contains(key)) {
                    positions.remove(key);
                    continue;
                }
                
                positions.add(key);
                unactiveQueue.add(robot);
            }
            
            Queue<int[]> temp = unactiveQueue;
            unactiveQueue = activeQueue;
            activeQueue = temp;
            
            if (positions.size() == 1) {
                return String.join(",", positions.iterator().next().split(" "));
            }
        }
    }
    
    private List<int[]> robotsCells(List<String> grid) {
        List<int[]> robots = new ArrayList<>();
        
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {
                char c = grid.get(i).charAt(j);
                if (c == '>') {
                    robots.add(new int[] {i, j, 0, 0});
                }
                else if (c == 'v') {
                    robots.add(new int[] {i, j, 1, 0});
                }
                else if (c == '<') {
                    robots.add(new int[] {i, j, 2, 0});
                }
                else if (c == '^') {
                    robots.add(new int[] {i, j, 3, 0});
                }
            }
        }
        
        return robots;
    }
    
    @Override
    public String solveString() {
        List<String> grid = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            grid.add(reader.nextLine());
        }
        reader.close();
        
        return solve(grid);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }
    
}
