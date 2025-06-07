package year2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day10Part1 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private String solve(List<int[]> points) {
        while(!seemsLikeAMessage(points)) {
            for (int[] point : points) {
                point[0] += point[2];
                point[1] += point[3];
            }
        }
        
        printMessage(points);
        return "^ Read the message above ^";
    }
    
    private boolean seemsLikeAMessage(List<int[]> points) {
        return countGroups(points) < points.size() / 4;
    }
    
    private int countGroups(List<int[]> points) {
        List<Set<String>> groups = new ArrayList<>();
        for (int[] point : points) {
            int groupAdded = -1;
            for (int[] dir : DIRECTIONS) {
                int x = point[0] + dir[0];
                int y = point[1] + dir[1];
                String key = x + " " + y;
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).contains(key)) {
                        if (groupAdded == -1) {
                            groups.get(i).add(point[0] + " " + point[1]);
                            groupAdded = i;
                        }
                        else if (groupAdded != i) {
                            groups.get(groupAdded).addAll(groups.remove(i));
                        }
                    }
                }
            }
            
            if (groupAdded == -1) {
                Set<String> newGroup = new HashSet<>();
                newGroup.add(point[0] + " " + point[1]);
                groups.add(newGroup);
            }
        }
        
        return groups.size();
    }
    
    private void printMessage(List<int[]> points) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int[] point : points) {
            minX = Math.min(minX, point[1]);
            minY = Math.min(minY, point[0]);
            maxX = Math.max(maxX, point[1]);
            maxY = Math.max(maxY, point[0]);
        }
        
        boolean[][] hasPoint = new boolean[maxX - minX + 3][maxY - minY + 3];
        for (int[] point : points) {
            hasPoint[point[1] - minX + 1][point[0] - minY + 1] = true;
        }
        
        for (boolean[] line : hasPoint) {
            for (boolean cell : line) {
                System.out.print(cell ? '#' : '.');
            }
            System.out.println();
        }
    }
    
    @Override
    public String solveString() {
        List<int[]> points = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("-?\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            
            int[] point = new int[4];
            for (int i = 0; i < point.length; i++) {
                matcher.find();
                point[i] = Integer.parseInt(matcher.group());
            }
            points.add(point);
        }
        reader.close();
        
        return solve(points);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
