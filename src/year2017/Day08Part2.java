package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day08Part2 extends Challenge {
    
    private int solve(List<String[]> instructions) {
        Map<String, Integer> registers = new HashMap<>();        
        
        int max = 0;
        
        for (String[] instruction : instructions) {
            if (satisfies(registers.getOrDefault(instruction[3], 0), instruction[4], instruction[5])) {
                int value = registers.getOrDefault(instruction[0], 0) + Integer.parseInt(instruction[1]) * Integer.parseInt(instruction[2]);
                registers.put(instruction[0], value);
                max = Math.max(value, max);
            }
        }
        
        return max;
    }
    
    public boolean satisfies(int left, String comparator, String rightS) {
        int right = Integer.parseInt(rightS);
        switch (comparator) {
            case "<":
                return left < right;
            case ">":
                return left > right;
            case "<=":
                return left <= right;
            case ">=":
                return left >= right;
            case "!=":
                return left != right;
            case "==":
                return left == right;
        }
        
        throw new IllegalAccessError("Woops shouldn't be here");
    }

    @Override
    public long solve() {
        List<String[]> instructions = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(.*) (.*) (.*) if (.*) (.*) (.*)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            String[] instruction = new String[6];
            instruction[0] = matcher.group(1);
            instruction[1] = matcher.group(2).equals("inc") ? "1" : "-1";
            instruction[2] = matcher.group(3);
            instruction[3] = matcher.group(4);
            instruction[4] = matcher.group(5);
            instruction[5] = matcher.group(6);
            
            instructions.add(instruction);
        }
        reader.close();
        
        return solve(instructions);
    }

}
