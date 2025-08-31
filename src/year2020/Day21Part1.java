package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day21Part1 extends Challenge {
    
    private int solve(List<String[][]> foods) {
        Map<String, Set<String>> possibleIngredients = new HashMap<>();
        
        for (String[][] food : foods) {
            Set<String> ingredients = new HashSet<>();
            for (String ingredient : food[0]) {
                ingredients.add(ingredient);
            }
            
            for (String allergen : food[1]) {
                if (!possibleIngredients.containsKey(allergen)) {
                    possibleIngredients.put(allergen, ingredients);
                }
                else {
                    possibleIngredients.get(allergen).retainAll(ingredients);
                }
            }
        }
        
        Set<String> alreadyMatchedIngredients = new HashSet<>();
        boolean isModified = true;
        while (isModified) {
            isModified = false;
            for (Set<String> value : possibleIngredients.values()) {
                if (value.size() == 1) {
                    alreadyMatchedIngredients.add(value.iterator().next());
                }
            }
            
            for (Set<String> value : possibleIngredients.values()) {
                if (value.size() != 1) {
                    isModified = isModified || value.removeAll(alreadyMatchedIngredients);
                }
            }
        }
        
        Set<String> allergenIngredients = new HashSet<>();
        for (String[][] food : foods) {
            for (String allergen : food[1]) {
                allergenIngredients.addAll(possibleIngredients.get(allergen));
            }
        }
        
        int sum = 0;
        for (String[][] food : foods) {
            for (String ingredient : food[0]) {
                if (!allergenIngredients.contains(ingredient)) {
                    sum++;
                }
            }
        }
        
        return sum;
    }
    
    @Override
    public long solve() {
        List<String[][]> foods = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" \\(contains ");
            String[] ingredients = line[0].split(" ");
            String[] allergens = line[1].substring(0, line[1].length() - 1).split(", ");
            foods.add(new String[][] { ingredients, allergens });
        }
        reader.close();
        
        return solve(foods);
    }
    
}
