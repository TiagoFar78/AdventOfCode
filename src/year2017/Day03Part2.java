package year2017;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day03Part2 extends Challenge {
    
    private final static int[][] DIRECTIONS = { 
            { 0, 1 },
            { 1, 1 },
            { 1, 0 },
            { 1, -1 },
            { 0, -1 },
            { -1, -1 },
            { -1, 0 },
            { -1, 1 },
    };
    
    private int solve(int target) {
        int x = 0;
        int y = 0;
        int n = 0;
        Map<String, Integer> grid = new HashMap<>();
        grid.put("0 0", 1);
        while (true) {
            n++;
            y++;
            if (storeValue(x, y, grid, target) != -1) {
                return storeValue(x, y, grid, target);
            }
            
            for (int i = 0; i < n - 1 + n; i++) {
                x--;
                if (storeValue(x, y, grid, target) != -1) {
                    return storeValue(x, y, grid, target);
                }
            }
            
            for (int i = 0; i < 2 * n; i++) {
                y--;
                if (storeValue(x, y, grid, target) != -1) {
                    return storeValue(x, y, grid, target);
                }
            }
            
            for (int i = 0; i < 2 * n; i++) {
                x++;
                if (storeValue(x, y, grid, target) != -1) {
                    return storeValue(x, y, grid, target);
                }
            }
            
            for (int i = 0; i < 2 * n; i++) {
                y++;
                if (storeValue(x, y, grid, target) != -1) {
                    return storeValue(x, y, grid, target);
                }
            }
        }
    }
    
    private int storeValue(int x, int y, Map<String, Integer> grid, int target) {
        int sum = 0;
        for (int[] dir : DIRECTIONS) {
            sum += grid.getOrDefault((x + dir[0]) + " " + (y + dir[1]), 0);
        }
        
        if (sum > target) {
            return sum;
        }
        
        grid.put(x + " " + y, sum);
        return -1;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int square = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(square);
    }

}
