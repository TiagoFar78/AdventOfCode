package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day19Part2 extends Challenge {
    
    // 417 too high
    
    private int solve(Map<Integer, String> rules, List<String> messages) {
        String regex8 = "(" + createRegex(rules, 42) + ")+";
        String regex11 = "(" + createRegex(rules, 42) + ")+(" + createRegex(rules, 31) + ")+"; // 42 and 31 must have the same amount of executions
        Pattern pattern = Pattern.compile(regex8 + regex11);
        
        int matches = 0;
        for (String message : messages) {
            matches += pattern.matcher(message).matches() ? 1 : 0;
        }
        
        return matches;
    }
    
    private String createRegex(Map<Integer, String> rules, int target) {
        if (rules.get(target).contains("\"")) {
            return rules.get(target).substring(1, 2);
        }
        
        String[] ruleParts = rules.get(target).split(" ");
        int orIndex = orIndex(ruleParts);
        if (orIndex != -1) {
            String regex = "(";
            for (int i = 0; i < orIndex; i++) {
                regex += createRegex(rules, Integer.parseInt(ruleParts[i]));
            }
            
            regex += "|";
            
            for (int i = orIndex + 1; i < ruleParts.length; i++) {
                regex += createRegex(rules, Integer.parseInt(ruleParts[i]));
            }
            
            return regex + ")";
        }
        
        String regex = "";
        for (int i = 0; i < ruleParts.length; i++) {
            regex += createRegex(rules, Integer.parseInt(ruleParts[i]));
        }
        
        return regex;
    }
    
    private int orIndex(String[] ruleParts) {
        for (int i = 0; i < ruleParts.length; i++) {
            if (ruleParts[i].equals("|")) {
                return i;
            }
        }
        
        return -1;
    }
    
    @Override
    public long solve() {
        Map<Integer, String> rules = new HashMap<>();
        List<String> messages = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d+): (.*)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.equals("")) {
                break;
            }
            
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            int key = Integer.parseInt(matcher.group(1));
            String rule = matcher.group(2);
            rules.put(key, rule);
        }
        
        while (reader.hasNextLine()) {
            messages.add(reader.nextLine());
        }
        reader.close();
        
        return solve(rules, messages);
    }

}
