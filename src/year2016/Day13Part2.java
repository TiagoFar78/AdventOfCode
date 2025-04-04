package year2016;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day13Part2 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
            { 1, 0 },
            { 0, 1 },
            { -1, 0 },
            { 0, -1 }
    };
    
    private int solve(int designersFavNumber, int steps) {
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        Set<String> seen = new HashSet<>();
        pq.add(new int[] { 1, 1, 0 });
        
        while (!pq.isEmpty()) {
            int cell[] = pq.poll();
            int x = cell[0];
            int y = cell[1];
            seen.add(x + " " + y);
            if (cell[2] == steps) {
                continue;
            }
            
            for (int[] dir : DIRECTIONS) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (seen.contains(newX + " " + newY) || newX < 0 || newY < 0 || isWall(newX, newY, designersFavNumber)) {
                    continue;
                }
                
                pq.add(new int[] { newX, newY, cell[2] + 1 });
            }
        }
        
        return seen.size();
    }
    
    private boolean isWall(int x, int y, int designersFavNumber) {
        int sum = x*x + 3*x + 2*x*y + y + y*y + designersFavNumber;
        boolean isOddNumberOfOnes = false;
        while (sum > 0) {
            if (sum % 2 == 1) {
                isOddNumberOfOnes = !isOddNumberOfOnes;
            }
            sum /= 2;
        }
        
        return isOddNumberOfOnes;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int designersFavNumber = Integer.parseInt(reader.nextLine());
        reader.close();
        
        int steps = 50;
        return solve(designersFavNumber, steps);
    }

}
