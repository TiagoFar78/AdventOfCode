package year2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day08Part1 extends Challenge {
    
    private int solve(Map<String, List<int[]>> antennasFiltered, int n, int m) {        
        Set<String> antinodes = new HashSet<>();
        for (List<int[]> antennas : antennasFiltered.values()) {
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    int rowDistance = antennas.get(i)[0] - antennas.get(j)[0];
                    int colDistance = antennas.get(i)[1] - antennas.get(j)[1];
                    
                    int antinodeRow = antennas.get(i)[0] + rowDistance;
                    int antinodeCol = antennas.get(i)[1] + colDistance;
                    if (isInsideGrid(antinodeRow, antinodeCol, n, m)) {
                        antinodes.add(antinodeRow + " " + antinodeCol);
                    }
                    
                    antinodeRow = antennas.get(j)[0] - rowDistance;
                    antinodeCol = antennas.get(j)[1] - colDistance;
                    if (isInsideGrid(antinodeRow, antinodeCol, n, m)) {
                        antinodes.add(antinodeRow + " " + antinodeCol);
                    }
                }
            }
        }
        
        return antinodes.size();
    }
    
    private boolean isInsideGrid(int row, int col, int n, int m) {
        return row >= 0 && row < n && col >= 0 && col < m;
    }

    @Override
    public long solve() {
        Map<String, List<int[]>> antennas = new HashMap<>();
        int m = 0;
        
        Pattern pattern = Pattern.compile("[0-9a-zA-z]");
        int row = 0;
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            m = line.length();
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String key = matcher.group();
                if (!antennas.containsKey(key)) {
                    antennas.put(key, new ArrayList<>());
                }
                
                antennas.get(key).add(new int[] { row, matcher.start() });
            }
            row++;
        }
        reader.close();
        
        return solve(antennas, row, m);
    }
    
}
