package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day17Part1 extends Challenge {
    
    private static final int[][] DIR = {
            { -1, 0 },
            { -1, 1 },
            { 0, 1 },
            { 1, 1 },
            { 1, 0 },
            { 1, -1 },
            { 0, -1 },
            { -1, -1 }
    };
    
    private int solve(List<String> plane) {
        Set<String> active = toSet(plane);
        int minRow = -1;
        int maxRow = plane.size();
        int minCol = -1;
        int maxCol = plane.get(0).length();
        int minHeight = -1;
        int maxHeight = 1;
        
        int cycles = 6;
        for (int i = 0; i < cycles; i++) {
            active = update(active, minRow, maxRow, minCol, maxCol, minHeight, maxHeight);
            minRow--;
            maxRow++;
            minCol--;
            maxCol++;
            minHeight--;
            maxHeight++;
        }
        
        return active.size();
    }
    
    private Set<String> toSet(List<String> plane) {
        int h = 0;
        Set<String> active = new HashSet<>();
        for (int r = 0; r < plane.size(); r++) {
            for (int c = 0; c < plane.get(0).length(); c++) {
                if (plane.get(r).charAt(c) == '#') {
                    active.add(r + " " + c + " " + h);
                }
            }
        }
        
        return active;
    }
    
    private Set<String> update(Set<String> active, int minRow, int maxRow, int minCol, int maxCol, int minHeight, int maxHeight) {
        Set<String> newActive = new HashSet<>();
        
        for (int r = minRow; r <= maxRow; r++) {
            for (int c = minCol; c <= maxCol; c++) {
                for (int h = minHeight; h <= maxHeight; h++) {
                    if (isActive(active, r, c, h)) {
                        newActive.add(r + " " + c + " " + h);
                    }
                }
            }
        }
        
        return newActive;
    }
    
    private boolean isActive(Set<String> active, int r, int c, int h) {
        int activeNeighbors = 0;
        activeNeighbors += active.contains(r + " " + c + " " + (h - 1)) ? 1 : 0;
        activeNeighbors += active.contains(r + " " + c + " " + (h + 1)) ? 1 : 0;
        for (int i = -1; i <= 1; i++) {
            for (int[] dir : DIR) {
                activeNeighbors += active.contains((r + dir[0]) + " " + (c + dir[1]) + " " + (h + i)) ? 1 : 0;
            }
        }
        
        if (active.contains(r + " " + c + " " + h)) {
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
