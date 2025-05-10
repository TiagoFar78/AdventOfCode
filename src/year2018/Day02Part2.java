package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
    private String solve(List<String> ids) {
        for (int i = 0; i < ids.size(); i++) {
            for (int j = 0; j < ids.size(); j++) {
                int diffPos = diffPosition(ids.get(i), ids.get(j));
                if (diffPos != -1) {
                    return ids.get(i).substring(0, diffPos) + ids.get(i).substring(diffPos + 1, ids.get(i).length());
                }
            }
        }
        
        throw new IllegalAccessError("whoops shouldn't be here");
    }
    
    private int diffPosition(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return -1;
        }
        
        int diffPos = -1;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (diffPos != -1) {
                    return -1;
                }
                
                diffPos = i;
            }
        }
        
        return diffPos;
    }

    @Override
    public String solveString() {
        List<String> ids = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            ids.add(reader.nextLine());
        }
        reader.close();
        
        return solve(ids);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
