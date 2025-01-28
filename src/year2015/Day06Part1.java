package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day06Part1 extends Challenge {
    
    private final static int OFF = 0;
    private final static int ON = 1;
    private final static int TOGGLE = 2;
    
    private long solve(List<int[]> instructions) {
        boolean[][] grid = new boolean[1000][1000];
        
        for (int[] instruction : instructions) {
            if (instruction[0] == TOGGLE) {
                for (int i = instruction[1]; i <= instruction[3]; i++) {
                    for (int j = instruction[2]; j <= instruction[4]; j++) {
                        grid[i][j] = !grid[i][j];
                    }
                }
                continue;
            }
            
            for (int i = instruction[1]; i <= instruction[3]; i++) {
                for (int j = instruction[2]; j <= instruction[4]; j++) {
                    grid[i][j] = instruction[0] == ON;
                }
            }
        }
        
        int lit = 0;
        for (boolean[] line : grid) {
            for (boolean cell : line) {
                lit += cell ? 1 : 0;
            }
        }
        
        return lit;
    }

    @Override
    public long solve() {
        List<int[]> instructions = new ArrayList<>();
        
        Pattern pattern = Pattern.compile(".*(on|off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            instructions.add(new int[] {
                    matcher.group(1).equals("on") ? ON : matcher.group(1).equals("off") ? OFF : TOGGLE, 
                    Integer.parseInt(matcher.group(2)), 
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(5))
            });
        }
        reader.close();
        
        return solve(instructions);
    }
}
