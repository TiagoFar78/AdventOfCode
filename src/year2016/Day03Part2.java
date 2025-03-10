package year2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day03Part2 extends Challenge {

    private int solve(List<int[]> triangles) {
        List<Integer> col1 = new ArrayList<>();
        List<Integer> col2 = new ArrayList<>();
        List<Integer> col3 = new ArrayList<>();
        for (int[] triangle : triangles) {
            col1.add(triangle[0]);
            col2.add(triangle[1]);
            col3.add(triangle[2]);
        }

        List<int[]> newTriangles = new ArrayList<>();
        newTriangles.addAll(toTriangles(col1));
        newTriangles.addAll(toTriangles(col2));
        newTriangles.addAll(toTriangles(col3));
        
        int possible = 0;
        
        for (int[] triangle : newTriangles) {
            Arrays.sort(triangle);
            possible += triangle[0] + triangle[1] > triangle[2] ? 1 : 0;
        }
        
        return possible;
    }
    
    private List<int[]> toTriangles(List<Integer> col) {
        List<int[]> triangles = new ArrayList<>();
        for (int i = 0; i < col.size(); i += 3) {
            int[] triangle = new int[3];
            triangle[0] = col.get(i);
            triangle[1] = col.get(i + 1);
            triangle[2] = col.get(i + 2);
            triangles.add(triangle);
        }
        
        return triangles;
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
