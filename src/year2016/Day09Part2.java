package year2016;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day09Part2 extends Challenge {
    
    private long solve(String line) {
        long length = 0;
        
        Map<Integer, Integer> multiplierEnd = new HashMap<>();
        long multiplier = 1;
        for (int i = 0; i < line.length(); i++) {
            if (multiplierEnd.containsKey(i)) {
                multiplier /= multiplierEnd.get(i);
            }
            
            if (line.charAt(i) == '(') {
                int[] marker = getMarker(line, i);
                multiplier *= marker[2];
                
                int key = i + marker[0] + marker[1];
                i += marker[0] - 1;
                multiplierEnd.put(key, multiplierEnd.getOrDefault(key, 1) * marker[2]);
            }
            else {
                length += multiplier;
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
