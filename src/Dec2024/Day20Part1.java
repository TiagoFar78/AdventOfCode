package Dec2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day20Part1 extends Challenge {
    
    private static final int MIN_SAVE = 100; 
    private static final int[][] DIRECTIONS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private long solve(List<String> racetrack) {
        int rows = racetrack.size();
        int cols = racetrack.get(0).length();
        
        int startRow = -1;
        int startCol = -1;
        int endRow = -1;
        int endCol = -1;
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char c = racetrack.get(row).charAt(col);
                if (c == 'S') {
                    startRow = row;
                    startCol = col;
                }
                else if (c == 'E') {
                    endRow = row;
                    endCol = col;
                }
            }
        }
        
        List<int[]> path = new ArrayList<>();
        Map<String, Integer> cellStep = new HashMap<>();
        int currentRow = startRow;
        int currentCol = startCol;
        int currentSteps = 0;
        while (currentRow != endRow || currentCol != endCol) {
            path.add(new int[] {currentRow, currentCol});
            cellStep.put(currentRow + " " + currentCol, currentSteps);
            currentSteps++;
            for (int[] dir : DIRECTIONS) {
                char c = racetrack.get(currentRow + dir[0]).charAt(currentCol + dir[1]);
                if ((c == '.' || c == 'E') && !cellStep.containsKey((currentRow + dir[0]) + " " + (currentCol + dir[1]))) {
                    currentRow = currentRow + dir[0];
                    currentCol = currentCol + dir[1];
                    break;
                }
            }
        }
        path.add(new int[] {currentRow, currentCol});
        cellStep.put(currentRow + " " + currentCol, currentSteps);
        
        int possibilities = 0;
        for (int[] cell : path) {
            int currentStep = cellStep.get(cell[0] + " " + cell[1]);
            for (int[] dir : DIRECTIONS) {
                if (racetrack.get(cell[0] + dir[0]).charAt(cell[1] + dir[1]) == '#' &&
                        cellStep.getOrDefault((cell[0] + dir[0] * 2) + " " + (cell[1] + dir[1] * 2), -10) - currentStep - 2 >= MIN_SAVE) {
                    possibilities++;
                }
            }
        }
        
        return possibilities;
    }
    
    @Override
    public long solve() {
        List<String> racetrack = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            racetrack.add(reader.nextLine());
        }
        reader.close();
        
        return solve(racetrack);
    }
}
