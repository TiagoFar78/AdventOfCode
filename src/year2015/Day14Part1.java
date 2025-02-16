package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day14Part1 extends Challenge {
    
    private static final int TARGET_SEC = 2503;
    
    private long solve(List<int[]> speeds) {
        int maxDistance = 0;
        
        for (int[] speed : speeds) {
            int distance = TARGET_SEC / (speed[1] + speed[2]) * speed[0] * speed[1];
            int leftSeconds = TARGET_SEC % (speed[1] + speed[2]);
            if (leftSeconds > speed[1]) {
                leftSeconds = speed[1];
            }
            distance += leftSeconds * speed[0];
            maxDistance = Math.max(distance, maxDistance);
        }
        
        return maxDistance;
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
