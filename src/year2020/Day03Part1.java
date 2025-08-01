package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day03Part1 extends Challenge {
    
    private int solve(List<String> park) {
        int n = park.get(0).length();
        
        int rowMove = 1;
        int colMove = 3;
        
        int row = rowMove;
        int col = colMove;
        
        int trees = 0;
        while (row < park.size()) {
            if (park.get(row).charAt(col) == '#') {
                trees++;
            }
            
            row += rowMove;
            col = (col + colMove) % n;
        }
        
        return trees;
    }

    @Override
    public long solve() {
        List<String> park = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            park.add(reader.nextLine());        
        }
        reader.close();
        
        return solve(park);
    }

}
