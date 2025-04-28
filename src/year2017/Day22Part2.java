package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day22Part2 extends Challenge {
    
    private static final int CLEAN = 0;
    private static final int WEAKENED = 1;
    private static final int INFECTED = 2;
    private static final int FLAGGED = 3;
    
    private static final int[][] DIRECTIONS = {
            { -1, 0 },
            { 0, 1},
            { 1, 0 },
            { 0, -1 }
    };
    
    private int solve(List<String> initialGrid, int bursts) {
        Map<String, Integer> states = new HashMap<>();
        for (int i = 0; i < initialGrid.size(); i++) {
            for (int j = 0; j < initialGrid.get(i).length(); j++) {
                if (initialGrid.get(i).charAt(j) == '#') {
                    states.put(i + " " + j, INFECTED);
                }
            }
        }

        int infections = 0;
        int x = initialGrid.size() / 2;
        int y = initialGrid.get(0).length() / 2;
        int dir = 0;
        while (bursts > 0) {
            String key = x + " " + y;
            int state = states.getOrDefault(key, CLEAN);
            switch (state) {
                case CLEAN:
                    dir = (dir - 1 + DIRECTIONS.length) % DIRECTIONS.length;
                    break;
                case WEAKENED:
                    break;
                case INFECTED:
                    dir = (dir + 1 + DIRECTIONS.length) % DIRECTIONS.length;
                    break;
                case FLAGGED:
                    dir = (dir + 2 + DIRECTIONS.length) % DIRECTIONS.length;
                    break;
            }
            
            if (state == WEAKENED) {
                infections++;
            }
            states.put(key, (state + 1) % 4);
            
            x += DIRECTIONS[dir][0];
            y += DIRECTIONS[dir][1];
            bursts--;
        }
        
        return infections;
    }
    
    @Override
    public long solve() {
        List<String> initialGrid = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            initialGrid.add(reader.nextLine());
        }
        reader.close();
        
        int bursts = 10000000;
        return solve(initialGrid, bursts);
    }
    
}
