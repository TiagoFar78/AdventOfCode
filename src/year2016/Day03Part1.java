package year2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day03Part1 extends Challenge {

    private int solve(List<int[]> triangles) {
        int possible = 0;
        
        for (int[] triangle : triangles) {
            Arrays.sort(triangle);
            possible += triangle[0] + triangle[1] > triangle[2] ? 1 : 0;
        }
        
        return possible;
    }
    
    @Override
    public long solve() {
        List<int[]> triangles = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            int[] triangle = new int[3];
            matcher.find();
            triangle[0] = Integer.parseInt(matcher.group());
            matcher.find();
            triangle[1] = Integer.parseInt(matcher.group());
            matcher.find();
            triangle[2] = Integer.parseInt(matcher.group());
            
            triangles.add(triangle);
        }
        reader.close();
        
        return solve(triangles);
    }
    
}
