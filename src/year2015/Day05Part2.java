package year2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day05Part2 extends Challenge {
    
    private long solve(List<String> strings) {
        int niceStrings = 0;
        
        for (String s : strings) {
            niceStrings += isNice(s) ? 1 : 0;
        }
        
        return niceStrings;
    }
    
    private boolean isNice(String s) {
        boolean repeatedLetterWithSpace = false;
        boolean twoLetterMatch = false;
        Map<String, Integer> twoLetterPosition = new HashMap<>();
        
        char prevPrevC = s.charAt(0);
        char prevC = s.charAt(1);
        twoLetterPosition.put("" + prevPrevC + prevC, 0);
        for (int i = 2; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == prevPrevC) {
                repeatedLetterWithSpace = true;
            }
            
            String key = "" + prevC + c;
            if (twoLetterPosition.containsKey(key)) {
                if (i - 1 - twoLetterPosition.get(key) > 1) {
                    twoLetterMatch = true;   
                }
            }
            else {
                twoLetterPosition.put(key, i - 1);
            }
            
            prevPrevC = prevC;
            prevC = c;
        }
        
        return repeatedLetterWithSpace && twoLetterMatch;
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
