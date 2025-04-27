package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day20Part1 extends Challenge {
    
    private int solve(List<int[][]> properties) {
        int min = Integer.MAX_VALUE;
        int particle = -1;
        
        for (int i = 0; i < properties.size(); i++) {
            int currentMin = Math.abs(properties.get(i)[2][0]) + Math.abs(properties.get(i)[2][1]) + Math.abs(properties.get(i)[2][2]);
            if (currentMin < min) {
                min = currentMin;
                particle = i;
            }
        }
        
        return particle;
    }
    
    @Override
    public long solve() {
        List<int[][]> properties = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int[][] property = new int[3][3];
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    property[i][j] = Integer.parseInt(matcher.group(1 + i * 3 + j));
                }
            }
            
            properties.add(property);
        }
        reader.close();
        
        return solve(properties);
    }
    
}
