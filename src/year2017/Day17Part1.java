package year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day17Part1 extends Challenge {

    private final static int SPINS = 2017;
    
    private int solve(int jump) {
        List<Integer> list = new ArrayList<>();
        int current = 0;
        list.add(0);
        
        for (int i = 1; i <= SPINS; i++) {
            current = (current + jump) % list.size() + 1;
            list.add(current, i);
        }
        
        return list.get(current + 1);
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int jump = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(jump);
    }

}
