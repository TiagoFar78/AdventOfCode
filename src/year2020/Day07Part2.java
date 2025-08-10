package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private long solve(Map<String, List<BagAmount>> contents) {
        return countContents(contents, "shiny gold", new HashMap<>());
    }
    
    private long countContents(Map<String, List<BagAmount>> contents, String current, Map<String, Long> seen) {
        if (seen.containsKey(current)) {
            return seen.get(current);
        }
        
        if (!contents.containsKey(current)) {
            return 1;
        }
        
        long count = 0;
        for (BagAmount content : contents.get(current)) {
            count += content.amount + content.amount * countContents(contents, content.name, seen);
        }
        
        seen.put(current, count);
        return count;
    }
    
    @Override
    public long solve() {
        Map<String, List<BagAmount>> contents = new HashMap<>();
        
        Pattern fullLinePattern = Pattern.compile("^(.*?) bags contain (.*)\\.?$", Pattern.MULTILINE);
        Pattern bagNamePattern = Pattern.compile("(\\d+) (.*?) bags?");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            Matcher matcher = fullLinePattern.matcher(line);
            matcher.matches();
            
            String bag = matcher.group(1);
            if (!contents.containsKey(bag)) {
                contents.put(bag, new ArrayList<>());
            }
            
            Matcher sub = bagNamePattern.matcher(matcher.group(2));
            while (sub.find()) {
                contents.get(bag).add(new BagAmount(sub.group(2), Integer.parseInt(sub.group(1))));
            }
        }
        reader.close();
        
        return solve(contents);
    }
    
    private class BagAmount {
        
        public String name;
        public int amount;
        
        public BagAmount(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }
        
        @Override
        public String toString() {
            return name + " " + amount;
        }
        
    }

}
