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

public class Day03Part2 extends Challenge {
    
    private int solve(List<int[]> claims) {
        Set<Integer> claimsIds = new HashSet<>();
        for (int i = 1; i <= claims.size(); i++) {
            claimsIds.add(i);
        }
        
        Map<String, Integer> cellClaim = new HashMap<>();
        
        for (int[] claim : claims) {
            for (int x = claim[0]; x < claim[0] + claim[2]; x++) {
                for (int y = claim[1]; y < claim[1] + claim[3]; y++) {
                    String key = x + " " + y;
                    if (cellClaim.containsKey(key)) {
                        claimsIds.remove(cellClaim.get(key));
                        claimsIds.remove(claim[4]);
                    }
                    cellClaim.put(key, claim[4]);
                }
            }
        }
        
        return claimsIds.iterator().next();
    }

    @Override
    public long solve() {
        List<int[]> claims = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("#(\\d++) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int[] claim = new int[5];
            claim[0] = Integer.parseInt(matcher.group(2));
            claim[1] = Integer.parseInt(matcher.group(3));
            claim[2] = Integer.parseInt(matcher.group(4));
            claim[3] = Integer.parseInt(matcher.group(5));
            claim[4] = Integer.parseInt(matcher.group(1));
            
            claims.add(claim);
        }
        reader.close();
        
        return solve(claims);
    }

}
