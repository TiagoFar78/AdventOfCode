package Dec2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day14Part1 extends Challenge {
    
    private static final int ROWS = 103;
    private static final int COLS = 101;
    private static final int SECONDS = 100;
    
    private int solve(List<int[]> guards) {
        int[] quadrantGuards = new int[4];
        for (int[] guard : guards) {
            int finalRow = ((guard[1] + guard[3] * SECONDS) % ROWS + ROWS) % ROWS;
            if (finalRow == ROWS / 2) {
                continue;
            }
            
            int finalCol = ((guard[0] + guard[2] * SECONDS) % COLS + COLS) % COLS;
            if (finalCol == COLS / 2) {
                continue;
            }
            
            quadrantGuards[(finalRow > ROWS / 2 ? 2 : 0) + (finalCol > COLS / 2 ? 1 : 0)]++;
        }
        
        return quadrantGuards[0] * quadrantGuards[1] * quadrantGuards[2] * quadrantGuards[3];
    }
    
    @Override
    public long solve() {
        List<int[]> guards = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("-?\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            int[] guard = new int[4];
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.find();
            guard[0] = Integer.parseInt(matcher.group());
            matcher.find();
            guard[1] = Integer.parseInt(matcher.group());
            matcher.find();
            guard[2] = Integer.parseInt(matcher.group());
            matcher.find();
            guard[3] = Integer.parseInt(matcher.group());
            
            guards.add(guard);
        }
        reader.close();
        
        return solve(guards);
    }

}
