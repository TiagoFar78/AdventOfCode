package year2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day19Part1 extends Challenge {
    
    private int solve(Map<String, List<String>> replacements, String molecule) {
        Set<String> molecules = new HashSet<>();
        
        for (String key : replacements.keySet()) {
            int i = molecule.indexOf(key);
            while (i != -1) {
                molecules.addAll(replace(molecule, i, key.length(), replacements.get(key)));
                i = molecule.indexOf(key, i + 1);
            }
        }
        
        return molecules.size();
    }
    
    private Set<String> replace(String molecule, int i, int length, List<String> replacements) {
        Set<String> molecules = new HashSet<>();
        for (String replacement : replacements) {
            molecules.add(molecule.substring(0, i) + replacement + molecule.substring(i + length));
        }
        
        return molecules;
    }
    
    @Override
    public long solve() {
        Map<String, List<String>> replacements = new HashMap<>();
        
        Pattern pattern = Pattern.compile("(.*) => (.*)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.length() == 0) {
                break;
            }
            
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            
            if (!replacements.containsKey(matcher.group(1))) {
                replacements.put(matcher.group(1), new ArrayList<>());
            }
            
            replacements.get(matcher.group(1)).add(matcher.group(2));
        }
        
        String molecule = reader.nextLine();
        reader.close();
        
        return solve(replacements, molecule);
    }

}
