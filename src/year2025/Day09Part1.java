package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day09Part1 extends Challenge {
    
    private long solve(List<int[]> corners) {
        long max = 0;
        for (int i = 0; i < corners.size(); i++) {
            for (int j = i + 1; j < corners.size(); j++) {
                int length = Math.abs(corners.get(i)[0] - corners.get(j)[0]) + 1;
                int width = Math.abs(corners.get(i)[1] - corners.get(j)[1]) + 1;
                max = Math.max(max, (long) length * width);
            }
        }
        
        return max;
    }
    
    @Override
    public long solve() {
        List<int[]> corners = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            corners.add(toInt(reader.nextLine().split(",")));
        }
        reader.close();
        
        return solve(corners);
    }
    
    private int[] toInt(String[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = Integer.parseInt(arr[i]);
        }
        
        return ans;
    }

}
