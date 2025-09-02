package year2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
    private int solve(List<String[]> directions) {
        int depth = 0;
        int aim = 0;
        int horizontal = 0;
        for (String[] direction : directions) {
            int value = Integer.parseInt(direction[1]);
            switch (direction[0]) {
                case "forward":
                    horizontal += value;
                    depth += value * aim;
                    break;
                case "up":
                    aim -= value;
                    break;
                case "down":
                    aim += value;
                    break;
            }
        }
        
        return depth * horizontal;
    }

    @Override
    public long solve() {
        List<String[]> directions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            directions.add(reader.nextLine().split(" "));
        }
        reader.close();
        
        return solve(directions);
    }

}
