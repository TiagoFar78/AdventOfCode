package year2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day16Part2 extends Challenge {
    
    private int solve(List<String[]> aunts) {
        Map<String, Integer> properties = new HashMap<>();
        properties.put("children", 3);
        properties.put("cats", 7);
        properties.put("samoyeds", 2);
        properties.put("pomeranians", 3);
        properties.put("akitas", 0);
        properties.put("vizslas", 0);
        properties.put("goldfish", 5);
        properties.put("trees", 3);
        properties.put("cars", 2);
        properties.put("perfumes", 1);
        
        for (int i = 0; i < aunts.size(); i++) {
            if (matchProperties(properties, aunts.get(i))) {
                return i + 1;
            }
        }
        
        return -1;
    }
    
    private boolean matchProperties(Map<String, Integer> properties, String[] aunt) {
        for (int i = 0; i < aunt.length; i += 2) {
            int value = Integer.parseInt(aunt[i + 1]);
            if (aunt[i].equals("cats") || aunt[i].equals("trees")) {
                if (value <= properties.get(aunt[i])) {
                    return false;
                }
            }
            else if (aunt[i].equals("pomeranians") || aunt[i].equals("goldfish")) {
                if (value >= properties.get(aunt[i])) {
                    return false;
                }
            }
            else if (properties.get(aunt[i]) != value) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public long solve() {
        List<String[]> aunts = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("Sue \\d+: (.*): (\\d+), (.*): (\\d+), (.*): (\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            String[] aunt = new String[6];
            aunt[0] = matcher.group(1);
            aunt[1] = matcher.group(2);
            aunt[2] = matcher.group(3);
            aunt[3] = matcher.group(4);
            aunt[4] = matcher.group(5);
            aunt[5] = matcher.group(6);
            aunts.add(aunt);
        }
        reader.close();
        
        return solve(aunts);
    }

}
