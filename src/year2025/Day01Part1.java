package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day01Part1 extends Challenge {
    
    private long solve(List<Integer> rotations) {
        int pass = 0;
        int size = 100;
        int curr = 50;
        
        for (int rotation : rotations) {
            curr += (rotation % size + size);
            curr %= size;
            if (curr == 0) {
                pass++;
            }
        }
        
        return pass;
    }
    
    @Override
    public long solve() {
        List<Integer> rotations = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("([R|L])(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.find();
            rotations.add(Integer.parseInt(matcher.group(2)) * (matcher.group(1).equals("R") ? 1 : -1));
        }
        reader.close();
        
        return solve(rotations);
    }

}
