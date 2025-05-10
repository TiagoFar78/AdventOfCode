package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part1 extends Challenge {
    
    private static final int ALPHABET = 26;
    
    private int solve(List<String> ids) {
        int lettersTwice = 0;
        int lettersThreeTimes = 0;
        
        for (String id : ids) {
            boolean[] count = countAmounts(id);
            if (count[0]) {
                lettersTwice++;
            }
            
            if (count[1]) {
                lettersThreeTimes++;
            }
        }
        
        return lettersTwice * lettersThreeTimes;
    }
    
    private boolean[] countAmounts(String id) {
        int[] counts = new int[ALPHABET];
        for (char c : id.toCharArray()) {
            counts[c - 'a']++;
        }
        
        boolean twice = false;
        boolean threeTimes = false;

        for (int i = 0; i < ALPHABET; i++) {
            if (counts[i] == 2) {
                twice = true;
            }
            else if (counts[i] == 3) {
                threeTimes = true;
            }
        }
        
        return new boolean[] { twice, threeTimes };
    }

    @Override
    public long solve() {
        List<String> ids = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            ids.add(reader.nextLine());
        }
        reader.close();
        
        return solve(ids);
    }

}
