package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day08Part1 extends Challenge {
    
    private final static int ROWS = 6;
    private final static int COLS = 50;
    
    private int solve(List<int[]> operations) {
        boolean[][] screen = new boolean[ROWS][COLS];
        
        for (int[] operation : operations) {
            switch (operation[0]) {
                case 0:
                    rect(screen, operation[1], operation[2]);
                    break;
                case 1:
                    rowShift(screen, operation[1], operation[2]);
                    break;
                case 2:
                    colShift(screen, operation[1], operation[2]);
                    break;
            }
        }
        
        int lit = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (screen[i][j]) {
                    lit++;
                }
            }
        }
        
        return lit;
    }
    
    private void rect(boolean[][] screen, int a, int b) {
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < a; j++) {
                screen[i][j] = true;
            }
        }
    }
    
    private void rowShift(boolean[][] screen, int row, int amount) {
        boolean[] newRow = new boolean[COLS];
        
        for (int i = 0; i < COLS; i++) {
            newRow[(i + amount) % COLS] = screen[row][i];
        }
        
        screen[row] = newRow;
    }
    
    private void colShift(boolean[][] screen, int col, int amount) {
        boolean[] newCol = new boolean[ROWS];
        
        for (int i = 0; i < ROWS; i++) {
            newCol[(i + amount) % ROWS] = screen[i][col];
        }
        
        for (int i = 0; i < ROWS; i++) {
            screen[i][col] = newCol[i];
        }
    }
    
    @Override
    public long solve() {
        List<int[]> operations = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            int[] operation = new int[3];
            
            String line = reader.nextLine();
            int opCode = line.startsWith("rect") ? 0 : line.startsWith("rotate row") ? 1 : 2;
            operation[0] = opCode;
            
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            operation[1] = Integer.parseInt(matcher.group());
            matcher.find();
            operation[2] = Integer.parseInt(matcher.group());
            
            operations.add(operation);
        }
        reader.close();
        
        return solve(operations);
    }
    
}
