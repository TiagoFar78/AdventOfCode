package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day07Part1 extends Challenge {
    
    private int solve(Map<String, List<String>> containers) {
        return countContainers(containers, "shiny gold", new HashMap<>()).size();
    }
    
    private Set<String> countContainers(Map<String, List<String>> containers, String current, Map<String, Set<String>> seen) {
        if (seen.containsKey(current)) {
            return seen.get(current);
        }
        
        if (!containers.containsKey(current)) {
            return new HashSet<>();
        }
        
        Set<String> count = new HashSet<>(containers.get(current));
        for (String container : containers.get(current)) {
            count.addAll(countContainers(containers, container, seen));
        }
        
        seen.put(current, count);
        return count;
    }
    
    @Override
    public long solve() {
        Map<String, List<String>> containers = new HashMap<>();
        
        Pattern fullLinePattern = Pattern.compile("^(.*?) bags contain (.*)\\.?$", Pattern.MULTILINE);
        Pattern bagNamePattern = Pattern.compile("\\d+ (.*?) bags?");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            Matcher matcher = fullLinePattern.matcher(line);
            matcher.matches();
            
            String container = matcher.group(1);
            List<String> containeds = new ArrayList<>();
            Matcher sub = bagNamePattern.matcher(matcher.group(2));
            while (sub.find()) {
                containeds.add(sub.group(1));
            }
            
            for (String contained : containeds) {
                if (!containers.containsKey(contained)) {
                    containers.put(contained, new ArrayList<>());
                }
                containers.get(contained).add(container);
            }
        }
        reader.close();
        
        return solve(containers);
    }

}
