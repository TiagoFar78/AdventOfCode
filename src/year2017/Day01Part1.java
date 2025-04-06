package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day01Part1 extends Challenge {
    
    private int solve(String captcha) {
        int sum = 0;
        for (int i = 0; i < captcha.length() - 1; i++) {
            sum += captcha.charAt(i) == captcha.charAt(i + 1) ? captcha.charAt(i) - '0' : 0;
        }
        
        return sum + (captcha.charAt(0) == captcha.charAt(captcha.length() - 1) ? captcha.charAt(0) - '0' : 0);
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        return solve(line);
    }

}
