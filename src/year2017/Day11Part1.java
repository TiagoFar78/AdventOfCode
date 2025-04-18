package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day11Part1 extends Challenge {
    
    private int solve(String[] path) {
        int verticalDistance = 0;
        int b13Distance = 0;
        int b24Distance = 0;
        
        for (String move : path) {
            switch (move) {
                case "nw":
                    b24Distance++;
                    break;
                case "ne":
                    b13Distance++;
                    break;
                case "n":
                    verticalDistance++;
                    break;
                case "sw":
                    b13Distance--;
                    break;
                case "se":
                    b24Distance--;
                    break;
                case "s":
                    verticalDistance--;
                    break;                    
            }
        }
        
        if (b13Distance < 0 == b24Distance < 0) {
            int min = Math.abs(b13Distance) < Math.abs(b24Distance) ? b13Distance : b24Distance;
            b13Distance -= min;
            b24Distance -= min;
            verticalDistance += min;
        }
        
        if (b13Distance < 0 != verticalDistance < 0) {
            int min = Math.min(Math.abs(b13Distance), Math.abs(verticalDistance));
            b13Distance += min * (b13Distance < 0 ? 1 : -1);
            b24Distance += min * (b13Distance < 0 ? -1 : 1);
            verticalDistance += min * (verticalDistance < 0 ? 1 : -1);
        }
        
        if (b24Distance < 0 != verticalDistance < 0) {
            int min = Math.min(Math.abs(b24Distance), Math.abs(verticalDistance));
            b13Distance += min * (b24Distance < 0 ? 1 : -1);
            b24Distance += min * (b24Distance < 0 ? -1 : 1);
            verticalDistance += min * (verticalDistance < 0 ? 1 : -1);
        }
        
        return Math.abs(verticalDistance) + Math.abs(b13Distance) + Math.abs(b24Distance);
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String[] path = reader.nextLine().split(",");
        reader.close();
        
        return solve(path);
    }

}
