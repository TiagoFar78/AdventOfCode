package year2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day06Part1 extends Challenge {
    
    private int solve(List<String> answers) {
        int sum = 0;
        boolean[] answered = new boolean[26];
        
        for (String answer : answers) {
            if (answer.length() == 0) {
                for (int i = 0; i < 26; i++) {
                    if (answered[i]) {
                        sum++;
                    }
                }
                Arrays.fill(answered, false);
            }
            else {
                for (int i = 0; i < answer.length(); i++) {
                    answered[answer.charAt(i) - 'a'] = true;
                }
            }
        }
        

        for (int i = 0; i < 26; i++) {
            if (answered[i]) {
                sum++;
            }
        }
        
        return sum;
    }
    
    @Override
    public long solve() {
        List<String> answers = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            answers.add(reader.nextLine());
        }
        reader.close();
        
        return solve(answers);
    }

}
