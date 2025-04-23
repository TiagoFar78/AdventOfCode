package year2017;

import java.util.Scanner;

import main.Challenge;

public class Day16Part1 extends Challenge {

    private String solve(String[] instructions) {
        String programs = "abcdefghijklmnop";
        
        for (String instruction : instructions) {
            if (instruction.startsWith("s")) {
                programs = spin(programs, Integer.parseInt(instruction.substring(1)));
            }
            else if (instruction.startsWith("x")) {
                String[] ab = instruction.substring(1).split("/");
                programs = exchange(programs, Integer.parseInt(ab[0]), Integer.parseInt(ab[1]));
            }
            else if (instruction.startsWith("p")) {
                programs = partner(programs, instruction.charAt(1), instruction.charAt(3));
            }
        }
        
        return programs;
    }
    
    private String spin(String programs, int amount) {
        return programs.substring(programs.length() - amount) + programs.substring(0, programs.length() - amount);
    }
    
    private String exchange(String programs, int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        return programs.substring(0, min) + programs.charAt(max) + programs.substring(min + 1, max) + programs.charAt(min) + programs.substring(max + 1);
    }
    
    private String partner(String programs, char a, char b) {
        int aIndex = -1;
        int bIndex = -1;
        for (int i = 0; i < programs.length(); i++) {
            if (programs.charAt(i) == a) {
                aIndex = i;
            }
            else if (programs.charAt(i) == b) {
                bIndex = i;
            }
        }
        
        return exchange(programs, aIndex, bIndex);
    }
    
    @Override
    public String solveString() {
        Scanner reader = getInputFile();
        String[] instructions = reader.nextLine().split(",");
        reader.close();

        return solve(instructions);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
