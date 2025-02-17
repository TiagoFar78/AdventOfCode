package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day15Part1 extends Challenge {
    
    private final static int TEASPOONS = 100;
    
    private long solve(List<int[]> ingredients) {
        return getScore(ingredients, 0, new int[4], 0);
    }
    
    private long getScore(List<int[]> ingredients, int ingredient, int[] properties, int currentTeaspoons) {
        if (ingredients.size() == ingredient) {
            return calculateScore(properties);
        }
        
        long maxScore = 0;
        for (int i = currentTeaspoons; i < TEASPOONS; i++) {
            maxScore = Math.max(maxScore, getScore(ingredients, ingredient + 1, getProperties(ingredients.get(ingredient), properties, i - currentTeaspoons + 1), i + 1));
            removeProperties(ingredients.get(ingredient), properties, i - currentTeaspoons + 1);
        }
        
        return maxScore;
    }
    
    private int[] getProperties(int[] ingredient, int[] properties, int teaspoons) {
        for (int i = 0; i < properties.length; i++) {
            properties[i] += ingredient[i] * teaspoons;
        }
        
        return properties;
    }
    
    private void removeProperties(int[] ingredient, int[] properties, int teaspoons) {
        for (int i = 0; i < properties.length; i++) {
            properties[i] -= ingredient[i] * teaspoons;
        }
    }
    
    private long calculateScore(int[] properties) {
        long score = 1;
        
        for (int i = 0; i < properties.length; i++) {
            score *= properties[i] < 0 ? 0 : properties[i];
        }
        
        return score;
    }

    @Override
    public long solve() {
        List<int[]> ingredients = new ArrayList<>();
        
        Pattern pattern = Pattern.compile(".*: capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int[] speed = new int[5];
            speed[0] = Integer.parseInt(matcher.group(1));
            speed[1] = Integer.parseInt(matcher.group(2));
            speed[2] = Integer.parseInt(matcher.group(3));
            speed[3] = Integer.parseInt(matcher.group(4));
            speed[4] = Integer.parseInt(matcher.group(5));
            ingredients.add(speed);
        }
        reader.close();
        
        return solve(ingredients);
    }

}
