package year2015;

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

public class Day13Part2 extends Challenge {
    
    private long solve(Set<String> people, Map<String, Integer> relations) {
        for (String person : people) {
            relations.put(person + " YOU", 0);
            relations.put("YOU " + person, 0);
        }
        people.add("YOU");
        
        return calculateMaxRelation(relations, people);
    }
    
    private int calculateMaxRelation(Map<String, Integer> relations, Set<String> people) {
        int maxRelation = Integer.MIN_VALUE;
        
        for (String person : people) {
            List<String> currentPeople = new ArrayList<>();
            currentPeople.add(person);
            maxRelation = Math.max(maxRelation, calculateRelation(relations, currentPeople, people));
        }
        
        return maxRelation;
    }
    
    private int calculateRelation(Map<String, Integer> relations, List<String> currentPeople, Set<String> people) {
        String last = currentPeople.get(currentPeople.size() - 1);
        if (currentPeople.size() == people.size()) {
            String first = currentPeople.get(0);
            return relations.get(first + " " + last) + relations.get(last + " " + first);
        }
        
        int maxRelation = Integer.MIN_VALUE;
        for (String person : people) {
            if (!currentPeople.contains(person)) {
                List<String> newPeople = new ArrayList<>(currentPeople);
                newPeople.add(person);
                int relation = relations.get(last + " " + person) + relations.get(person + " " + last) + calculateRelation(relations, newPeople, people);
                maxRelation = Math.max(relation, maxRelation);
            }
        }
        
        return maxRelation;
    }

    @Override
    public long solve() {
        Set<String> people = new HashSet<>();
        Map<String, Integer> relations = new HashMap<>();
        
        Pattern pattern = Pattern.compile("(.*) would (.*) (\\d+) happiness units by sitting next to (.*).");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            people.add(matcher.group(1));
            int relation = Integer.parseInt(matcher.group(3));
            if (matcher.group(2).equals("lose")) {
                relation *= -1;
            }
            relations.put(matcher.group(1) + " " + matcher.group(4), relation);
        }
        reader.close();
        
        return solve(people, relations);
    }

}
