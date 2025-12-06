package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day06Part1 extends Challenge {
    
    private long solve(List<String[]> problemLines) {
        int n = problemLines.get(0).length;
        long grandTotal = 0;
        for (int i = 0; i < n; i++) {
            grandTotal += solve(problemLines, i);
        }
        
        return grandTotal;
    }
    
    private long solve(List<String[]> problems, int problem) {
        if (problems.get(problems.size() - 1)[problem].equals("+")) {
            long ans = 0;
            for (int i = 0; i < problems.size() - 1; i++) {
                ans += toInt(problems.get(i)[problem]);
            }
            
            return ans;
        }
        
        long ans = 1;
        for (int i = 0; i < problems.size() - 1; i++) {
            ans *= toInt(problems.get(i)[problem]);
        }
        
        return ans;
    }
    
    private int toInt(String s) {
        return Integer.parseInt(s);
    }
    
    @Override
    public long solve() {
        List<String[]> problemLines = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {            
            problemLines.add(reader.nextLine().trim().split(" +"));
        }
        reader.close();
        
        return solve(problemLines);
    }

}
