package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day02Part1 extends Challenge {
    
    private long solve(List<int[]> boxes) {
        int total = 0;
        
        for (int[] box : boxes) {
            total += 2 * box[0] * box[1];
            total += 2 * box[1] * box[2];
            total += 2 * box[2] * box[0];
            
            total += Math.min(box[0] * box[1], Math.min(box[1] * box[2], box[2] * box[0]));
        }
        
        return total;
    }

    @Override
    public long solve() {
        List<int[]> boxes = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d+)x(\\d+)x(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.find();
            int[] box = new int[3];
            box[0] = Integer.parseInt(matcher.group(1));
            box[1] = Integer.parseInt(matcher.group(2));
            box[2] = Integer.parseInt(matcher.group(3));
            boxes.add(box);
        }
        reader.close();
        
        return solve(boxes);
    }

}
