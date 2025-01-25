package year2015;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day03Part2 extends Challenge {
    
    private long solve(String s) {
        int xSanta = 0;
        int ySanta = 0;
        int xRobot = 0;
        int yRobot = 0;
        
        Set<String> visited = new HashSet<>();
        visited.add("0 0");
        for (int i = 0; i < s.length(); i += 2) {
            int[] visitResult = visitHouse(s.charAt(i), xSanta, ySanta, visited);
            xSanta = visitResult[0];
            ySanta = visitResult[1];
            
            visitResult = visitHouse(s.charAt(i + 1), xRobot, yRobot, visited);
            xRobot = visitResult[0];
            yRobot = visitResult[1];
        }
        
        if (s.length() % 2 == 1) {
            visitHouse(s.charAt(s.length() - 1), xSanta, ySanta, visited);
        }
        
        return visited.size();
    }
    
    private int[] visitHouse(char c, int x, int y, Set<String> visited) {
        switch (c) {
            case '>':
                y++;
                break;
            case 'v':
                x++;
                break;
            case '<':
                y--;
                break;
            case '^':
                x--;
        }
        
        visited.add(x + " " + y);
        return new int[] {x,y};
    }

    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        return solve(line);
    }

}
