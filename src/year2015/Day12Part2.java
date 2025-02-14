package year2015;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day12Part2 extends Challenge {
    
    private int solve(String document) {
        Stack<Integer> stack = new Stack<>();
        Stack<Boolean> delStack = new Stack<>();
        
        for (int i = 0; i < document.length(); i++) {
            if (document.charAt(i) == '{') {
                stack.add(i);
                delStack.add(false);
            }
            else if (document.charAt(i) == '}') {
                int start = stack.pop();
                
                if (delStack.pop()) {
                    document = document.substring(0, start) + document.substring(i + 1);
                    i = start - 1;
                }
            }
            else if (containsRed(document, i)) {
                delStack.pop();
                delStack.add(true);
            }
        }
        
        return count(document);
    }
    
    private boolean containsRed(String document, int start) {
        char[] red = { ':', '\"', 'r', 'e', 'd', '\"' };
        
        int i;
        for (i = 0; start + i < document.length() && i < red.length; i++) {
            if (red[i] != document.charAt(start + i)) {
                return false;
            }
        }
        
        return i == red.length;
    }
    
    private int count(String document) {
        int total = 0;
        
        Pattern pattern = Pattern.compile("-?\\d+");
        
        Matcher matcher = pattern.matcher(document);
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group(0));
        }
        
        return total;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String document = reader.nextLine();
        reader.close();
        
        return solve(document);
    }

}
