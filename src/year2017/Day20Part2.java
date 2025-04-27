package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day20Part2 extends Challenge {
    
    private int solve(List<int[][]> properties) {
        Map<Integer, Set<Integer>> colisions = new HashMap<>();
        
        for (int i = 0; i < properties.size(); i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                Set<Integer> colisionTime = colisionTime(properties, i, j);
                if (colisionTime != null) {
                    for (int time : colisionTime) {
                        Set<Integer> colisionTimesSet = colisions.getOrDefault(time, new HashSet<>());
                        colisionTimesSet.add(i);
                        colisionTimesSet.add(j);
                        colisions.put(time, colisionTimesSet);
                    }
                }
            }
        }
        
        System.out.println(colisions);
        
        Queue<Integer> pq = new PriorityQueue<>(colisions.keySet());
        Set<Integer> removed = new HashSet<>();
        while (!pq.isEmpty()) {
            int timestamp = pq.poll();
            int left = (int) colisions.get(timestamp).stream().filter(p -> !removed.contains(p)).count();
            if (left >= 2) {
                removed.addAll(colisions.get(timestamp));
            }
        }
        
        return properties.size() - removed.size();
    }
    
    private Set<Integer> colisionTime(List<int[][]> properties, int i, int j) {
        int[] xColisionTime = colisionTime(properties.get(i)[0][0], properties.get(i)[1][0], properties.get(i)[2][0], properties.get(j)[0][0], properties.get(j)[1][0], properties.get(j)[2][0]);
        if (xColisionTime == null) {
            return null;
        }
        
        int[] yColisionTime = colisionTime(properties.get(i)[0][1], properties.get(i)[1][1], properties.get(i)[2][1], properties.get(j)[0][1], properties.get(j)[1][1], properties.get(j)[2][1]);
        if (yColisionTime == null) {
            return null;
        }
        
        int[] zColisionTime = colisionTime(properties.get(i)[0][2], properties.get(i)[1][2], properties.get(i)[2][2], properties.get(j)[0][2], properties.get(j)[1][2], properties.get(j)[2][2]);
        if (zColisionTime == null) {
            return null;
        }
        
        int minusOnes = 0;
        Set<Integer> colisions = new HashSet<>();
        for (int time : xColisionTime) {
            if (time == -1) {
                minusOnes++;
                break;
            }
            
            if ((contains(yColisionTime, time) || contains(yColisionTime, -1)) && (contains(zColisionTime, time) || contains(zColisionTime, -1))) {
                colisions.add(time);
            }
        }
        
        for (int time : yColisionTime) {
            if (time == -1) {
                minusOnes++;
                break;
            }
            
            if ((contains(xColisionTime, time) || contains(xColisionTime, -1)) && (contains(zColisionTime, time) || contains(zColisionTime, -1))) {
                colisions.add(time);
            }
        }

        for (int time : zColisionTime) {
            if (time == -1) {
                minusOnes++;
                break;
            }
            
            if ((contains(yColisionTime, time) || contains(yColisionTime, -1)) && (contains(xColisionTime, time) || contains(xColisionTime, -1))) {
                colisions.add(time);
            }
        }
        
        if (minusOnes == 3) {
            colisions.add(0);
        }
        
        return colisions;
    }
    
    private boolean contains(int[] array, int target) {
        for (int element : array) {
            if (element == target) {
                return true;
            }
        }
        
        return false;
    }
    
    private int[] colisionTime(int pi, int vi, int ai, int pj, int vj, int aj) {
        int a = ai - aj;
        int b = 2 * (vi - vj) + ai - aj;
        int c = 2 * (pi - pj);
        
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return new int[] { -1 };
                }
                
                return null;
            }
            
            return c % b == 0 ? new int[] { - c / b } : null;
        }
        
        int discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return null;
        }
        
        if (discriminant == 0) {
            if (-b / (2 * a) < 0) {
                return null;
            }
            
            return new int[] { -b / (2 * a) };
        }
        
        int discriminantSqr = (int) Math.sqrt(discriminant);
        if (discriminantSqr * discriminantSqr != discriminant) {
            return null;
        }
        discriminant = discriminantSqr;
        
        if ((-b + discriminant) / (2 * a) < 0) {
            if ((-b - discriminant) / (2 * a) < 0) {
                return null;
            }
            
            return new int[] { (-b - discriminant) / (2 * a) };
        }
        else if ((-b - discriminant) / (2 * a) < 0) {            
            return new int[] { (-b + discriminant) / (2 * a) };
        }
        
        return new int[] { (-b + discriminant) / (2 * a), (-b - discriminant) / (2 * a) };
    }
    
    @Override
    public long solve() {
        List<int[][]> properties = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int[][] property = new int[3][3];
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    property[i][j] = Integer.parseInt(matcher.group(1 + i * 3 + j));
                }
            }
            
            properties.add(property);
        }
        reader.close();
        
        return solve(properties);
    }
    
}
