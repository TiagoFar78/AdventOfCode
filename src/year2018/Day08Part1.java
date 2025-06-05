package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day08Part1 extends Challenge {
    
    private int solve(List<Integer> values) {
        return getSum(values, 0)[0];
    }
    
    private int[] getSum(List<Integer> values, int i) {
        int sum = 0;
        int sons = values.get(i);
        i++;
        int metadatas = values.get(i);
        i++;
        
        while (sons > 0) {
            sons--;
            
            int[] res = getSum(values, i);
            sum += res[0];
            i = res[1];
        }
        
        while (metadatas > 0) {
            sum += values.get(i);
            metadatas--;
            i++;
        }
        
        return new int[] { sum, i };
    }
    
    @Override
    public long solve() {
        List<Integer> values = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        while (matcher.find()) {
            values.add(Integer.parseInt(matcher.group()));
        }
        reader.close();
        
        return solve(values);
    }

}
