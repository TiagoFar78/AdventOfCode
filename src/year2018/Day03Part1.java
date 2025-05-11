package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day03Part1 extends Challenge {
    
    private int solve(List<int[]> claims) {
        Map<String, Integer> cellClaims = new HashMap<>();
        int multipleClaimCells = 0;
        
        for (int[] claim : claims) {
            for (int x = claim[0]; x < claim[0] + claim[2]; x++) {
                for (int y = claim[1]; y < claim[1] + claim[3]; y++) {
                    String key = x + " " + y;
                    if (cellClaims.getOrDefault(key, 0) == 1) {
                        multipleClaimCells++;
                    }
                    cellClaims.put(key, cellClaims.getOrDefault(key, 0) + 1);
                }
            }
        }
        
        return multipleClaimCells;
    }

    @Override
    public long solve() {
        List<int[]> claims = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("#\\d++ @ (\\d+),(\\d+): (\\d+)x(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int[] claim = new int[4];
            claim[0] = Integer.parseInt(matcher.group(1));
            claim[1] = Integer.parseInt(matcher.group(2));
            claim[2] = Integer.parseInt(matcher.group(3));
            claim[3] = Integer.parseInt(matcher.group(4));
            
            claims.add(claim);
        }
        reader.close();
        
        return solve(claims);
    }

}
