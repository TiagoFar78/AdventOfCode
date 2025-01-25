package year2015;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day03Part1 extends Challenge {
    
    private long solve(String s) {
        int x = 0;
        int y = 0;
        
        Set<String> visited = new HashSet<>();
        visited.add("0 0");
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
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
        }
        
        return visited.size();
    }

    @Override
    public long solve() {        
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        return solve(line);
    }

}
