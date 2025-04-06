package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day01Part2 extends Challenge {
    
    private int solve(String captcha) {
        int n = captcha.length();
        int sum = 0;
        for (int i = 0; i < captcha.length(); i++) {
            sum += captcha.charAt(i) == captcha.charAt((i + n / 2) % n) ? captcha.charAt(i) - '0' : 0;
        }
        
        return sum;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        return solve(line);
    }

}
