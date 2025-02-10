package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day08Part2 extends Challenge {
    
    private int solve(List<String> strings) {
        int diff = 0;
        
        for (String s : strings) {
            diff += diff(s);
        }
        
        return diff;
    }
    
    private int diff(String s) {
        int diff = 2;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\\' || s.charAt(i) == '"') {
                diff++;
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
