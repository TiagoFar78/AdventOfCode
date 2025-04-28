package year2017;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day22Part1 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
            { -1, 0 },
            { 0, 1},
            { 1, 0 },
            { 0, -1 }
    };
    
    private int solve(List<String> initialGrid, int bursts) {
        Set<String> infected = new HashSet<>();
        for (int i = 0; i < initialGrid.size(); i++) {
            for (int j = 0; j < initialGrid.get(i).length(); j++) {
                if (initialGrid.get(i).charAt(j) == '#') {
                    infected.add(i + " " + j);
                }
            }
        }

        int infections = 0;
        int x = initialGrid.size() / 2;
        int y = initialGrid.get(0).length() / 2;
        int dir = 0;
        while (bursts > 0) {
            String key = x + " " + y;
            if (infected.contains(key)) {
                dir = (dir + 1 + DIRECTIONS.length) % DIRECTIONS.length;
                infected.remove(key);
            }
            else {
                dir = (dir - 1 + DIRECTIONS.length) % DIRECTIONS.length;
                infected.add(key);
                infections++;
            }
            
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
        
        int bursts = 10000;
        return solve(initialGrid, bursts);
    }
    
}
