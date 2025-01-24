package Dec2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day21Part1 extends Challenge {
    
    private static final int ROBOTS = 3;
    
    private static final int[][] NUMERIC_KEYPAD = {
            { 3, 1 },
            { 2, 0 },
            { 2, 1 },
            { 2, 2 },
            { 1, 0 },
            { 1, 1 },
            { 1, 2 },
            { 0, 0 },
            { 0, 1 },
            { 0, 2 },
            { 3, 2 } // A
    };
    
    private static final int[] findNumericKey(char c) {
        return NUMERIC_KEYPAD[c == 'A' ? 10 : c - '0'];
    }
    
    private static final int[][] DIRECTIONAL_KEYPAD = {
            { 0, 2 }, // A
            { 0, 1 }, // ^
            { 1, 0 }, // <
            { 1, 1 }, // v
            { 1, 2 }  // >
    };
    
    private static final int[] findDirectionalIndex(char c) {
        switch (c) {
            case 'A':
                return DIRECTIONAL_KEYPAD[0];
            case '^':
                return DIRECTIONAL_KEYPAD[1];
            case '<':
                return DIRECTIONAL_KEYPAD[2];
            case 'v':
                return DIRECTIONAL_KEYPAD[3];
            case '>':
                return DIRECTIONAL_KEYPAD[4];
        }
        
        return null;
    }
    
    private long solve(List<String> codes) {
        long complexity = 0;
        
        for (String code : codes) {
            complexity += calculateComplexity(code);
        }
                
        return complexity;
    }
    
    private long calculateComplexity(String code) {
        int size = 0;
        int[] prevNumericKey = findNumericKey('A');
        
        for (char n : code.toCharArray()) {
            int[] currentNumericKey = findNumericKey(n);
            
            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < Math.abs(currentNumericKey[0] - prevNumericKey[0]); i++) {
                newString.append(currentNumericKey[0] - prevNumericKey[0] > 0 ? 'v' : '^');
            }
            
            for (int i = 0; i < Math.abs(currentNumericKey[1] - prevNumericKey[1]); i++) {
                newString.append(currentNumericKey[1] - prevNumericKey[1] > 0 ? '>' : '<');
            }
            newString.append('A');
            
            size += calculateMoveLength(newString, ROBOTS - 1, findDirectionalIndex('A'));
            prevNumericKey = currentNumericKey;
        }
                
        System.out.println(code + " deu " + size + " * " + Integer.parseInt(code.substring(0, code.length() - 1)) + " = " + (size * Integer.parseInt(code.substring(0, code.length() - 1))));
        
        return size * Integer.parseInt(code.substring(0, code.length() - 1));
    }
    
    private int calculateMoveLength(StringBuilder newString, int robots, int[] prevKey) {
        if (robots == 0) {
            return newString.length();
        }
        
        String currentString = newString.toString();
        StringBuilder newString1 = new StringBuilder();
        int[] prevKey1 = prevKey;
        for (char c : currentString.toCharArray()) {
            int[] currentKey = findDirectionalIndex(c);
            
            for (int j = 0; j < Math.abs(currentKey[0] - prevKey1[0]); j++) {
                newString1.append(currentKey[0] - prevKey1[0] > 0 ? 'v' : '^');
            }
            for (int j = 0; j < Math.abs(currentKey[1] - prevKey1[1]); j++) {
                newString1.append(currentKey[1] - prevKey1[1] > 0 ? '>' : '<');
            }
            newString1.append('A');
            prevKey1 = currentKey;
        }

        StringBuilder newString2 = new StringBuilder();
        int[] prevKey2 = prevKey;
        for (char c : currentString.toCharArray()) {
            int[] currentKey = findDirectionalIndex(c);

            for (int j = 0; j < Math.abs(currentKey[1] - prevKey2[1]); j++) {
                newString2.append(currentKey[1] - prevKey2[1] > 0 ? '>' : '<');
            }
            for (int j = 0; j < Math.abs(currentKey[0] - prevKey2[0]); j++) {
                newString2.append(currentKey[0] - prevKey2[0] > 0 ? 'v' : '^');
            }
            newString2.append('A');
            prevKey2 = currentKey;
        }
        
        return Math.min(calculateMoveLength(newString1, robots - 1, prevKey1), calculateMoveLength(newString2, robots - 1, prevKey2));
    }
    
    @Override
    public long solve() {
        System.out.println("boas");
        List<String> codes = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            codes.add(reader.nextLine());
        }
        reader.close();
        
        return solve(codes);
    }
}
