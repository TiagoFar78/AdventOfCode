package year2015;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day25Part1 extends Challenge {
    
    private long solve(int row, int col) {
        int diagonal = row + col - 1;
        int sum = 0;
        for (int i = 1; i < diagonal; i++) {
            sum += i;
        }
        
        int index = sum + diagonal - row;
        
        long cell = 20151125;
        for (int i = 0; i < index; i++) {
            cell *= 252533;
            cell %= 33554393;
        }        
        
        return cell;
    }
    
    @Override
    public long solve() {
        Pattern pattern = Pattern.compile("To continue, please consult the code grid in the manual.  Enter the code at row (\\d+), column (\\d+).");
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        matcher.matches();
        int row = Integer.parseInt(matcher.group(1));
        int col = Integer.parseInt(matcher.group(2));
        reader.close();
        
        return solve(row, col);
    }

}
