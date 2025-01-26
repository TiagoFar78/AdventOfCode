package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day05Part1 extends Challenge {
    
    private static final int ALPHABET_SIZE = 26;
    private static final char[] VOWELS = { 'a', 'e', 'i', 'o', 'u' };
    
    private long solve(List<String> strings) {
        int niceStrings = 0;
        
        for (String s : strings) {
            niceStrings += isNice(s) ? 1 : 0;
        }
        
        return niceStrings;
    }
    
    private boolean isNice(String s) {
        int[] counts = new int[ALPHABET_SIZE];
        boolean hasTwiceInARow = false;
        
        char prevC = s.charAt(0);
        counts[s.charAt(0) - 'a']++;
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            counts[c - 'a']++;
            
            if (prevC == c) {
                hasTwiceInARow = true;
            }
            else if (c == 'b' || c == 'd' || c == 'q' || c == 'y') {
                if (prevC == c - 1) {
                    return false;
                }
            }
            
            prevC = c;
        }
        
        int vowels = 0;
        for (char vowel : VOWELS) {
            vowels += counts[vowel - 'a'];
        }
        
        return hasTwiceInARow && vowels >= 3;
    }

    @Override
    public long solve() {
        List<String> strings = new ArrayList<>();
         
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            strings.add(reader.nextLine());
        }
        reader.close();
        
        return solve(strings);
    }

}
