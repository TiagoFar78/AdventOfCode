package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day13Part1 extends Challenge {
    
    private int solve(int earliest, List<Integer> ids) {
        int minMinutesWaiting = Integer.MAX_VALUE;
        int minId = ids.get(0);
        for (int id : ids) {
            int minutesWaiting = (id - (earliest % id)) % id;
            if (minutesWaiting < minMinutesWaiting) {
                minId = id;
                minMinutesWaiting = minutesWaiting;
            }
        }
        
        return minId * minMinutesWaiting;
    }
    
    @Override
    public long solve() {
        List<Integer> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        int earliest = Integer.parseInt(reader.nextLine());
        for (String part : reader.nextLine().split(",")) {
            if (!part.equals("x")) {
                instructions.add(Integer.parseInt(part));
            }
        }
        reader.close();
        
        return solve(earliest, instructions);
    }

}
