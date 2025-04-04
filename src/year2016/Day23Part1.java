package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day23Part1 extends Challenge {
    
    private int solve(List<String[]> instructions, int eggs) {
        int[] registers = new int[4];
        registers[0] = eggs;
        
        for (int i = 0; i < instructions.size(); i++) {
            String[] parts = instructions.get(i);
            switch (parts[0]) {
                case "tgl":
                    int target = i + registers[parts[1].charAt(0) - 'a'];
                    if (target < 0 || target >= instructions.size()) {
                        break;
                    }
                    
                    int args = instructions.get(target).length - 1;
                    if (args == 1) {
                        if (instructions.get(target)[0].equals("inc")) {
                            instructions.get(target)[0] = "dec";
                        }
                        else {
                            instructions.get(target)[0] = "inc";
                        }
                    }
                    else {
                        if (instructions.get(target)[0].equals("jnz")) {
                            instructions.get(target)[0] = "cpy";
                        }
                        else {
                            instructions.get(target)[0] = "jnz";
                        }
                    }
                    break;
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
                        i += (parts[2].charAt(0) - 'a' >= 0 && parts[2].charAt(0) - 'a' <= 3 ? registers[parts[2].charAt(0) - 'a'] : Integer.parseInt(parts[2])) - 1;
                    }
                    break;
            }
        }

        return registers[0];
    }
    
    @Override
    public long solve() {
        List<String[]> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            instructions.add(reader.nextLine().split(" "));
        }
        reader.close();
        
        int eggs = 7;
        return solve(instructions, eggs);
    }

}
