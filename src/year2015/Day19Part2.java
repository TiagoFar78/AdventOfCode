package year2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day19Part2 extends Challenge {
    
    private final static String TARGET = "e";
    
    private int solve(Map<String, String> replacements, String molecule) {
        List<String> orderedKeys = new ArrayList<>(replacements.keySet());
        orderedKeys.sort((a, b) -> b.length() - a.length());

        int steps = 0;
        while (!molecule.equals(TARGET)) {
            int i = 0;
            while (molecule.indexOf(orderedKeys.get(i)) == -1) {
                i++;
            }
            
            molecule = replace(molecule, molecule.indexOf(orderedKeys.get(i)), orderedKeys.get(i).length(), replacements.get(orderedKeys.get(i)));
            steps++;
        }
        
        return steps;
    }
    
    private String replace(String molecule, int i, int length, String replacement) {        
        return molecule.substring(0, i) + replacement + molecule.substring(i + length);
    }
    
    @Override
    public long solve() {
        Map<String, String> replacements = new HashMap<>();
        
        Pattern pattern = Pattern.compile("(.*) => (.*)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.length() == 0) {
                break;
            }
            
            Matcher matcher = pattern.matcher(line);
            matcher.matches();            
            replacements.put(matcher.group(2), matcher.group(1));
        }
        
        String molecule = reader.nextLine();
        reader.close();
        
        return solve(replacements, molecule);
    }

}
