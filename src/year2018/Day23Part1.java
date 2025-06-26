
package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day23Part1 extends Challenge {
    
    private int solve(List<int[]> nanobots) {
        int strongest = 0;
        for (int i = 1; i < nanobots.size(); i++) {
            if (nanobots.get(strongest)[3] < nanobots.get(i)[3]) {
                strongest = i;
            }
        }
        
        int inRange = 0;
        for (int[] nanobot : nanobots) {
            int distance = 0;
            for (int i = 0; i < 3; i++) {
                distance += Math.abs(nanobots.get(strongest)[i] - nanobot[i]);
            }
            
            if (distance <= nanobots.get(strongest)[3]) {
                inRange++;
            }
        }
        
        return inRange;
    }
    
    @Override
    public long solve() {
        System.out.println("a");
        List<int[]> nanobots = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            int[] nanobot = new int[4];
            for (int i = 0; i < 4; i++) {
                nanobot[i] = Integer.parseInt(matcher.group(i + 1));
            }
            nanobots.add(nanobot);
        }
        reader.close();
        
        return solve(nanobots);
    }
    
}
