package Dec2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private long solve(List<Long> results, List<List<Long>> numbers) {
        int n = results.size();
        long total = 0;
        for (int i = 0; i < n; i++) {
            if (isPossibleEquation(results.get(i), numbers.get(i))) {
                total += results.get(i);
            }
        }
        
        return total;
    }
    
    private boolean isPossibleEquation(long result, List<Long> numbers) {
        return isPossibleEquation(result, numbers, 1, numbers.get(0));
    }
    
    private boolean isPossibleEquation(long result, List<Long> numbers, int next, long current) {
        if (next == numbers.size()) {
            return result == current;
        }
        
        return isPossibleEquation(result, numbers, next + 1, current + numbers.get(next)) || 
                isPossibleEquation(result, numbers, next + 1, current * numbers.get(next)) ||
                isPossibleEquation(result, numbers, next + 1, concatenate(current, numbers.get(next)));
    }
    
    private long concatenate(long n1, long n2) {
        return Long.parseLong(Long.toString(n1) + Long.toString(n2));
    }

    @Override
    public long solve() {
        List<Long> results = new ArrayList<>();
        List<List<Long>> numbers = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            results.add(Long.parseLong(matcher.group()));
            List<Long> l = new ArrayList<>();
            while (matcher.find()) {
                l.add(Long.parseLong(matcher.group()));
            }
            numbers.add(l);
        }
        reader.close();
        
        return solve(results, numbers);
    }
    
}
