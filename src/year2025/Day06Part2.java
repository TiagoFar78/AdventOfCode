package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day06Part2 extends Challenge {
    
    private long solve(List<String> problemLines) {
        int n = problemLines.get(0).length();
        int lines = problemLines.size();
        
        long grandTotal = 0;
        List<Integer> curr = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            int digit = 0;
            for (int j = 0; j < lines - 1; j++) {
                char c = problemLines.get(j).charAt(i);
                if (c != ' ') {
                    digit *= 10;
                    digit += c - '0';
                }
            }
            curr.add(digit);
            
            char c = problemLines.get(lines - 1).charAt(i);
            if (c == '*') {
                grandTotal += mult(curr);
                curr.clear();
                i--;
            }
            else if (c == '+') {
                grandTotal += sum(curr);
                curr.clear();
                i--;
            }
        }
        
        return grandTotal;
    }
    
    private long sum(List<Integer> nums) {
        long ans = 0;
        for (int num : nums) {
            ans += num;
        }
        
        return ans;
    }
    
    private long mult(List<Integer> nums) {
        long ans = 1;
        for (int num : nums) {
            ans *= num;
        }
        
        return ans;
    }
    
    @Override
    public long solve() {
        List<String> problemLines = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {            
            problemLines.add(reader.nextLine());
        }
        reader.close();
        
        return solve(problemLines);
    }

}
