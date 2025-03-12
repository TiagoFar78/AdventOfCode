package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day04Part1 extends Challenge {
    
    private static final int ALPHABET_SIZE = 26;

    private int solve(List<String[]> rooms) {
        int sum = 0;
        
        for (String[] room : rooms) {
            sum += isReal(room) ? Integer.parseInt(room[1]) : 0;
        }
        
        return sum;
    }
    
    private boolean isReal(String[] room) {
        int[] counts = new int[ALPHABET_SIZE];
        for (char c : room[0].toCharArray()) {
            if (c != '-') {
                counts[c - 'a']++;   
            }
        }
        
        List<int[]> toSort = new ArrayList<>();
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            toSort.add(new int[] { counts[i], i });
        }
        toSort.sort((a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
        
        String mostCommon = "";
        for (int i = 0; i < room[2].length(); i++) {
            mostCommon += (char)(toSort.get(i)[1] + 'a');
        }
        
        return mostCommon.equals(room[2]);
    }
    
    @Override
    public long solve() {
        List<String[]> rooms = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(.*)-(\\d+)\\[(.*)\\]");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            String[] room = new String[3];
            room[0] = matcher.group(1);
            room[1] = matcher.group(2);
            room[2] = matcher.group(3);
            
            rooms.add(room);
        }
        reader.close();
        
        return solve(rooms);
    }
    
}
