package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day12Part1 extends Challenge {
    
    private int solve(List<String> document) {
        int total = 0;
        
        Pattern pattern = Pattern.compile("-?\\d+");
        
        for (String line : document) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                total += Integer.parseInt(matcher.group(0));
            }
        }
        
        return total;
    }

    @Override
    public long solve() {
        List<String> document = new ArrayList<>();        
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            document.add(reader.nextLine());
        }
        reader.close();
        
        return solve(document);
    }

}
