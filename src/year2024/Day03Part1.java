package year2024;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day03Part1 extends Challenge {

    @Override
    public long solve() {
        int mulSum = 0;
        
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            while (matcher.find()) {
                mulSum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        reader.close();
        
        return mulSum;
    }

}
