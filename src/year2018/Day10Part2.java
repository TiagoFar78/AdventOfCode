package year2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day10Part2 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private int solve(List<int[]> points) {
        int i = 0;
        while(!seemsLikeAMessage(points, i)) {
            i++;
            for (int[] point : points) {
                point[0] += point[2];
                point[1] += point[3];
            }
        }
        
        return i;
    }
    
    private boolean seemsLikeAMessage(List<int[]> points, int i) {
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
    
    @Override
    public long solve() {
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

}
