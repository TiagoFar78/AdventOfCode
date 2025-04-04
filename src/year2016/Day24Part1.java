package year2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day24Part1 extends Challenge {
    
    private final static int[][] DIRECTIONS = {
            {0, 1},
            {1, 0},
            {0, -1},
            {-1, 0}
    };
    
    private int solve(List<String> grid, int numbers) {
        int startingX = -1;
        int startingY = -1;
        for (int i = 0; i < grid.size(); i++) {
            if (grid.get(i).indexOf('0') != -1) {
                startingX = i;
                startingY = grid.get(i).indexOf('0');
                break;
            }
        }
        grid.set(startingX, grid.get(startingX).substring(0, startingY) + '.' + grid.get(startingX).substring(startingY + 1));
        
        int target = 0;
        for (int i = 1; i <= numbers; i++) {
            target |= (int)Math.pow(2, i - 1);
        }
        
        Set<String> seen = new HashSet<>();
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]); // ( x, y, steps, prevX, prevY, numbersBitwiseOr 
        pq.add(new int[] { startingX, startingY, 0, startingX, startingY, 0 });
        
        while (!pq.isEmpty()) {
            int[] cell = pq.poll();
            
            if (seen.contains(cell[0] + " " + cell[1] + " " + cell[5])) {
                continue;
            }
            seen.add(cell[0] + " " + cell[1] + " " + cell[5]);

            if (cell[5] == target) {
                return cell[2];
            }
            
            for (int[] dir : DIRECTIONS) {
                int newX = cell[0] + dir[0];
                int newY = cell[1] + dir[1];
                if (grid.get(newX).charAt(newY) == '#' || (grid.get(cell[0]).charAt(cell[1]) == '.' && newX == cell[3] && newY == cell[4])) {
                    continue;
                }
                
                int bitwiseOr = grid.get(newX).charAt(newY) != '.' ? (int)Math.pow(2, grid.get(newX).charAt(newY) - '0' - 1) : 0;
                pq.add(new int[] { newX, newY, cell[2] + 1, cell[0], cell[1], cell[5] | bitwiseOr});
            }
        }
        
        throw new IllegalAccessError("Woops shouldn't reach here");
    }
    
    @Override
    public long solve() {
        List<String> grid = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            grid.add(reader.nextLine());
        }
        reader.close();
        
        int numbers = 4;
        return solve(grid, numbers);
    }

}
