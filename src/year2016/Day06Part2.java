package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day06Part2 extends Challenge {
    
    private static final int ALPHABET_SIZE = 26;
    
    private String solve(List<String> messages) {
        int[][] occurences = new int[messages.get(0).length()][ALPHABET_SIZE];
        
        for (String message : messages) {
            for (int i = 0; i < message.length(); i++) {
                occurences[i][message.charAt(i) - 'a']++; 
            }
        }
        
        char[] errorCorrected = new char[messages.get(0).length()];
        for (int i = 0; i < occurences.length; i++) {
            int minCount = 0;
            for (int j = 1; j < ALPHABET_SIZE; j++) {
                if (occurences[i][j] != 0 && occurences[i][j] < occurences[i][minCount]) {
                    minCount = j;
                }
            }
            
            errorCorrected[i] = (char)(minCount + 'a');
        }
        
        return String.valueOf(errorCorrected);
    }
    
    @Override
    public String solveString() {
        List<String> messages = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            messages.add(reader.nextLine());
        }
        reader.close();
        
        return solve(messages);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }
    
}
