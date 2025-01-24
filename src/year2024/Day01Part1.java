package year2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day01Part1 extends Challenge {
    
    private long solve(List<Integer> left, List<Integer> right) {
        Collections.sort(left);
        Collections.sort(right);
        
        int distance = 0;
        for (int i = 0; i < left.size(); i++) {
            distance += Math.abs(right.get(i) - left.get(i));
        }
        
        return distance;
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
