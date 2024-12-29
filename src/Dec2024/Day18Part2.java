package Dec2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day18Part2 extends Challenge {
    
    private static final int SIZE = 71;
    private static final int[][] DIRECTIONS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private int solve(List<int[]> bytes) {
        int l = 0;
        int r = bytes.size() - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (isPossible(bytes, mid)) {
                l = mid + 1;
            }
            else {
                r = mid;
            }
        }
        
        System.out.println(bytes.get(l)[0] + "," + bytes.get(l)[1]);
        return 0;
    }
    
    private boolean isPossible(List<int[]> bytes, int consideredBytes) {
        Set<String> unreachableBytes = new HashSet<>();
        for (int i = 0; i <= consideredBytes; i++) {
            unreachableBytes.add(bytes.get(i)[0] + " " + bytes.get(i)[1]);
        }
        
        int[][] memory = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                memory[i][j] = Integer.MAX_VALUE;
            }
        }
        memory[0][0] = 0;
        
        List<int[]> unvisited = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!unreachableBytes.contains(i + " " + j)) {
                    unvisited.add(new int[] { i, j });   
                }
            }
        }
        
        Set<String> visited = new HashSet<>();
        while (!unvisited.isEmpty()) {
            Collections.sort(unvisited, (a, b) -> memory[a[0]][a[1]] - memory[b[0]][b[1]]);
            int[] cell = unvisited.remove(0);
            int row = cell[0];
            int col = cell[1];
            if (memory[row][col] == Integer.MAX_VALUE) {
                break;
            }
            
            visited.add(row + " " + col);
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                String key = newRow + " " + newCol;
                if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE || visited.contains(key) || unreachableBytes.contains(key)) {
                    continue;
                }
                
                memory[newRow][newCol] = Math.min(memory[newRow][newCol], memory[row][col] + 1);
            }
        }
        
        return memory[SIZE - 1][SIZE - 1] != Integer.MAX_VALUE;
    }
    
    @Override
    public long solve() {
        List<int[]> bytes = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d+),(\\d+)");
        
        Scanner reader = getInputFile();
        while(reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();            
            bytes.add(new int[] { Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) });
        }
        
        reader.close();
        
        return solve(bytes);
    }

}
