package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day06Part2 extends Challenge {
    
    private int solve(List<Integer> banks) {
        Map<String, Integer> seen = new HashMap<>();
        
        int i = 0;
        while (true) {
            String key = banks.toString();
            if (seen.containsKey(key)) {
                return i - seen.get(key);
            }
            seen.put(key, i);
            i++;
            
            int maxI = 0;
            for (int j = 1; j < banks.size(); j++) {
                if (banks.get(j) > banks.get(maxI)) {
                    maxI = j;
                }
            }
            
            int value = banks.get(maxI);
            banks.set(maxI, 0);
            while (value > 0) {
                maxI = (maxI + 1) % banks.size();
                banks.set(maxI, banks.get(maxI) + 1);
                value--;
            }
        }
    }

    @Override
    public long solve() {
        List<Integer> banks = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        while (matcher.find()) {
            banks.add(Integer.parseInt(matcher.group()));
        }
        reader.close();
        
        return solve(banks);
    }

}
