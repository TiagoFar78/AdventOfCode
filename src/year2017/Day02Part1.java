package year2017;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day02Part1 extends Challenge {
    
    private int solve(List<List<Integer>> spreadSheet) {
        int checkSum = 0;
        for (List<Integer> line : spreadSheet) {
            checkSum += Collections.max(line) - Collections.min(line);
        }
        
        return checkSum;
    }

    @Override
    public long solve() {
        List<List<Integer>> spreadSheet = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            List<Integer> line = new ArrayList<>();
            
            Matcher matcher = pattern.matcher(reader.nextLine());
            while (matcher.find()) {
                line.add(Integer.parseInt(matcher.group()));
            }
            
            spreadSheet.add(line);
        }
        reader.close();
        
        return solve(spreadSheet);
    }

}
