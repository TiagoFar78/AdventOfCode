package year2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day03Part1 extends Challenge {
    
    private int solve(String[] directions1, String[] directions2) {
        Map<Character, int[]> directions = new HashMap<>();
        directions.put('U', new int[] { 1, 0 });
        directions.put('R', new int[] { 0, 1 });
        directions.put('L', new int[] { 0, -1 });
        directions.put('D', new int[] { -1, 0 });
        
        Set<String> positions1 = new HashSet<>();
        int row = 0;
        int col = 0;
        for (String instruction : directions1) {
            int[] direction = directions.get(instruction.charAt(0));
            int distance = Integer.parseInt(instruction.substring(1));
            for (int d = 0; d < distance; d++) {
                row += direction[0];
                col += direction[1];
                positions1.add(row + " " + col);
            }
        }
        
        int closestIntersectionDistance = Integer.MAX_VALUE;
        row = 0;
        col = 0;
        for (String instruction : directions2) {
            int[] direction = directions.get(instruction.charAt(0));
            int distance = Integer.parseInt(instruction.substring(1));
            for (int d = 0; d < distance; d++) {
                row += direction[0];
                col += direction[1];
                if (positions1.contains(row + " " + col)) {
                    closestIntersectionDistance = Math.min(closestIntersectionDistance, Math.abs(row) + Math.abs(col));
                }
            }
        }
        
        return closestIntersectionDistance;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String[] directions1 = reader.nextLine().split(",");
        String[] directions2 = reader.nextLine().split(",");
        reader.close();
        
        return solve(directions1, directions2);
    }

}
