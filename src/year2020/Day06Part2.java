package year2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day06Part2 extends Challenge {
    
    private int solve(List<String> answers) {
        int sum = 0;
        int groupSize = 0;
        int[] counts = new int[26];
        
        for (String answer : answers) {
            if (answer.length() == 0) {
                for (int i = 0; i < 26; i++) {
                    if (counts[i] == groupSize) {
                        sum++;
                    }
                }
                groupSize = 0;
                Arrays.fill(counts, 0);
            }
            else {
                groupSize++;
                for (int i = 0; i < answer.length(); i++) {
                    counts[answer.charAt(i) - 'a']++;
                }
            }
        }
        

        for (int i = 0; i < 26; i++) {
            if (counts[i] == groupSize) {
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
