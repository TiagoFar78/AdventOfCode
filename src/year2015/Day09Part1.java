package year2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day09Part1 extends Challenge {
    
    private int solve(List<String[]> distances) {
        List<String> locations = new ArrayList<>();
        Map<String, Integer> distancesMap = new HashMap<>();
        
        for (String[] distance : distances) {
            int i1 = indexOf(locations, distance[0]);
            int i2 = indexOf(locations, distance[1]);
            int distanceValue = Integer.parseInt(distance[2]);
            distancesMap.put(i1 + " " + i2, distanceValue);
            distancesMap.put(i2 + " " + i1, distanceValue);
        }
        
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < locations.size(); i++) {
            minDistance = Math.min(minDistance, calculateDistance(distancesMap, i, locations.size()));
        }
        
        return minDistance;
    }
    
    private int indexOf(List<String> locations, String location) {
        if (!locations.contains(location)) {
            locations.add(location);
            return locations.size() - 1;
        }
        
        return locations.indexOf(location);
    }
    
    private int calculateDistance(Map<String, Integer> distancesMap, int i, int max) {
        List<Integer> seen = new ArrayList<>();
        seen.add(i);
        return calculateDistance(distancesMap, seen, max);
    }
    
    private int calculateDistance(Map<String, Integer> distancesMap, List<Integer> seen, int max) {
        if (seen.size() == max) {
            return 0;
        }
        
        int minDistance = Integer.MAX_VALUE;
        
        for (int i = 0; i < max; i++) {
            if (!seen.contains(i)) {
                List<Integer> newSeen = new ArrayList<>(seen);
                newSeen.add(i);
                int nextDistance = distancesMap.get(seen.get(seen.size() - 1) + " " + i);
                minDistance = Math.min(minDistance, nextDistance + calculateDistance(distancesMap, newSeen, max));
            }
        }
        
        return minDistance;
    }

    @Override
    public long solve() {
        List<String[]> distances = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(.*) to (.*) = (.*)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            String[] distance = new String[3];
            distance[0] = matcher.group(1);
            distance[1] = matcher.group(2);
            distance[2] = matcher.group(3);
            distances.add(distance);
        }
        reader.close();
        
        return solve(distances);
    }
    
}
