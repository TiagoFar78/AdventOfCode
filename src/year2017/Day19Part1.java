package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day19Part1 extends Challenge {
    
    private static final int[][] DIRECTIONS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };
    
    private String solve(List<String> diagram) {
        String lettersFound = "";
        int lines = diagram.size();
        int cols = diagram.get(0).length();
        
        int x = 0;
        int y = -1;
        for (int i = 0; i < cols; i++) {
            if (diagram.get(0).charAt(i) == '|') {
                y = i;
                break;
            }
        }
        
        int dir = 1;
        while (true) {
            if (diagram.get(x).charAt(y) >= 'A' && diagram.get(x).charAt(y) <= 'Z') {
                lettersFound += diagram.get(x).charAt(y);
            }
            
            int newX = x + DIRECTIONS[dir][0];
            int newY = y + DIRECTIONS[dir][1];
            if (newX >= 0 && newX < lines && newY >= 0 && newY < cols && diagram.get(newX).charAt(newY) != ' ') {
                x = newX;
                y = newY;
                continue;
            }
            
            dir = (dir + 1) % DIRECTIONS.length;
            newX = x + DIRECTIONS[dir][0];
            newY = y + DIRECTIONS[dir][1];
            if (newX >= 0 && newX < lines && newY >= 0 && newY < cols && diagram.get(newX).charAt(newY) != ' ') {
                x = newX;
                y = newY;
                continue;
            }
            

            dir = (dir + 2) % DIRECTIONS.length;
            newX = x + DIRECTIONS[dir][0];
            newY = y + DIRECTIONS[dir][1];
            if (newX >= 0 && newX < lines && newY >= 0 && newY < cols && diagram.get(newX).charAt(newY) != ' ') {
                x = newX;
                y = newY;
                continue;
            }
            
            break;
        }
        
        return lettersFound;
    }
    
    @Override
    public String solveString() {
        List<String> diagram = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            diagram.add(reader.nextLine());
        }
        reader.close();
        
        return solve(diagram);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
