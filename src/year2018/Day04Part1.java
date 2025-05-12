package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day04Part1 extends Challenge {
    
    private int solve(List<int[]> actions) {
        actions.sort((a,b) -> compare(a, b));
        
        Map<Integer, List<int[]>> sleepTimes = new HashMap<>();
        
        int maxSleepGuard = -1;
        Map<Integer, Integer> guardSleep = new HashMap<>();
        int currentGuard = 0;
        int fellAsleep = 0;
        for (int[] action : actions) {
            if (action[5] > 0) {
                currentGuard = action[5];
            }
            else if (action[5] == -1) {
                fellAsleep = action[4];
            }
            else {
                guardSleep.put(currentGuard, guardSleep.getOrDefault(currentGuard, 0) + action[4] - fellAsleep);
                if (maxSleepGuard == -1 || guardSleep.get(maxSleepGuard) < guardSleep.get(currentGuard)) {
                    maxSleepGuard = currentGuard;
                }
                
                if (!sleepTimes.containsKey(currentGuard)) {
                    sleepTimes.put(currentGuard, new ArrayList<>());
                }
                sleepTimes.get(currentGuard).add(new int[] { fellAsleep, action[4] });
            }
        }
        
        int mostSleptMinute = 0;
        int sleptTimes = 0;
        for (int i = 0; i < 60; i++) {
            int sleptTimesI = 0;
            for (int[] sleepSession : sleepTimes.get(maxSleepGuard)) {
                if (sleepSession[0] <= i && i < sleepSession[1]) {
                    sleptTimesI++;
                }
            }
            
            if (sleptTimesI > sleptTimes) {
                mostSleptMinute = i;
                sleptTimes = sleptTimesI;
            }
        }
        
        return maxSleepGuard * mostSleptMinute;
    }
    
    private int compare(int[] a1, int[] a2) {
        for (int i = 0; i < 5; i++) {
            if (a1[i] != a2[i]) {
                return a1[i] - a2[i];
            }
        }
        
        return 0;
    }

    @Override
    public long solve() {
        List<int[]> actions = new ArrayList<>();
        
        Pattern timePattern = Pattern.compile("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)");
        Pattern guardPattern = Pattern.compile("Guard #(\\d+) begins shift");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split("\\] ");
            Matcher matcher = timePattern.matcher(line[0]);
            matcher.matches();
            
            int[] action = new int[6];
            action[0] = Integer.parseInt(matcher.group(1));
            action[1] = Integer.parseInt(matcher.group(2));
            action[2] = Integer.parseInt(matcher.group(3));
            action[3] = Integer.parseInt(matcher.group(4));
            action[4] = Integer.parseInt(matcher.group(5));
            
            matcher = guardPattern.matcher(line[1]);
            if (matcher.matches()) {
                action[5] = Integer.parseInt(matcher.group(1));
            }
            else if (line[1].equals("falls asleep")) {
                action[5] = -1;
            }
            else if (line[1].equals("wakes up")) {
                action[5] = -2;
            }
            
            actions.add(action);
        }
        reader.close();
        
        return solve(actions);
    }

}
