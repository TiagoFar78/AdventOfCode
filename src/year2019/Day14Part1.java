package year2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day14Part1 extends Challenge {
    
    private class Reccipe {
        
        private int amount;
        private List<String> chemicals;
        private List<Integer> chemicalAmounts;
        
        public Reccipe(int amount, List<String> chemicals, List<Integer> chemicalAmounts) {
            this.amount = amount;
            this.chemicals = chemicals;
            this.chemicalAmounts = chemicalAmounts;
        }
        
    }
    
    private long solve(Map<String, Reccipe> recipes) {
        return neededOREs(recipes, new HashMap<>(), "FUEL", 1);
    }
    
    private long neededOREs(Map<String, Reccipe> recipes, Map<String, Integer> leftOvers, String current, int amount) {
        int leftOver = Math.min(amount, leftOvers.getOrDefault(current, 0));
        leftOvers.put(current, leftOvers.getOrDefault(current, 0) - leftOver);
        if (current.equals("ORE")) {
            return amount - leftOver;
        }
        
        int required = amount - leftOver;
        if (required == 0) {
            return 0;
        }
        
        int recipesRequired = required / recipes.get(current).amount + (required % recipes.get(current).amount == 0 ? 0 : 1);
        long ores = 0;
        List<String> chemicals = recipes.get(current).chemicals;
        List<Integer> amounts = recipes.get(current).chemicalAmounts;
        for (int i = 0; i < chemicals.size(); i++) {
            ores += neededOREs(recipes, leftOvers, chemicals.get(i), amounts.get(i) * recipesRequired);
        }
        
        leftOvers.put(current, leftOvers.getOrDefault(current, 0) + recipesRequired * recipes.get(current).amount - required);
        return ores;
    }

    @Override
    public long solve() {
        Map<String, Reccipe> recipes = new HashMap<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" => ");
            recipes.put(line[1].split(" ")[1], createRecipe(line[1].split(" ")[0], line[0].split(", ")));
        }
        reader.close();
        
        return solve(recipes);
    }
    
    private Reccipe createRecipe(String amount, String[] chemicals) {
        List<String> chemicalName = new ArrayList<>();
        List<Integer> chemicalAmount = new ArrayList<>();
        for (int i = 0; i < chemicals.length; i++) {
            chemicalAmount.add(Integer.parseInt(chemicals[i].split(" ")[0]));
            chemicalName.add(chemicals[i].split(" ")[1]);
        }
        
        return new Reccipe(Integer.parseInt(amount), chemicalName, chemicalAmount);
    }

}
