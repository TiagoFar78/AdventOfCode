package Dec2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day05Part1 extends Challenge {
    
    private int solve(List<int[]> rules, List<List<Integer>> pages) {
        Map<Integer, List<Integer>> rulesMap = new HashMap<>();
        for (int[] rule : rules) {
            if (!rulesMap.containsKey(rule[1])) {
                rulesMap.put(rule[1], new ArrayList<>());
            }
            rulesMap.get(rule[1]).add(rule[0]);
        }
        
        int middleSum = 0;
        for (List<Integer> page : pages) {
            if (isValidPage(rulesMap, page)) {
                middleSum += page.get(page.size() / 2);
            }
        }
        
        return middleSum;
    }
    
    private boolean isValidPage(Map<Integer, List<Integer>> rules, List<Integer> page) {
        for (int i = 0; i < page.size() - 1; i++) {
            if (anyCommonElement(page.subList(i + 1, page.size()), rules.get(page.get(i)))) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean anyCommonElement(List<Integer> l1, List<Integer> l2) {
        if (l2 == null) {
            return false;
        }
        
        for (int e1 : l1) {
            for (int e2 : l2) {
                if (e1 == e2) {
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public long solve() {
        List<int[]> rules = new ArrayList<>();
        List<List<Integer>> pages = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d+)\\|(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String nextLine = reader.nextLine();
            Matcher matcher = pattern.matcher(nextLine);
            if (!matcher.matches()) {
                break;
            }
            rules.add(new int[] { Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) });
        }

        pattern = Pattern.compile(",(\\d+)");
        
        while (reader.hasNextLine()) {
            List<Integer> page = new ArrayList<>();
            pages.add(page);
                        
            String nextLine = reader.nextLine();
            Matcher matcher = pattern.matcher(nextLine);
            page.add(Integer.parseInt(nextLine.substring(0, nextLine.indexOf(","))));
            while (matcher.find()) {
                page.add(Integer.parseInt(matcher.group(1)));
            }
        }
        
        reader.close();
        
        return solve(rules, pages);
    }

}
