package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day14Part2 extends Challenge {
    
    private static final int TARGET_SEC = 2503;
    
    private long solve(List<int[]> speeds) {
        int[] scores = new int[speeds.size()];
        int[] distances = new int[speeds.size()];
        
        for (int sec = 0; sec < TARGET_SEC; sec++) {
            for (int i = 0; i < speeds.size(); i++) {
                int[] speed = speeds.get(i);
                distances[i] += sec % (speed[1] + speed[2]) < speed[1] ? speed[0] : 0;
            }
            
            int maxDistance = 0;
            for (int i = 0; i < speeds.size(); i++) {
                if (maxDistance < distances[i]) {
                    maxDistance = distances[i];
                }
            }
            
            for (int i = 0; i < speeds.size(); i++) {
                if (maxDistance == distances[i]) {
                    scores[i]++;
                }
            }
        }
        
        int maxScore = 0;
        for (int i = 0; i < speeds.size(); i++) {
            maxScore = Math.max(maxScore, scores[i]);
        }
        
        return maxScore;
    }

    @Override
    public long solve() {
        List<int[]> speeds = new ArrayList<>();
        
        Pattern pattern = Pattern.compile(".* can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int[] speed = new int[3];
            speed[0] = Integer.parseInt(matcher.group(1));
            speed[1] = Integer.parseInt(matcher.group(2));
            speed[2] = Integer.parseInt(matcher.group(3));
            speeds.add(speed);
        }
        reader.close();
        
        return solve(speeds);
    }

}
