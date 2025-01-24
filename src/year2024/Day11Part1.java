package year2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day11Part1 extends Challenge {
    
    private final static int OPERATIONS = 25;
    private final static int YEAR = 2024;
    
    private long solve(List<Integer> stones) {
        long totalStones = 0;
        Map<String, Long> seen = new HashMap<>();
        
        for (int stone : stones) {
            totalStones += countStones(stone, OPERATIONS, seen);
        }
        
        return totalStones;
    }
    
    private long countStones(long stone, int operations, Map<String, Long> seen) {   
        String key = stone + " " + operations;
        if (seen.containsKey(key)) {
            return seen.get(key);
        }
        
        while (operations > 0) {
            operations--;
            if (stone == 0) {
                stone = 1;
                continue;
            }
            
            long[] splitNumbers = splitEvenDigits(stone);
            if (splitNumbers == null) {
                stone *= YEAR;
            }
            else {
                long count = countStones(splitNumbers[0], operations, seen) + countStones(splitNumbers[1], operations, seen);
                seen.put(key, count);
                return count;
            }
        }
        
        seen.put(key, 1L);
        return 1;
    }
    
    private long[] splitEvenDigits(long number) {
        String s = Long.toString(number);
        if (s.length() % 2 == 1) {
            return null;
        }
        
        return new long[] {Long.parseLong(s.substring(0, s.length() / 2)), Long.parseLong(s.substring(s.length() / 2))};
    }
     
    @Override
    public long solve() {
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        List<Integer> stones = new ArrayList<>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            stones.add(Integer.parseInt(matcher.group()));
        }
        
        return solve(stones);
    }

}
