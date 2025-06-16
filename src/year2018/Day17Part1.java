
package year2018;

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

public class Day17Part1 extends Challenge {

    private static final int WATER_SOURCE_COL = 500;
    private static final int WATER_SOURCE_ROW = 0;
    
    private int solve(List<int[]> claysList) {
        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        
        Set<String> clays = new HashSet<>();
        for (int[] clay : claysList) {
            if (clay[0] == 0) {
                for (int i = clay[2]; i <= clay[3]; i++) {
                    minRow = Math.min(minRow, i);
                    maxRow = Math.max(maxRow, i);
                    clays.add(i + " " + clay[1]);
                }
            }
            else {
                for (int i = clay[2]; i <= clay[3]; i++) {
                    clays.add(clay[1] + " " + i);
                }
                minRow = Math.min(minRow, clay[1]);
                maxRow = Math.max(maxRow, clay[1]);
            }
        }
        
        Map<String, Character> water = new HashMap<>();
        drop(minRow, maxRow, clays, water, WATER_SOURCE_ROW, WATER_SOURCE_COL);
        
        final int finalMinRow = minRow;
        final int finalMaxRow = maxRow;
        return (int) water.entrySet().stream()
                .filter(e -> Integer.parseInt(e.getKey().split(" ")[0]) >= finalMinRow && Integer.parseInt(e.getKey().split(" ")[0]) <= finalMaxRow)
                .filter(e -> e.getValue() == '~' || e.getValue() == '|')
                .count();
    }
    
   private void drop(int minRow, int maxRow, Set<String> clays, Map<String, Character> water, int row, int col) {
        if (row > maxRow) {
            return;
        }
        
        String p = row + " " + col;
        if (water.containsKey(p)) {
            return;
        }
        water.put(p, '|');

        String below = (row + 1) + " " + col;
        if (!clays.contains(below) && water.getOrDefault(below, '.') != '~') {
            drop(minRow, maxRow, clays, water, row + 1, col);
            if (water.getOrDefault(below, '.') != '~') {
                return;
            }
        }

        boolean closed = true;
        List<String> line = new ArrayList<>();
        for (int dir : new int[]{-1, 1}) {
            int newCol = col;
            while (true) {
                newCol += dir;
                String curr = row + " " + newCol;
                String under = (row + 1) + " " + newCol;
                if (clays.contains(curr)) {
                    break;
                }
                
                line.add(curr);
                if (!clays.contains(under) && water.getOrDefault(under, '.') != '~') {
                    closed = false;
                    drop(minRow, maxRow, clays, water, row + 1, newCol);
                    break;
                }
            }
        }

        if (closed) {
            water.put(p, '~');
            for (String pt : line) {
                water.put(pt, '~');
            }
            
            drop(minRow, maxRow, clays, water, row - 1, col);
        } else {
            for (String pt : line) {
                water.put(pt, '|');
            }
        }
    }
    
    @Override
    public long solve() {
        List<int[]> clays = new ArrayList<>();        

        Pattern pattern = Pattern.compile("\\d+");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            Matcher matcher = pattern.matcher(line);
            
            int[] clay = new int[4];
            clay[0] = line.charAt(0) == 'x' ? 0 : 1;
            
            int i = 1;
            while (matcher.find()) {
                clay[i] = Integer.parseInt(matcher.group());
                i++;
            }
            
            clays.add(clay);
        }
        reader.close();
        
        return solve(clays);
    }
    
}
