package year2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day06Part2 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
        { -1, 0 },
        { 0, 1 },
        { 1, 0 },
        { 0, -1 }
    };
    
    private int solve(List<String> lab) {
        int n = lab.size();
        int m = lab.get(0).length();
        
        int initialRow = -1;
        int initialCol = -1;
        for (int i = 0; i < n; i++) {
            initialCol = lab.get(i).indexOf("^");
            if (initialCol != -1) {
                initialRow = i;
                break;
            }
        }
        
        int currentDirection = 0;
        int nextRow = initialRow + DIRECTIONS[currentDirection][0];
        int nextCol = initialCol + DIRECTIONS[currentDirection][1];
        Set<String> foundObstacles = new HashSet<>();
        while (nextRow >= 0 && nextRow < n && nextCol >= 0 && nextCol < m) {
            if (lab.get(nextRow).charAt(nextCol) == '#') {
                nextRow -= DIRECTIONS[currentDirection][0];
                nextCol -= DIRECTIONS[currentDirection][1];
                currentDirection = (currentDirection + 1) % 4;
            }
            else {
                lab.set(nextRow, lab.get(nextRow).substring(0, nextCol) + "#" + lab.get(nextRow).substring(nextCol + 1));
                if (isLoop(lab, initialRow, initialCol, n, m)) {
                    foundObstacles.add(nextRow + "," + nextCol);
                }
                lab.set(nextRow, lab.get(nextRow).substring(0, nextCol) + "." + lab.get(nextRow).substring(nextCol + 1));
            }
            
            nextRow += DIRECTIONS[currentDirection][0];
            nextCol += DIRECTIONS[currentDirection][1];
        }
        
        return foundObstacles.size();
    }
    
    private boolean isLoop(List<String> lab, int currentRow, int currentCol, int n, int m) {
        Set<String> seen = new HashSet<>();
        
        int currentDirection = 0;
        while (currentRow >= 0 && currentRow < n && currentCol >= 0 && currentCol < m) {            
            if (lab.get(currentRow).charAt(currentCol) == '#') {
                String key = currentRow + " " + currentCol + " " + currentDirection;
                if (seen.contains(key)) {
                    return true;
                }
                seen.add(key);
                
                currentRow -= DIRECTIONS[currentDirection][0];
                currentCol -= DIRECTIONS[currentDirection][1];
                currentDirection = (currentDirection + 1) % 4;
            }
            
            currentRow += DIRECTIONS[currentDirection][0];
            currentCol += DIRECTIONS[currentDirection][1];
        }
        
        return false;
    }

    @Override
    public long solve() {
        List<String> lab = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            lab.add(reader.nextLine());
        }
        
        reader.close();
        
        return solve(lab);
    }

}
