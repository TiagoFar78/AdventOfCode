package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day15Part1 extends Challenge {
    
    private long solve(List<Integer> starting) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < starting.size(); i++) {
            System.out.println("Round " + (i + 1) + " -> " + starting.get(i));
            seen.put(starting.get(i), i);
        }
        
        int last = starting.get(starting.size() - 1);
        int round = starting.size();
        int target = 2020;
        while (round < target) {
            int temp;
            if (!seen.containsKey(last)) {
                temp = 0;
            }
            else {
                temp = round - 1 - seen.get(last);
            }
            System.out.println("Round " + (round + 1) + " -> " + temp);
            
            seen.put(last, round - 1);
            last = temp;
            
            round++;
        }
        
        return last;
    }
    
    @Override
    public long solve() {
        List<Integer> starting = new ArrayList<>();
        
        Scanner reader = getInputFile();
        for (String number : reader.nextLine().split(",")) {
            starting.add(Integer.parseInt(number));
        }
        reader.close();
        
        return solve(starting);
    }

}
