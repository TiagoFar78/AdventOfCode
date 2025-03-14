package year2015;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day24Part1 extends Challenge {
    
    private long solve(List<Integer> presents) {
        int sum = 0;
        for (int present : presents) {
            sum += present;
        }
        
        List<List<Integer>> groups = sumToTarget(sum / 3, presents);
        groups.sort((a, b) -> a.size() - b.size());
        
        long minQuantumEntanglement = Long.MAX_VALUE;
        int minGroupSize = Integer.MAX_VALUE;
        
        for (int i = 0; i < groups.size() ; i++) {
            if (groups.get(i).size() > minGroupSize) {
                return minQuantumEntanglement;
            }
            
            if (canComposeSleigh(groups, i)) {
                minGroupSize = groups.get(i).size();
                long mult = 1;
                for (int element : groups.get(i)) {
                    mult *= element;
                }
                
                minQuantumEntanglement = Math.min(minQuantumEntanglement, mult);
            }
        }
        
        
        return minQuantumEntanglement;
    }
    
    private List<List<Integer>> sumToTarget(int target, List<Integer> presents) {
        return sumToTarget(new ArrayList<>(), target, presents, 0);
    }
    
    private List<List<Integer>> sumToTarget(List<Integer> current, int target, List<Integer> presents, int i) {
        if (target == 0) {
            List<List<Integer>> list = new ArrayList<>();
            list.add(current);
            return list;
        }
        

        if (target < 0 || i == presents.size()) {
            return new ArrayList<>();
        }
        
        List<List<Integer>> list = new ArrayList<>();
        list.addAll(sumToTarget(current, target, presents, i + 1));
        
        List<Integer> newCurrent = new ArrayList<>(current);
        newCurrent.add(presents.get(i));
        list.addAll(sumToTarget(newCurrent, target - presents.get(i), presents, i + 1));
        
        return list;
    }
    
    private boolean canComposeSleigh(List<List<Integer>> groups, int i) {
        Set<Integer> currentGroup = new HashSet<>(groups.get(i));
        
        for (int j = 0; j < groups.size(); j++) {
            boolean foundJGroup = true;
            for (int e : groups.get(j)) {
                if (currentGroup.contains(e)) {
                    foundJGroup = false;
                    break;
                }
            }
            
            if (!foundJGroup) {
                continue;
            }
            
            for (int k = j + 1; k < groups.size(); k++) {
                boolean foundKGroup = true;
                for (int e : groups.get(k)) {
                    if (currentGroup.contains(e) || groups.get(j).contains(e)) {
                        foundKGroup = false;
                        break;
                    }
                }
                
                if (foundKGroup) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public long solve() {
        List<Integer> presents = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            presents.add(Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        
        return solve(presents);
    }

}
