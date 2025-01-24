package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day06Part1 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
        { -1, 0 },
        { 0, 1 },
        { 1, 0 },
        { 0, -1 }
    };
    
    private int solve(List<String> lab) {
        int n = lab.size();
        int m = lab.get(0).length();
        
        int currentRow = -1;
        int currentCol = -1;
        for (int i = 0; i < n; i++) {
            currentCol = lab.get(i).indexOf("^");
            if (currentCol != -1) {
                currentRow = i;
                break;
            }
        }
        
        int currentDirection = 0;
        while (currentRow >= 0 && currentRow < n && currentCol >= 0 && currentCol < m) {
            if (lab.get(currentRow).charAt(currentCol) != '#') {
                lab.set(currentRow, lab.get(currentRow).substring(0, currentCol) + "X" + lab.get(currentRow).substring(currentCol + 1));
            }
            else {
                currentRow -= DIRECTIONS[currentDirection][0];
                currentCol -= DIRECTIONS[currentDirection][1];
                currentDirection = (currentDirection + 1) % 4;
            }
            
            currentRow += DIRECTIONS[currentDirection][0];
            currentCol += DIRECTIONS[currentDirection][1];
        }
        
        int xCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (lab.get(i).charAt(j) == 'X') {
                    xCount++;
                }
            }
        }
        
        return xCount;
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
