package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day22Part1 extends Challenge {
    
    private final static int EXPANSIONS = 2000;    
    
    private long solve(List<Integer> secretNumbers) {
        long sum = 0;
        for (int secretNumber : secretNumbers) {
            sum += expand(secretNumber);
        }
        
        return sum;
    }
    
    private long expand(long secretNumber) {
        for (int i = 0; i < EXPANSIONS; i++) {
            secretNumber = evolve(secretNumber);
        }

        return secretNumber;
    }
    
    private long evolve(long secretNumber) {
        secretNumber ^= secretNumber * 64;
        secretNumber = secretNumber % 16777216;
        secretNumber ^= secretNumber / 32;
        secretNumber = secretNumber % 16777216;
        secretNumber ^= secretNumber * 2048;
        secretNumber = secretNumber % 16777216;

        return secretNumber;
    }

    @Override
    public long solve() {
        List<Integer> secretNumbers = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            secretNumbers.add(Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(secretNumbers);
    }

}
