package year2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day14Part2 extends Challenge {
    
    private static final int SIZE = 256;
    private static final int DENSE_SIZE = 16;
    private static final int RUNS = 64;
    private static final int[] EXTRA_LENGTHS = { 17, 31, 73, 47, 23 };
    
    private String knotHash(String input) {
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
    
    private static final Map<Character, String> HEX_TO_BINARY = buildHexToBinary();
    
    private static final Map<Character, String> buildHexToBinary() {
        Map<Character, String> hexToBinary = new HashMap<>();

        hexToBinary.put('0', "....");
        hexToBinary.put('1', "...#");
        hexToBinary.put('2', "..#.");
        hexToBinary.put('3', "..##");
        hexToBinary.put('4', ".#..");
        hexToBinary.put('5', ".#.#");
        hexToBinary.put('6', ".##.");
        hexToBinary.put('7', ".###");
        hexToBinary.put('8', "#...");
        hexToBinary.put('9', "#..#");
        hexToBinary.put('a', "#.#.");
        hexToBinary.put('b', "#.##");
        hexToBinary.put('c', "##..");
        hexToBinary.put('d', "##.#");
        hexToBinary.put('e', "###.");
        hexToBinary.put('f', "####");
        
        return hexToBinary;
    }
    
    private int solve(String input) {
        char[][] grid = new char[128][128];
        
        for (int i = 0; i < 128; i++) {
            String knotHash = knotHash(input + "-" + i);
            
            for (int j = 0; j < knotHash.length(); j++) {
                String binaryString = HEX_TO_BINARY.get(knotHash.charAt(j));
                for (int k = 0; k < 4; k++) {
                    grid[i][j * 4 + k] = binaryString.charAt(k);
                }
            }
        }
        
        int groupCount = 0;
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                if (grid[i][j] == '#') {
                    groupCount++;
                    deleteGroup(grid, i, j);
                }
            }
        }
        
        return groupCount;
    }
    
    private final static int[][] DIRECTIONS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private void deleteGroup(char[][] grid, int i, int j) {
        Set<String> seen = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { i, j });
        grid[i][j] = '.';
        
        while (!queue.isEmpty()) {
            int[] element = queue.poll();
            
            String key = element[0] + " " + element[1];
            if (seen.contains(key)) {
                continue;
            }
            seen.add(key);
            
            for (int[] dir : DIRECTIONS) {
                int x = element[0] + dir[0];
                int y = element[1] + dir[1];
                if (x >= 0 && x < 128 && y >= 0 && y < 128 && !seen.contains(x + " " + y) && grid[x][y] == '#') {
                    queue.add(new int[] {x, y});
                    grid[x][y] = '.';
                }
            }
        }
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String input = reader.nextLine();
        reader.close();
        
        return solve(input);
    }

}
