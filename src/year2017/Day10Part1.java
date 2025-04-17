package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day10Part1 extends Challenge {
    
    private static final int SIZE = 256;
    
    private int solve(List<Integer> lengths) {
        int[] list = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            list[i] = i;
        }
        
        int i = 0;
        int skipSize = 0;
        for (int length : lengths) {
            for (int j = 0; j < length / 2; j++) {
                int temp = list[(i + j) % SIZE];
                list[(i + j) % SIZE] = list[(i + length - 1 - j) % SIZE];
                list[(i + length - 1 - j) % SIZE] = temp;
            }
            i += length + skipSize;
            skipSize++;
        }
        
        return list[0] * list[1];
    }
    
    @Override
    public long solve() {
        List<Integer> lengths = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        while (matcher.find()) {
            lengths.add(Integer.parseInt(matcher.group()));
        }
        reader.close();
        
        return solve(lengths);
    }

}
