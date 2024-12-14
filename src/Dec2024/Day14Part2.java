package Dec2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day14Part2 extends Challenge {
    
    private static final int ROWS = 103;
    private static final int COLS = 101;
    private static final int SECONDS = 100;
    
    private int solve(List<int[]> guards) {
        int[][] picture = new int[ROWS][COLS];

        for (int i = 1; i <= SECONDS*100; i++) {
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    picture[row][col] = 0;
                }
            }
            
            boolean isRobotOverlapping = false;
            for (int[] guard : guards) {
                guard[1] = ((guard[1] + guard[3]) % ROWS + ROWS) % ROWS;
                guard[0] = ((guard[0] + guard[2]) % COLS + COLS) % COLS;
                picture[guard[1]][guard[0]]++;
                if (picture[guard[1]][guard[0]] > 1) {
                    isRobotOverlapping = true;
                }
            }
            
            if (isRobotOverlapping) {
                continue;
            }
            
            System.out.println("\n");
            System.out.println("Second " + i + "\n");
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    System.out.print(picture[row][col] == 0 ? "." : Integer.toString(picture[row][col]));
                }
                System.out.println();
            }
        }
        
        return 0;
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
