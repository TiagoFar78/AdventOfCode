package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day17Part2 extends Challenge {

    private final static int SPINS = 50000000;
    
    private int solve(int jump) {
        int current = 0;
        int afterZero = -1;
        
        for (int i = 1; i <= SPINS; i++) {
            current = (current + jump) % i + 1;
            if (current == 1) {
                afterZero = i;
            }
        }
        
        return afterZero;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int jump = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(jump);
    }

}
