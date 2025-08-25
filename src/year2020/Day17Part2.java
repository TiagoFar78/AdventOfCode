package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day17Part2 extends Challenge {
    
    private int solve(List<String> plane) {
        Set<String> active = toSet(plane);
        int minRow = -1;
        int maxRow = plane.size();
        int minCol = -1;
        int maxCol = plane.get(0).length();
        int minHeight = -1;
        int maxHeight = 1;
        int minWidth = -1;
        int maxWidth = 1;

        int cycles = 6;
        for (int i = 0; i < cycles; i++) {
            active = update(active, minRow, maxRow, minCol, maxCol, minHeight, maxHeight, minWidth, maxWidth);
            minRow--;
            maxRow++;
            minCol--;
            maxCol++;
            minHeight--;
            maxHeight++;
            minWidth--;
            maxWidth++;
        }
        
        return active.size();
    }
    
    private Set<String> toSet(List<String> plane) {
        int h = 0;
        int w = 0;
        Set<String> active = new HashSet<>();
        for (int r = 0; r < plane.size(); r++) {
            for (int c = 0; c < plane.get(0).length(); c++) {
                if (plane.get(r).charAt(c) == '#') {
                    active.add(r + " " + c + " " + h + " " + w);
                }
            }
        }
        
        return active;
    }
    
    private Set<String> update(Set<String> active, int minRow, int maxRow, int minCol, int maxCol, int minHeight, int maxHeight, int minWidth, int maxWidth) {
        Set<String> newActive = new HashSet<>();
        
        for (int r = minRow; r <= maxRow; r++) {
            for (int c = minCol; c <= maxCol; c++) {
                for (int h = minHeight; h <= maxHeight; h++) {
                    for (int w = minWidth; w <= maxWidth; w++) {
                        if (isActive(active, r, c, h, w)) {
                            newActive.add(r + " " + c + " " + h + " " + w);
                        }
                    }
                }
            }
        }
        
        return newActive;
    }
    
    private boolean isActive(Set<String> active, int r, int c, int h, int w) {
        int activeNeighbors = 0;
        activeNeighbors -= active.contains(r + " " + c + " " + h + " " + w) ? 1 : 0;
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                for (int d3 = -1; d3 <= 1; d3++) {
                    for (int d4 = -1; d4 <= 1; d4++) {
                        activeNeighbors += active.contains((r + d1) + " " + (c + d2) + " " + (h + d3) + " " + (w + d4)) ? 1 : 0;
                    }
                }
            }
        }
        
        if (active.contains(r + " " + c + " " + h + " " + w)) {
            return activeNeighbors == 2 || activeNeighbors == 3;
        }
        
        return activeNeighbors == 3;
    }
    
    @Override
    public long solve() {
        List<String> plane = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            plane.add(reader.nextLine());
        }
        reader.close();
        
        return solve(plane);
    }

}
