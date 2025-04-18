package year2017;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day11Part2 extends Challenge {
    
    enum HexDir {
        n(0, -1), ne(1, -1), se(1, 0), s(0, 1), sw(-1, 1), nw(-1, 0);

        int dx, dy;

        HexDir(int dx, int dy) { this.dx = dx; this.dy = dy; }
    }
    
    private static final Map<String, int[]> DIRECTIONS = buildMap();
    
    private static final Map<String, int[]> buildMap() {
        Map<String, int[]> map = new HashMap<>();
        
        map.put("n", new int[] { 0, -1 });
        map.put("ne", new int[] { 1, -1 });
        map.put("se", new int[] { 1, 0 });
        map.put("s", new int[] { 0, 1 });
        map.put("sw", new int[] { -1, 1 });
        map.put("nw", new int[] { -1, 0 });
        
        return map;
    }
    
    public int solve(String[] path) {
        int x = 0;
        int y = 0;
        int max = 0;
        for (String move : path) {
            int[] dir = DIRECTIONS.get(move);
            x += dir[0];
            y += dir[1];
            max = Math.max((Math.abs(x) + Math.abs(y) + Math.abs(x + y)) / 2, max);
        }

        return max;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String[] path = reader.nextLine().split(",");
        reader.close();
        
        return solve(path);
    }

}
