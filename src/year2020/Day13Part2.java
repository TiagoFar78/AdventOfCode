package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day13Part2 extends Challenge {
    
    // Solution too slow! It does not work
    
    private long solve(List<int[]> instructions) {
        long t = 0;
        long step = 1;
        
        for (int[] id : instructions) {
            while ((id[0] - (t % id[0])) % id[0] != id[1]) {
                t += step;
            }
            
            step = lcm(step, id[0]);
        }
        
        return t;
    }
    
    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        
        return a;
    }
    
    @Override
    public long solve() {
        List<int[]> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        reader.nextLine();
        
        int i = 0;
        for (String part : reader.nextLine().split(",")) {
            if (!part.equals("x")) {
                instructions.add(new int[] { Integer.parseInt(part), i });
            }
            i++;
        }
        reader.close();
        
        return solve(instructions);
    }

}
