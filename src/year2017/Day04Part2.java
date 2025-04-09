package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day04Part2 extends Challenge {
    
    private final static int ALPHABET_SIZE = 26;
    
    private int solve(List<String> passphrases) {
        int valid = 0;
        
        for (String passphrase : passphrases) {
            valid += isValid(passphrase) ? 1 : 0;
        }
        
        return valid;
    }
    
    private boolean isValid(String passphrase) {
        List<int[]> seen = new ArrayList<>();
        for (String password : passphrase.split(" ")) {
            int[] passwordChars = charsCount(password);
            if (contains(seen, passwordChars)) {
                return false;
            }
            
            seen.add(passwordChars);
        }
        
        return true;
    }
    
    private int[] charsCount(String password) {
        int[] charsCount = new int[ALPHABET_SIZE];
        
        for (char c : password.toCharArray()) {
            charsCount[c - 'a']++;
        }
        
        return charsCount;
    }
    
    private boolean contains(List<int[]> seen, int[] password) {
        outer: for (int[] e : seen) {
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (e[i] != password[i]) {
                    continue outer;
                }
            }
            
            return true;
        }
        
        return false;
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
