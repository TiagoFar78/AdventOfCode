package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day13Part1 extends Challenge {
    
    private long solve(List<int[]> machines) {
        long tokens = 0;
        for (int[] machine : machines) {
            tokens += tokensCost(machine);
        }
        return tokens;
    }
    
    private int tokensCost(int[] machine) {
        // b = (8400 - 5400 * 94) / (22 - 67 * 94) 
        //int b = (machine[4] - machine[5] * machine[0]) / (machine[1] - machine[3] * machine[0]);
        return 0;
    }
    
    @Override
    public long solve() {
        List<int[]> machines = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.length() == 0) {
                continue;
            }
            int[] machine = new int[6];
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            machine[0] = Integer.parseInt(matcher.group());
            matcher.find();
            machine[1] = Integer.parseInt(matcher.group());
            
            pattern.matcher(reader.nextLine());
            matcher.find();
            machine[2] = Integer.parseInt(matcher.group());
            matcher.find();
            machine[3] = Integer.parseInt(matcher.group());

            pattern.matcher(reader.nextLine());
            matcher.find();
            machine[4] = Integer.parseInt(matcher.group());
            matcher.find();
            machine[5] = Integer.parseInt(matcher.group());
            
            machines.add(machine);
        }
        reader.close();
        
        return solve(machines);
    }

}
