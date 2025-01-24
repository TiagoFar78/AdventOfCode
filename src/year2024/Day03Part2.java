package year2024;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day03Part2 extends Challenge {

    @Override
    public long solve() {
        int mulSum = 0;
        boolean isEnabled = true;
        
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don\'t\\(\\)");
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            while (matcher.find()) {
                if (matcher.group().equals("do()")) {
                    isEnabled = true;
                }
                else if (matcher.group().equals("don't()")) {
                    isEnabled = false;
                }
                else if (isEnabled) {
                    mulSum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                }
            }
        }
        reader.close();
        
        return mulSum;
    }

}
