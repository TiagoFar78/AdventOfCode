package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day03Part1 extends Challenge {
    
    private int solve(int square) {
        int n = 0;
        int sum = 1;
        while (sum <= square) {
            sum += n * 2 * 4;
            n++;
        }
        n--;
        sum -= n * 2 * 4;
        square -= sum;
        
        int indexInSquare = n - 1;
        int next = -1;
        for (int i = 1; i != square; i++) {
            indexInSquare += next;
            if (indexInSquare == 0) {
                next = 1;
            }
            else if (indexInSquare == n) {
                next = -1;
            }
        }
        
        
        return n + indexInSquare;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int square = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(square);
    }

}
