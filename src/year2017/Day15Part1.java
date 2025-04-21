package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day15Part1 extends Challenge {
    
    private static final int MOD = 2147483647;
    private static final int A_FACTOR = 16807;
    private static final int B_FACTOR = 48271;
    
    private int solve(long a, long b, int pairsAmount) {
        int total = 0;
        
        for (int i = 0; i < pairsAmount; i++) {
            a = a * A_FACTOR % MOD;
            b = b * B_FACTOR % MOD;
            
            if (match(a, b)) {
                total++;
            }
        }
        
        return total;
    }
    
    private boolean match(long a, long b) {
        int[] aBits = new int[16];
        int[] bBits = new int[16];
        
        for (int i = 0; i < 16; i++) {
            aBits[i] = (int) (a % 2);
            bBits[i] = (int) (b % 2);
            a /= 2;
            b /= 2;
        }
        
        for (int i = 0; i < 16; i++) {
            if (aBits[i] != bBits[i]) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int startingA = Integer.parseInt(reader.nextLine().substring("Generator A starts with ".length()));
        int startingB = Integer.parseInt(reader.nextLine().substring("Generator B starts with ".length()));
        reader.close();

        int pairsAmount = 40000000;
        return solve(startingA, startingB, pairsAmount);
    }

}
