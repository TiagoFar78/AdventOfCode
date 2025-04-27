package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day21Part2 extends Challenge {
    
    private int solve(List<String[]> enhancementRulesList, String pattern, int iterations) {
        Map<String, String> enhancementRules = new HashMap<String, String>();
        
        for (String[] rule : enhancementRulesList) {
            String key = rule[0];
            String value = rule[1];
            for (int i = 0; i < 4; i++) {
                enhancementRules.put(flip(key), value);
                key = rotate(key);
                enhancementRules.put(key, value);
            }
        }
        
        while (iterations > 0) {
            pattern = iterate(enhancementRules, pattern);
            iterations--;
        }
        
        int lit = 0;
        for (char c : pattern.toCharArray()) {
            if (c == '#') {
                lit++;
            }
        }
        
        return lit;
    }
    
    private String iterate(Map<String, String> enhancementRules, String pattern) {
        String[] rows = pattern.split("/");
        int n = rows.length;
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            grid[i] = rows[i].toCharArray();
        }

        int splitSize = n % 2 == 0 ? 2 : 3;
        int newN = n * (splitSize + 1) / splitSize;
        char[][] newGrid = new char[newN][newN];
        for (int i = 0; i < n / splitSize; i++) {
            for (int j = 0; j < n / splitSize; j++) {
                String enhancedSquare = enhancementRules.get(getSquare(grid, i * splitSize, j * splitSize, splitSize));
                putSquare(newGrid, i * (splitSize + 1), j * (splitSize + 1), enhancedSquare);
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < newN; i++) {
            result.append(new String(newGrid[i]));
            if (i < newN - 1) result.append("/");
        }
        
        return result.toString();
    }
    
    private String getSquare(char[][] grid, int x, int y, int size) {
        StringBuilder square = new StringBuilder();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                square.append(grid[x + i][y + j]);
            }
            
            if (i < size - 1) {
                square.append("/");
            }
        }
        
        return square.toString();
    }
    
    private void putSquare(char[][] grid, int x, int y, String squareS) {
        String[] square = squareS.split("/");
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[0].length(); j++) {
                grid[x + i][y + j] = square[i].charAt(j);
            }
        }
    }
    
    private String flip(String s) {
        int size3Bonus = s.charAt(3) == '/' ? 1 : 0;
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < 2 + size3Bonus; i++) {
            StringBuilder reverser = new StringBuilder(s.substring(i * (3 + size3Bonus), (i + 1) * (3 + size3Bonus) - 1));
            sb.append(reverser.reverse().toString());
            
            if (i < 1 + size3Bonus) {
                sb.append("/");
            }
        }
        
        return sb.toString();
    }
    
    private String rotate(String s) {
        String[] rows = s.split("/");
        int n = rows.length;
        char[][] grid = new char[n][n];

        for (int i = 0; i < n; i++) {
            grid[i] = rows[i].toCharArray();
        }

        char[][] rotated = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = grid[i][j];
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(new String(rotated[i]));
            if (i < n - 1) result.append("/");
        }

        return result.toString();
    }
    
    @Override
    public long solve() {
        List<String[]> enhancementRules = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            enhancementRules.add(reader.nextLine().split(" => "));
        }
        reader.close();
        
        String startingPattern = ".#./..#/###";
        int iterations = 18;
        return solve(enhancementRules, startingPattern, iterations);
    }
    
}
