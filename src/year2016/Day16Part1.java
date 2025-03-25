package year2016;

import java.util.Scanner;

import main.Challenge;

public class Day16Part1 extends Challenge {
    
    private String solve(String initialState, int targetSize) {
        StringBuilder sb = new StringBuilder(initialState);
        while (sb.length() < targetSize) {
            String reverse = sb.reverse().toString();
            sb.reverse();
            sb.append("0");
            sb.append(xorString(reverse));
        }
        
        String checkSum = checkSum(sb.toString().substring(0, targetSize));
        while (checkSum.length() % 2 == 0) {
            checkSum = checkSum(checkSum);
        }
        
        return checkSum; 
    }
    
    private String xorString(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c == '0' ? '1' : '0');
        }
        
        return sb.toString();
    }
    
    private String checkSum(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i += 2) {
            sb.append(s.charAt(i) == s.charAt(i + 1) ? '1' : '0');
        }
        
        return sb.toString();
    }
    
    @Override
    public String solveString() {
        Scanner reader = getInputFile();
        String initialState = reader.nextLine();
        reader.close();
        
        int targetSize = 272;
        return solve(initialState, targetSize);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
