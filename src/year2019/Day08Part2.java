package year2019;

import java.util.Scanner;

import main.Challenge;

public class Day08Part2 extends Challenge {

    private int solve(String image) {
        int cols = 25;
        int rows = 6;
        
        int[][] message = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                message[row][col] = 2;
            }
        }
        
        for (int i = 0; i < image.length(); i += cols * rows) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (message[row][col] == 2) {
                        message[row][col] = image.charAt(i + row * cols + col) - '0';
                    }
                }
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(message[row][col] == 0 ? '.' : '#');
            }
            System.out.println();
        }
        
        return 0;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String image = reader.nextLine();
        reader.close();
        
        return solve(image);
    }

}
