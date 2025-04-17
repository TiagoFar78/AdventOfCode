package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day10Part2 extends Challenge {
    
    private static final int SIZE = 256;
    private static final int DENSE_SIZE = 16;
    private static final int RUNS = 64;
    private static final int[] EXTRA_LENGTHS = { 17, 31, 73, 47, 23 };
    
    private String solve(String input) {
        List<Integer> lengths = new ArrayList<>();
        for (char c : input.toCharArray()) {
            lengths.add((int) c);
        }
        
        for (int length : EXTRA_LENGTHS) {
            lengths.add(length);
        }

        int[] list = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            list[i] = i;
        }
        
        int i = 0;
        int skipSize = 0;
        for (int run = 0; run < RUNS; run++) {
            int[] result = runRound(lengths, list, i, skipSize);
            i = result[0];
            skipSize = result[1];
        }
        
        int[] denseHash = new int[DENSE_SIZE];
        for (int j = 0; j < DENSE_SIZE; j++) {
            int densePart = 0;
            for (int k = 0; k < DENSE_SIZE; k++) {
                densePart ^= list[j * DENSE_SIZE + k];
            }
            denseHash[j] = densePart;
        }
        
        String hash = "";
        
        for (int j = 0; j < DENSE_SIZE; j++) {
            hash += String.format("%02x", denseHash[j]);
        }
        
        return hash;
    }
    
    private int[] runRound(List<Integer> lengths, int[] list, int i, int skipSize) {
        for (int length : lengths) {
            for (int j = 0; j < length / 2; j++) {
                int temp = list[(i + j) % SIZE];
                list[(i + j) % SIZE] = list[(i + length - 1 - j) % SIZE];
                list[(i + length - 1 - j) % SIZE] = temp;
            }
            i += length + skipSize;
            skipSize++;
        }
        
        return new int[] { i, skipSize };
    }
    
    @Override
    public String solveString() {
        Scanner reader = getInputFile();
        String input = reader.nextLine();
        reader.close();
        
        return solve(input);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
