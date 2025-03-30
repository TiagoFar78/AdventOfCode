package year2016;

import java.util.Scanner;

import main.Challenge;

public class Day18Part1 extends Challenge {
    
    private int solve(String prevLine, int lines) {
        int safeTiles = 0;
        for (char c : prevLine.toCharArray()) {
            safeTiles += c == '.' ? 1 : 0;
        }
        
        for (int i = 1; i < lines; i++) {
            String newLine = "";
            
            for (int j = 0; j < prevLine.length(); j++) {
                if (isTrap(prevLine, j)) {
                    newLine += '^';
                }
                else {
                    newLine += '.';
                    safeTiles++;
                }
            }
            
            prevLine = newLine;
        }
        
        return safeTiles;
    }
    
    private boolean isTrap(String line, int i) {
        char left = i - 1 < 0 ? '.' : line.charAt(i - 1);
        char right = i + 1 == line.length() ? '.' : line.charAt(i + 1);
        return left != right;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String firstLine = reader.nextLine();
        reader.close();
        
        int lines = 40;
        return solve(firstLine, lines);
    }

}
