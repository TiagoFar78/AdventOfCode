package Dec2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day20Part2 extends Challenge {
    
    private static final int MIN_SAVE = 100; 
    private static final int MAX_CHEAT = 20;
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
                int newRow = currentRow + dir[0];
                int newCol = currentCol + dir[1];
                if (!isValid(newRow, newCol, rows, cols)) {
                    continue;
                }
                
                char c = racetrack.get(newRow).charAt(newCol);
                if ((c == '.' || c == 'E') && !cellStep.containsKey(newRow + " " + newCol)) {
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
            
            for (int[] possibleCheat : getPossibleCheats(racetrack, cell[0], cell[1], rows, cols)) {
                if (cellStep.getOrDefault(possibleCheat[0] + " " + possibleCheat[1], -10) - currentStep - possibleCheat[2] >= MIN_SAVE) {
                    possibilities++;
                }
            }
        }
        
        return possibilities;
    }
    
    private List<int[]> getPossibleCheats(List<String> racetrack, int startRow, int startCol, int rows, int cols) {
        List<int[]> possibleCheats = new ArrayList<>();
        
        Set<String> explored = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        for (int[] dir : DIRECTIONS) {
            if (racetrack.get(startRow + dir[0]).charAt(startCol + dir[1]) == '#') {
                queue.add(new int[] {startRow + dir[0], startCol + dir[1]});
            }
        }
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            explored.add(cell[0] + " " + cell[1]);
            for (int[] dir : DIRECTIONS) {
                int newRow = cell[0] + dir[0];
                int newCol = cell[1] + dir[1];
                int cheatPicoseconds = Math.abs(newRow - startRow) + Math.abs(newCol - startCol);
                
                if (!isValid(newRow, newCol, rows, cols)) {
                    continue;
                }
                
                if (explored.contains(newRow + " " + newCol)) {
                    continue;
                }
                
                if (cheatPicoseconds < MAX_CHEAT) {
                    queue.add(new int[] {newRow, newCol});
                }
                
                if (racetrack.get(newRow).charAt(newCol) != '#') {
                    if (!explored.contains(newRow + " " + newCol)) {
                        possibleCheats.add(new int[] {newRow, newCol, cheatPicoseconds});
                    }
                    explored.add(newRow + " " + newCol);
                }
            }
        }
        
        return possibleCheats;
    }
    
    private boolean isValid(int row, int col, int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
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
