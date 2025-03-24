package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day15Part2 extends Challenge {

    private int solve(List<int[]> disks) {
        disks.add(new int[] { 11, 0 });
        
        int time = 0;
        outer: while (true) {
            for (int i = 0; i < disks.size(); i++) {
                int[] disk = disks.get(i);
                if ((disk[1] + time + i) % disk[0] != 0) {
                    time++;
                    continue outer;
                }
            }
            
            return time - 1;
        }
    }
    
    @Override
    public long solve() {
        List<int[]> disks = new ArrayList<>();

        Pattern pattern = Pattern.compile("Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+).");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int[] disk = new int[2];
            disk[0] = Integer.parseInt(matcher.group(1));
            disk[1] = Integer.parseInt(matcher.group(2));
            disks.add(disk);
        }
        reader.close();
        
        return solve(disks);
    }

}
