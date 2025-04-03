package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day21Part2 extends Challenge {
    
    private String solve(List<String> instructions, String s) {
        for (int i = instructions.size() - 1; i >= 0; i--) {
            String[] instruction = instructions.get(i).split(" ");
            switch (instruction[0]) {
                case "swap_position":
                    s = swapPosition(s, Integer.parseInt(instruction[1]), Integer.parseInt(instruction[4]));
                    break;
                case "swap_letter":
                    s = swapLetter(s, instruction[1].charAt(0), instruction[4].charAt(0));
                    break;
                case "rotate_based":
                    s = unRotateBased(s, instruction[5].charAt(0));
                    break;
                case "rotate":
                    s = rotate(s, Integer.parseInt(instruction[2]) * (instruction[1].equals("left") ? -1 : 1) * -1);
                    break;
                case "reverse":
                    s = reverse(s, Integer.parseInt(instruction[2]), Integer.parseInt(instruction[4]));
                    break;
                case "move":
                    s = move(s, Integer.parseInt(instruction[5]), Integer.parseInt(instruction[2]));
                    break;
            }
        }
        
        return s;
    }
    
    private String swapPosition(String s, int x, int y) {
        int lower = Math.min(x, y);
        int larger = Math.max(x, y);
        return s.substring(0, lower) + s.charAt(larger) + s.substring(lower + 1, larger) + s.charAt(lower) + s.substring(larger + 1);
    }
    
    private String swapLetter(String s, char x, char y) {
        return s.replace(x, '0').replace(y, x).replace('0', y);
    }
    
    private String rotate(String s, int x) {
        x %= s.length();
        return x < 0 ? s.substring(-x) + s.substring(0, -x) : s.substring(s.length() - x) + s.substring(0, s.length() - x);
    }
    
    private String unRotateBased(String s, char x) {
        String origial = s;
        while (!rotateBased(s, x).equals(origial)) {
            s = rotate(s, -1);
        }
        
        return s;
    }
    
    private String rotateBased(String s, char x) {
        int index = s.indexOf(x);
        int steps = 1 + index + (index >= 4 ? 1 : 0);
        return rotate(s, steps);
    }
    
    private String reverse(String s, int x, int y) {
        String reversed = new StringBuilder(s.substring(x, y + 1)).reverse().toString();
        return s.substring(0, x) + reversed + s.substring(y + 1);
    }
    
    private String move(String s, int x, int y) {
        return x > y ?
                s.substring(0, y) + s.charAt(x) + s.substring(y, x) + s.substring(x + 1) :
                s.substring(0, x) + s.substring(x + 1, y + 1) + s.charAt(x) + s.substring(y + 1);
    }
    
    @Override
    public String solveString() {
        List<String> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            instructions.add(reader.nextLine().replace("rotate based", "rotate_based").replace("swap position", "swap_position").replace("swap letter", "swap_letter"));
        }
        reader.close();
        
        String starting = "fbgdceah";
        return solve(instructions, starting);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
