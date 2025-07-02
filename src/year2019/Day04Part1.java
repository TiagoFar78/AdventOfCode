package year2019;

import java.util.Scanner;

import main.Challenge;

public class Day04Part1 extends Challenge {
    
    private int solve(int min, int max) {
        int validPasswords = 0;
        for (int i = min; i <= max; i++) {
            if (isValidPassword(i)) {
                validPasswords++;
            }
        }
        
        return validPasswords;
    }
    
    private boolean isValidPassword(int password) {
        String s = Integer.toString(password);
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) - s.charAt(i + 1) > 0) {
                return false;
            }
        }
        
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String[] line = reader.nextLine().split("-");
        int min = Integer.parseInt(line[0]);
        int max = Integer.parseInt(line[1]);
        reader.close();
        
        return solve(min, max);
    }

}
