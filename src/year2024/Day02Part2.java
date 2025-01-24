package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
    private int solve(List<List<Integer>> reports) {
        int safeReports = 0;
        for (List<Integer> report : reports) {
            safeReports += isAlmostSafeReport(report) ? 1 : 0;
        }
        
        return safeReports;
    }
    
    private static final int[][] DIFFERENCES = { { 1, 3 }, { -3, -1} };
    
    private boolean isAlmostSafeReport(List<Integer> report) {        
        return isAlmostSafeReport(DIFFERENCES[0], report) || isAlmostSafeReport(DIFFERENCES[1], report);
    }
    
    private boolean isAlmostSafeReport(int[] differences, List<Integer> report) {
        boolean failed = false;
        for (int i = 1; i < report.size(); i++) {
            if (isLevelUnsafe(differences, report.get(i) - report.get(i - 1))) {
                if (failed) {
                    return false;
                }
                failed = true;
                
                if (i == report.size() - 1 || !isLevelUnsafe(differences, report.get(i + 1) - report.get(i - 1))) {
                    i++;
                }
                else if (i != 1 && isLevelUnsafe(differences, report.get(i) - report.get(i - 2))) {
                    return false;
                }
                
            }
        }
        
        return true;
    }
    
    private boolean isLevelUnsafe(int[] difference, int levelDiff) {
        return levelDiff > difference[1] || levelDiff < difference[0];
    }

    @Override
    public long solve() {
        List<List<Integer>> reports = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split("\\s+");
            List<Integer> report = new ArrayList<>();
            for (String number : line) {
                report.add(Integer.parseInt(number));
            }
            reports.add(report);
        }
        reader.close();
        
        return solve(reports);
    }

}
