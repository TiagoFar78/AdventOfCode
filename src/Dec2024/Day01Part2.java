package Dec2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day01Part2 extends Challenge {
    
    private long solve(List<Integer> left, List<Integer> right) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < right.size(); i++) {
            counts.put(right.get(i), counts.getOrDefault(right.get(i), 0) + 1);
        }
        
        int similarityScore = 0;
        for (int i = 0; i < left.size(); i++) {
            similarityScore += left.get(i) * counts.getOrDefault(left.get(i), 0);
        }
        
        return similarityScore;
    }
    
    public long solve() {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d+)");
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.find();
            left.add(Integer.parseInt(matcher.group(1)));
            right.add(Integer.parseInt(matcher.group(2)));
        }
        reader.close();
        
        return solve(left, right);
    }

}
