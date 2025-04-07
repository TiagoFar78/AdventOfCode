package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
    private int solve(List<List<Integer>> spreadSheet) {
        int checkSum = 0;
        
        outer: for (List<Integer> line : spreadSheet) {
            for (int i = 0; i < line.size(); i++) {
                for (int j = i + 1; j < line.size(); j++) {
                    if (line.get(i) % line.get(j) == 0) {
                        checkSum += line.get(i) / line.get(j);
                        continue outer;
                        
                    }
                    else if (line.get(j) % line.get(i) == 0) {
                        checkSum += line.get(j) / line.get(i);
                        continue outer;
                    }
                }
            }
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
