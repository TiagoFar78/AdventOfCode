package Dec2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day02Part1 extends Challenge {
    
    private int solve(List<List<Integer>> reports) {
        int safeReports = 0;
        for (List<Integer> report : reports) {
            safeReports += isSafeReport(report) ? 1 : 0;
        }
        
        return safeReports;
    }
    
    private static final int[][] DIFFERENCES = { { 1, 3 }, { -3, -1} }; 
    
    private boolean isSafeReport(List<Integer> report) {
        int[] difference = report.get(1) - report.get(0) > 0 ? DIFFERENCES[0] : DIFFERENCES[1];
        
        for (int i = 1; i < report.size(); i++) {
            int levelDiff = report.get(i) - report.get(i - 1);
            if (levelDiff > difference[1] || levelDiff < difference[0]) {
                return false;
            }
        }
        
        return true;
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
