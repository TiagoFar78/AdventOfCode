package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day14Part2 extends Challenge {

    private int solve(int start) {
        List<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);
        int first = 0;
        int second = 1;
        
        while (!foundStart(recipes, start, 0) && !foundStart(recipes, start, 1)) {
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
        
        int startSize = 0;
        int temp = start;
        while (temp > 0) {
            startSize++;
            temp /= 10;
        }
        
        return recipes.get(recipes.size() - 1) == start % 10 ? recipes.size() - startSize : recipes.size() - startSize - 1;
    }
    
    private boolean foundStart(List<Integer> recipes, int start, int offset) {
        while (start > 0) {
            if (recipes.get(recipes.size() - 1 - offset) != start % 10) {
                return false;
            }
            offset++;
            start /= 10;
        }
        
        return true;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int start = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(start);
    }
    
}
