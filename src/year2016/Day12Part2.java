package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day12Part2 extends Challenge {
    
    private int solve(List<String> instructions) {
        int[] registers = new int[4];
        registers[2] = 1;
        
        for (int i = 0; i < instructions.size(); i++) {
            String[] parts = instructions.get(i).split(" ");
            switch (parts[0]) {
                case "cpy":
                    int value = parts[1].charAt(0) - 'a' >= 0 && parts[1].charAt(0) - 'a' <= 3 ? registers[parts[1].charAt(0) - 'a'] : Integer.parseInt(parts[1]);
                    registers[parts[2].charAt(0) - 'a'] = value;
                    break;
                case "inc":
                    registers[parts[1].charAt(0) - 'a']++;
                    break;
                case "dec":
                    registers[parts[1].charAt(0) - 'a']--;
                    break;
                case "jnz":
                    int x = parts[1].charAt(0) - 'a' >= 0 && parts[1].charAt(0) - 'a' <= 3 ? registers[parts[1].charAt(0) - 'a'] : Integer.parseInt(parts[1]);
                    if (x != 0) {
                        i += Integer.parseInt(parts[2]) - 1;
                    }
                    break;
            }
        }

        return registers[0];
    }
    
    @Override
    public long solve() {
        List<String> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            instructions.add(reader.nextLine());
        }
        reader.close();
        
        return solve(instructions);
    }

}
