package year2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day14Part2 extends Challenge {
    
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
        long maxOre = 1_000_000_000_000L;
        
        long l = 1L;
        long r = maxOre;
        while (l < r) {
            long mid = (l + r + 1) / 2;
            if (neededOres(recipes, mid) > maxOre) {
                r = mid - 1;
            }
            else {
                l = mid;
            }
        }
        
        return l;
    }
    
    private long neededOres(Map<String, Reccipe> recipes, long amount) {
        return neededOREs(recipes, new HashMap<>(), "FUEL", amount);
    }
    
    private long neededOREs(Map<String, Reccipe> recipes, Map<String, Long> leftOvers, String current, long amount) {
        long leftOver = Math.min(amount, leftOvers.getOrDefault(current, 0L));
        leftOvers.put(current, leftOvers.getOrDefault(current, 0L) - leftOver);
        if (current.equals("ORE")) {
            return amount - leftOver;
        }
        
        long required = amount - leftOver;
        if (required == 0) {
            return 0;
        }
        
        long recipesRequired = required / recipes.get(current).amount + (required % recipes.get(current).amount == 0 ? 0 : 1);
        long ores = 0;
        List<String> chemicals = recipes.get(current).chemicals;
        List<Integer> amounts = recipes.get(current).chemicalAmounts;
        for (int i = 0; i < chemicals.size(); i++) {
            ores += neededOREs(recipes, leftOvers, chemicals.get(i), amounts.get(i) * recipesRequired);
        }
        
        leftOvers.put(current, leftOvers.getOrDefault(current, 0L) + recipesRequired * recipes.get(current).amount - required);
        return ores;
    }

    @Override
    public long solve() {
        System.out.println("a");
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
