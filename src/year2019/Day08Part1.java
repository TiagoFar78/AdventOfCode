package year2019;

import java.util.Scanner;

import main.Challenge;

public class Day08Part1 extends Challenge {

    private int solve(String image) {
        int cols = 25;
        int rows = 6;
        
        int minZeros = Integer.MAX_VALUE;
        int answer = 0;
        
        for (int i = 0; i < image.length(); i += cols * rows) {
            int[] counts = new int[3];
            for (int j = 0; j < cols * rows; j++) {
                counts[image.charAt(i + j) - '0']++;
            }
            
            if (counts[0] < minZeros) {
                minZeros = counts[0];
                answer = counts[1] * counts[2];
            }
        }
        
        return answer;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String image = reader.nextLine();
        reader.close();
        
        return solve(image);
    }

}
