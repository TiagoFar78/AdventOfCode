package year2017;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day04Part1 extends Challenge {
    
    private int solve(List<String> passphrases) {
        int valid = 0;
        
        for (String passphrase : passphrases) {
            valid += isValid(passphrase) ? 1 : 0;
        }
        
        return valid;
    }
    
    private boolean isValid(String passphrase) {
        Set<String> seen = new HashSet<>();
        for (String password : passphrase.split(" ")) {
            if (seen.contains(password)) {
                return false;
            }
            seen.add(password);
        }
        
        return true;
    }

    @Override
    public long solve() {
        List<String> passphrases = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            passphrases.add(reader.nextLine());
        }
        reader.close();
        
        return solve(passphrases);
    }

}
