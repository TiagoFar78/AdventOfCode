package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day08Part1 extends Challenge {
    
    private int solve(List<String> strings) {
        int diff = 0;
        
        for (String s : strings) {
            diff += diff(s);
        }
        
        return diff;
    }
    
    private int diff(String s) {
        int diff = 2;
        
        for (int i = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) == '\\') {
                if (s.charAt(i + 1) != 'x') {
                    diff++;
                    i++;
                }
                else {
                    diff += 3;
                    i += 3;
                }
            }
        }
        
        return diff;
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
