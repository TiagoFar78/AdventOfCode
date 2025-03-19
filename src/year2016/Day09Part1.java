package year2016;

import java.util.Scanner;

import main.Challenge;

public class Day09Part1 extends Challenge {
    
    private int solve(String line) {
        int length = 0;
        
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                int[] marker = getMarker(line, i);
                i += marker[0] + marker[1] - 1;
                length += marker[1] * marker[2];
            }
            else {
                length++;
            }
        }
        
        return length;
    }
    
    private int[] getMarker(String line, int i) {
        int[] marker = new int[3];
        
        int j = i;
        while (line.charAt(j) != 'x') {
            j++;
        }
        
        marker[1] = Integer.parseInt(line.substring(i + 1, j));
        int xIndex = j;
        
        while (line.charAt(j) != ')') {
            j++;
        }
        
        marker[2] = Integer.parseInt(line.substring(xIndex + 1, j));
        marker[0] = j - i + 1;
        
        return marker;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        return solve(line);
    }
    
}
