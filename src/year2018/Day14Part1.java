package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day14Part1 extends Challenge {

    private static final int RECIPES = 10; 
    
    private String solve(int start) {
        List<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);
        int first = 0;
        int second = 1;
        
        while (recipes.size() < start + RECIPES) {
            int nextRecipe = recipes.get(first) + recipes.get(second);
            if (nextRecipe == 0) {
                recipes.add(0);
            }
            else {
                List<Integer> newRecipes = new ArrayList<>();
                while (nextRecipe != 0) {
                    newRecipes.add(nextRecipe % 10);
                    nextRecipe /= 10;
                }
                
                for (int i = newRecipes.size() - 1; i >= 0; i--) {
                    recipes.add(newRecipes.get(i));
                }
            }
            
            first = (first + recipes.get(first) + 1) % recipes.size();
            second = (second + recipes.get(second) + 1) % recipes.size();
        }
        
        String res = "";
        for (int i = 0; i < RECIPES; i++) {
            res += Integer.toString(recipes.get(i + start));
        }
        
        return res;
    }
    
    @Override
    public String solveString() {
        Scanner reader = getInputFile();
        int start = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(start);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }
    
}
